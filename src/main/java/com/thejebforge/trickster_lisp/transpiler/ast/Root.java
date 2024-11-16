package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.vavr.Tuple2;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public record Root(List<PreProcessor> preProcessors, List<SExpression> expressions) {
    public Root(List<Macro> macros) {
        this(new ArrayList<>(List.of(macros.stream().map(
                m -> (PreProcessor) m
        ).toArray(PreProcessor[]::new))), new ArrayList<>());
    }

    public long treeSize() {
        return expressions().stream().mapToLong(SExpression::treeSize).sum();
    }

    public Root simplifyRoot() {
        if (expressions.size() == 1
                && expressions.getFirst() instanceof Call call
                && call.getSubject() instanceof Empty
        ) {
            return new Root(preProcessors, call.getArguments());
        } else {
            return this;
        }
    }

    private Map<String, Macro> collectMacroMap() {
        var map = new HashMap<String, Macro>();

        preProcessors.stream()
                .filter(p -> p instanceof Macro)
                .map(p -> (Macro) p)
                .forEach(m -> map.put(m.getName(), m));

        return map;
    }

    private SExpression traverseAndApply(SExpression expr, Function<SExpression, SExpression> func) {
        expr = func.apply(expr);

        if (expr instanceof Call call) {
            return new Call(
                    traverseAndApply(call.getSubject(), func),
                    call.getArguments().stream()
                            .map(a -> traverseAndApply(a, func))
                            .toList()
            );
        } else if (expr instanceof ExpressionList list) {
            return new ExpressionList(list.getExpressions().stream()
                    .map(a -> traverseAndApply(a, func))
                    .toList());
        } else if (expr instanceof MapExpression map) {
            HashMap<SExpression, SExpression> newMap = new HashMap<>();
            map.getExpressionMap().forEach((k, v) -> newMap.put(
                    traverseAndApply(k, func),
                    traverseAndApply(v, func)
            ));
            return new MapExpression(newMap);
        }

        return expr;
    }

    private SExpression applyMacros(SExpression expr, Map<String, Macro> macros) {
        if (expr instanceof MacroCall call
                && macros.containsKey(call.getMacroName())) {
            return macros.get(call.getMacroName()).apply(
                    expr,
                    call.getArguments().stream()
                            .map(e -> traverseAndApply(e, ie -> applyMacros(ie, macros)))
                            .toList()
            );
        } else {
            return expr;
        }
    }

    public Root runPreProcessors() {
        var macros = collectMacroMap();

        return new Root(
                preProcessors,
                expressions.stream()
                        .map(rootExpr -> traverseAndApply(rootExpr, expr -> applyMacros(expr, macros)))
                        .toList()
        );
    }

    public Root reverseMacros(List<Macro> macros) {
        return new Root(
                preProcessors,
                expressions.stream()
                        .map(e -> reverseMacros(e, macros))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public Root reverseMacros() {
        return reverseMacros(retrieveMacros());
    }

    private SExpression reverseMacros(SExpression expr, List<Macro> macros) {
        // Recurse, so we can walk up the tree later
        // Don't take macro calls into consideration, they shouldn't exist at the moment
        switch (expr) {
            case Call call -> {
                call.setSubject(reverseMacros(call.getSubject(), macros));
                call.setArguments(
                        call.getArguments().stream()
                                .map(e -> reverseMacros(e, macros))
                                .collect(Collectors.toCollection(ArrayList::new))
                );
            }

            case ExpressionList list -> list.setExpressions(
                    list.getExpressions().stream()
                            .map(e -> reverseMacros(e, macros))
                            .collect(Collectors.toCollection(ArrayList::new))
            );

            case MapExpression map -> {
                HashMap<SExpression, SExpression> newMap = new HashMap<>();

                map.getExpressionMap().entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(
                                reverseMacros(entry.getKey(), macros),
                                reverseMacros(entry.getValue(), macros)
                        ))
                        .forEach(e -> newMap.put(e.getKey(), e.getValue()));

                map.setExpressionMap(newMap);
            }

            default -> {}
        }

        // After we recursed as much as we can, do the matching
        var candidate = macros.stream()
                .map(m -> new Tuple2<>(m, m.matchAndCollect(expr)))
                .filter(t -> t._2().isPresent())
                .min(Comparator.comparingLong(a -> a._1().treeSize()));

        //noinspection OptionalGetWithoutIsPresent
        return candidate.map(t -> t._2().get()).orElse(expr);
    }

    public Root prependMacros(Collection<Macro> macros) {
        preProcessors.addAll(0, macros);
        return this;
    }

    public List<Macro> retrieveMacros() {
        return preProcessors.stream()
                .filter(p -> p instanceof Macro)
                .map(Macro.class::cast)
                .toList();
    }

    @Override
    public String toString() {
        return "Root{" +
                "\npreProcessors=" + preProcessors +
                ", \nexpressions=" + expressions +
                "\n}";
    }

    public String toCode() {
        return toCode(LispUtils.DEFAULT_TAB_SIZE);
    }

    public String toCode(int tabSize) {
        return preProcessors.stream()
                .map(p -> p.toCode(0, tabSize, false))
                .collect(Collectors.joining("\n"))
                + ((!preProcessors.isEmpty() && !expressions.isEmpty()) ? "\n\n" : "") +
                expressions.stream()
                        .map(e -> e.toCode(0, tabSize, false))
                        .collect(Collectors.joining("\n"));
    }
}
