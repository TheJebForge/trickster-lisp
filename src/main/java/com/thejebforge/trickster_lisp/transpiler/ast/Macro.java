package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import com.thejebforge.trickster_lisp.transpiler.ast.builder.MacroCallBuilder;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Macro extends PreProcessor {
    public static final StructEndec<Macro> ENDEC = StructEndecBuilder.of(
            StructEndec.STRING.fieldOf("name", Macro::getName),
            StructEndec.STRING.listOf().fieldOf("args", Macro::getArguments),
            StructEndec.BOOLEAN.fieldOf("greedy", Macro::isGreedy),
            SExpression.ENDEC.fieldOf("subst", Macro::getSubstitute),
            Macro::new
    );

    private String name;
    private List<String> arguments;
    private boolean greedy;
    private SExpression substitute;

    public Macro(String name, List<String> arguments, boolean greedy, SExpression substitute) {
        this.name = name;
        this.arguments = arguments;
        this.greedy = greedy;
        this.substitute = substitute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isGreedy() {
        return greedy;
    }

    public void setGreedy(boolean greedy) {
        this.greedy = greedy;
    }

    public SExpression getSubstitute() {
        return substitute;
    }

    public void setSubstitute(SExpression substitute) {
        this.substitute = substitute;
    }

    private Stream<SExpression> findAndReplace(SExpression current, List<SExpression> substitutions) {
        switch (current) {
            case Identifier id -> {
                var argumentIndex = arguments.indexOf(id.getName());

                if (argumentIndex != -1)
                    return Stream.of(substitutions.get(argumentIndex).deepCopy());
                else
                    return Stream.of(id);
            }

            case Call call -> {
                return Stream.of(new Call(
                        findAndReplace(call.getSubject(), substitutions)
                                .findFirst()
                                .orElseThrow(),
                        call.getArguments().stream()
                                .flatMap(e -> findAndReplace(e, substitutions))
                                .toList()
                ));
            }

            case ExpressionList list -> {
                return Stream.of(new ExpressionList(list.getExpressions().stream()
                        .flatMap(e -> findAndReplace(e, substitutions))
                        .toList()));
            }

            case MapExpression map -> {
                HashMap<SExpression, SExpression> newMap = new HashMap<>();
                map.getExpressionMap().forEach((k, v) -> newMap.put(
                        findAndReplace(k, substitutions).findFirst()
                                .orElseThrow(() -> CallUtils.getConversionError(k, "This should never happen")),
                        findAndReplace(v, substitutions).findFirst()
                                .orElseThrow(() -> CallUtils.getConversionError(k, "This should never happen"))
                ));

                return Stream.of(new MapExpression(newMap));
            }

            case Greedy ignored -> {
                return substitutions.stream()
                        .skip(arguments.size());
            }

            default -> {
                return Stream.of(current);
            }
        }
    }

    public SExpression apply(SExpression parent, List<SExpression> args) {
        if (arguments.size() != args.size() && !greedy) {
            throw CallUtils.getConversionError(parent, "Invalid amount of arguments passed to macro call, " +
                    "expected " + arguments.size() + " arguments, got " + args.size());
        }

        return findAndReplace(substitute.deepCopy(), args)
                .findFirst()
                .orElseThrow();
    }

    private boolean greedyMatchAndCollect(
            List<SExpression> expressions,
            List<SExpression> substitutions,
            Map<String, List<SExpression>> collectedArgs) {

        boolean encounteredGreedyBefore = collectedArgs.containsKey("...");

        int substituteIndex = 0;
        boolean gotGreedy = false;

        for (SExpression expression : expressions) {
            // Substitute list too short
            if (substituteIndex > substitutions.size()) {
                return false;
            }

            if (substituteIndex < substitutions.size()
                    && substitutions.get(substituteIndex) instanceof Greedy
                    && !gotGreedy) {
                gotGreedy = true;
                collectedArgs.putIfAbsent("...", new ArrayList<>());
                substituteIndex++;
            }

            if (gotGreedy) {
                if (substituteIndex < substitutions.size()
                        && matchAndCollect(expression, substitutions.get(substituteIndex), collectedArgs)) {
                    gotGreedy = false;
                    substituteIndex++;
                } else {
                    if (encounteredGreedyBefore) {
                        if (!collectedArgs.get("...").contains(expression)) {
                            return false;
                        }
                    } else {
                        collectedArgs.get("...").add(expression);
                    }
                }
            } else {
                if (substituteIndex >= substitutions.size()
                        || !matchAndCollect(expression, substitutions.get(substituteIndex), collectedArgs)) {
                    return false;
                }

                substituteIndex++;
            }
        }

        return substituteIndex == substitutions.size();
    }

    private boolean matchAndCollect(
            SExpression expression,
            SExpression substitute,
            Map<String, List<SExpression>> collectedArgs
    ) {
        if (substitute instanceof Identifier id
                && this.arguments.contains(id.getName())) {
            // Make sure new expression is the same as previous one
            if (collectedArgs.containsKey(id.getName())) {
                return collectedArgs.get(id.getName()).contains(expression);
            } else {
                collectedArgs.putIfAbsent(id.getName(), new ArrayList<>());
                collectedArgs.get(id.getName()).add(expression);
                return true;
            }
        }

        if (!expression.shallowEquals(substitute)) {
            return false;
        }

        switch (expression) {
            case Call call when substitute instanceof Call substituteCall -> {
                if (!matchAndCollect(call.getSubject(), substituteCall.getSubject(), collectedArgs))
                    return false;

                if (!greedyMatchAndCollect(call.getArguments(), substituteCall.getArguments(), collectedArgs))
                    return false;
            }

            case ExpressionList list when substitute instanceof ExpressionList substituteList -> {
                if (!greedyMatchAndCollect(list.getExpressions(), substituteList.getExpressions(), collectedArgs))
                    return false;
            }

            case MapExpression map when substitute instanceof MapExpression substituteMap -> {
                var substituteSet = substituteMap.getExpressionMap().entrySet();

                for (var entry : map.getExpressionMap().entrySet()) {
                    Optional<SExpression> substituteValue = Optional.empty();

                    for (var substituteEntry : substituteSet) {
                        if (matchAndCollect(entry.getKey(), substituteEntry.getKey(), collectedArgs)) {
                            substituteValue = Optional.of(substituteEntry.getValue());
                        }
                    }

                    if (substituteValue.isEmpty()) {
                        return false;
                    }

                    if (!matchAndCollect(entry.getValue(), substituteValue.get(), collectedArgs))
                        return false;
                }
            }

            default -> {}
        }

        return true;
    }

    private boolean confirmCollectedArguments(Map<String, List<SExpression>> collectedArgs) {
        for (var argName : arguments) {
            if (!collectedArgs.containsKey(argName)) {
                return false;
            }

            var argList = collectedArgs.get(argName);
            if (argList.stream().distinct().count() != 1) {
                return false;
            }
        }

        return !greedy || collectedArgs.containsKey("...");
    }

    private SExpression buildMacroCall(Map<String, List<SExpression>> collectedArgs) {
        var builder = MacroCallBuilder.builder(name);

        for (var argName : arguments) {
            builder.add(collectedArgs.get(argName).getFirst());
        }

        if (greedy) {
            collectedArgs.get("...")
                    .forEach(builder::add);
        }

        return builder.build();
    }

    public Optional<SExpression> matchAndCollect(SExpression expression) {
        HashMap<String, List<SExpression>> collectedArgs = new HashMap<>();

        if (!matchAndCollect(expression, substitute, collectedArgs))
            return Optional.empty();

        if (!confirmCollectedArguments(collectedArgs))
            return Optional.empty();

        return Optional.of(buildMacroCall(collectedArgs));
    }

    public long treeSize() {
        return ((long) arguments.size())
                + substitute.treeSize()
                + 3L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Macro macro)) return false;
        return greedy == macro.greedy
                && Objects.equals(name, macro.name)
                && Objects.equals(arguments, macro.arguments)
                && Objects.equals(substitute, macro.substitute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments, greedy, substitute);
    }

    @Override
    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + "(#def " + name
                + " (" + String.join(" ", arguments)
                + (greedy ? (arguments.isEmpty() ? "" : " ") + "..." : "") + ") \n"
                + substitute.toCode(indent + tabSize, tabSize, inline) + ")";
    }
}
