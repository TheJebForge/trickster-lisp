package com.thejebforge.trickster_lisp.transpiler;

import com.thejebforge.trickster_lisp.parser.lispLexer;
import com.thejebforge.trickster_lisp.parser.lispParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// This one is manual since ANTLR does CST instead
public abstract class LispAST {
    private static final int DEFAULT_TAB_SIZE = 4;

    public record Root(List<PreProcessor> preProcessors, List<SExpression> expressions) {
        public Root simplifyRoot() {
            if (expressions.size() == 1
                    && expressions.getFirst() instanceof Call call
                    && call.subject instanceof Empty
            ) {
                return new Root(preProcessors, call.arguments);
            } else {
                return this;
            }
        }

        private Map<String, Macro> collectMacroMap() {
            var map = new HashMap<String, Macro>();

            preProcessors.stream()
                    .filter(p -> p instanceof Macro)
                    .map(p -> (Macro) p)
                    .forEach(m -> map.put(m.name, m));

            return map;
        }

        private SExpression traverseAndApply(SExpression expr, Function<SExpression, SExpression> func) {
            expr = func.apply(expr);

            if (expr instanceof Call call) {
                return new Call(
                        func.apply(call.subject),
                        call.arguments.stream()
                                .map(func)
                                .toList()
                );
            } else if (expr instanceof ExpressionList list) {
                return new ExpressionList(list.expressions.stream()
                        .map(func)
                        .toList());
            }

            return expr;
        }

        private SExpression applyMacros(SExpression expr, Map<String, Macro> macros) {
            if (expr instanceof Call call
                    && call.subject instanceof Identifier id
                    && macros.containsKey(id.name)) {
                return macros.get(id.name).apply(
                        call.arguments.stream()
                                .map(e -> traverseAndApply(e, ie -> applyMacros(ie, macros)))
                                .toList()
                );
            } else if (expr instanceof Identifier id
                    && macros.containsKey(id.name)) {
                return macros.get(id.name).apply(Collections.emptyList());
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

        @Override
        public String toString() {
            return "Root{" +
                    "\npreProcessors=" + preProcessors +
                    ", \nexpressions=" + expressions +
                    "\n}";
        }

        public String toCode() {
            return toCode(DEFAULT_TAB_SIZE);
        }

        public String toCode(int tabSize) {
            return preProcessors.stream()
                            .map(p -> p.toCode(0, tabSize, false))
                            .collect(Collectors.joining("\n"))
                    + (!preProcessors.isEmpty() ? "\n\n" : "") +
                    expressions.stream()
                            .map(e -> e.toCode(0, tabSize, false))
                            .collect(Collectors.joining("\n"));
        }
    }

    // PreProcessors

    public abstract static class PreProcessor {
        public String toCode(int indent) {
            return toCode(indent, DEFAULT_TAB_SIZE, false);
        }

        public abstract String toCode(int indent, int tabSize, boolean inline);
    }

    public static class Macro extends PreProcessor {
        private String name;
        private List<String> arguments;
        private SExpression substitute;

        public Macro(String name, List<String> arguments, SExpression substitute) {
            this.name = name;
            this.arguments = arguments;
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

        public SExpression getSubstitute() {
            return substitute;
        }

        public void setSubstitute(SExpression substitute) {
            this.substitute = substitute;
        }

        private SExpression findAndReplace(SExpression current, List<SExpression> substitutions) {
            switch (current) {
                case Identifier id -> {
                    var argumentIndex = arguments.indexOf(id.getName());

                    if (argumentIndex != -1)
                        return substitutions.get(argumentIndex).deepCopy();
                    else
                        return id;
                }

                case Call call -> {
                    return new Call(
                            findAndReplace(call.subject, substitutions),
                            call.arguments.stream()
                                    .map(e -> findAndReplace(e, substitutions))
                                    .toList()
                    );
                }

                case ExpressionList list -> {
                    return new ExpressionList(list.expressions.stream()
                            .map(e -> findAndReplace(e, substitutions))
                            .toList());
                }

                default -> {
                    return current;
                }
            }
        }

        public SExpression apply(List<SExpression> args) {
            if (arguments.size() != args.size()) {
                return null;
            }

            return findAndReplace(substitute.deepCopy(), args);
        }

        @Override
        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + "(#def " + name + " (" + String.join(" ", arguments) + ") \n"
                    + substitute.toCode(indent + tabSize, tabSize, inline) + ")";
        }
    }


    // SExpressions

    public abstract static class SExpression {
        public abstract SExpression deepCopy();

        public String toCode(int indent) {
            return toCode(indent, DEFAULT_TAB_SIZE, false);
        }

        public abstract String toCode(int indent, int tabSize, boolean inline);
    }

    private static String addIndent(int indent, boolean inline) {
        return inline ? "" : " ".repeat(indent);
    }

    public static class Void extends SExpression {
        @Override
        public String toString() {
            return "Void";
        }

        @Override
        public SExpression deepCopy() {
            return new Void();
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + "void";
        }
    }

    public static class Empty extends SExpression {
        @Override
        public String toString() {
            return "Empty";
        }

        @Override
        public SExpression deepCopy() {
            return new Empty();
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + "_";
        }
    }

    public static class Identifier extends SExpression {
        private String name;

        public Identifier(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Identifier that)) return false;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        @Override
        public String toString() {
            return "Identifier{\n" +
                    "name='" + name + '\'' +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new Identifier(name);
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + name;
        }
    }

    public static class StringExpression extends SExpression {
        private String value;

        public StringExpression(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StringExpression that)) return false;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return "StringExpression{\n" +
                    "value='" + value + '\'' +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new StringExpression(value);
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + '"' + value + '"';
        }
    }

    public static class Operator extends SExpression {
        private String type;

        public Operator(String name) {
            this.type = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Operator operator)) return false;
            return Objects.equals(type, operator.type);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(type);
        }

        @Override
        public String toString() {
            return "Operator{\n" +
                    "type='" + type + '\'' +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new Operator(type);
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + type;
        }
    }

    public static class DoubleValue extends SExpression {
        private double number;

        public DoubleValue(double number) {
            this.number = number;
        }

        public double getNumber() {
            return number;
        }

        public void setNumber(double number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DoubleValue that)) return false;
            return Double.compare(number, that.number) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(number);
        }

        @Override
        public String toString() {
            return "Double{\n" +
                    "number=" + number +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new DoubleValue(number);
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + number;
        }
    }

    public static class IntegerValue extends SExpression {
        private int number;

        public IntegerValue(int number){
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof IntegerValue that)) return false;
            return number == that.number;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(number);
        }

        @Override
        public String toString() {
            return "Integer{\n" +
                    "number=" + number +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new IntegerValue(number);
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + number;
        }
    }

    public static class BooleanValue extends SExpression {
        private Boolean value;

        public BooleanValue(Boolean value) {
            this.value = value;
        }

        public Boolean getValue() {
            return value;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BooleanValue that)) return false;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return "BooleanValue{" +
                    "value=" + value +
                    '}';
        }

        @Override
        public SExpression deepCopy() {
            return new BooleanValue(value);
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + value;
        }
    }

    public static class Call extends SExpression {
        private SExpression subject;
        private List<SExpression> arguments;

        public Call(SExpression subject, List<SExpression> arguments) {
            this.subject = subject;
            this.arguments = arguments;
        }

        public SExpression getSubject() {
            return subject;
        }

        public void setSubject(SExpression subject) {
            this.subject = subject;
        }

        public List<SExpression> getArguments() {
            return arguments;
        }

        public void setArguments(List<SExpression> arguments) {
            this.arguments = arguments;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Call call)) return false;
            return Objects.equals(subject, call.subject) && Objects.equals(arguments, call.arguments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subject, arguments);
        }

        @Override
        public String toString() {
            return "Call{\n" +
                    "subject='" + subject + '\'' +
                    ", \narguments=" + arguments +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new Call(subject.deepCopy(), arguments.stream()
                    .map(SExpression::deepCopy)
                    .toList());
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            List<String> codeArguments = arguments.stream()
                    .map(expression -> expression.toCode(indent + tabSize, tabSize, true))
                    .toList();

            int argumentsLength = codeArguments.stream()
                    .map(String::length)
                    .reduce(0, (result, len) -> result + len + 1);

            if (argumentsLength > 40) {
                return addIndent(indent, inline) + '(' + subject.toCode(indent + tabSize, tabSize, true) + '\n'
                        + codeArguments.stream()
                        .map(arg -> addIndent(indent + tabSize, false) + arg)
                        .collect(Collectors.joining("\n"))
                        + '\n' + " ".repeat(indent) + ')';
            } else {
                return addIndent(indent, inline) + '(' + subject.toCode(indent + tabSize, tabSize, true)
                        + (codeArguments.isEmpty() ? "" : ' ')
                        + String.join(" ", codeArguments) + ')';
            }
        }
    }

    public static class ExpressionList extends SExpression {
        private List<SExpression> expressions;

        public ExpressionList(List<SExpression> expressions) {
            this.expressions = expressions;
        }

        public List<SExpression> getExpressions() {
            return expressions;
        }

        public void setExpressions(List<SExpression> expressions) {
            this.expressions = expressions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ExpressionList that)) return false;
            return Objects.equals(expressions, that.expressions);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(expressions);
        }

        @Override
        public String toString() {
            return "ExpressionList{\n" +
                    "expressions=" + expressions +
                    "\n}";
        }

        @Override
        public SExpression deepCopy() {
            return new ExpressionList(expressions.stream()
                    .map(SExpression::deepCopy)
                    .toList());
        }

        public String toCode(int indent, int tabSize, boolean inline) {
            if (expressions.isEmpty()) {
                return addIndent(indent, inline) + "[]";
            } else {
                return addIndent(indent, inline) + "[" + (expressions.size() == 1 ? "" : '\n')
                        + expressions.stream()
                        .map(e -> e.toCode(indent + tabSize, tabSize, expressions.size() == 1))
                        .collect(Collectors.joining(",\n")) + ']';
            }
        }
    }

    public static class MapExpression extends SExpression {
        private HashMap<SExpression, SExpression> expressionMap;

        public MapExpression(HashMap<SExpression, SExpression> expressionMap) {
            this.expressionMap = expressionMap;
        }

        public HashMap<SExpression, SExpression> getExpressionMap() {
            return expressionMap;
        }

        public void setExpressionMap(HashMap<SExpression, SExpression> expressionMap) {
            this.expressionMap = expressionMap;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MapExpression that)) return false;
            return Objects.equals(expressionMap, that.expressionMap);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(expressionMap);
        }

        @Override
        public SExpression deepCopy() {
            var newMap = new HashMap<SExpression, SExpression>();

            expressionMap.forEach((k, v) -> newMap.put(k.deepCopy(), v.deepCopy()));

            return new MapExpression(newMap);
        }

        @Override
        public String toCode(int indent, int tabSize, boolean inline) {
            if (expressionMap.isEmpty()) {
                return addIndent(indent, inline) + "{}";
            } else {
                return addIndent(indent, inline) + "{" + (expressionMap.size() == 1 ? "" : '\n')
                        + expressionMap.entrySet().stream()
                        .map(e ->
                                e.getKey().toCode(indent + tabSize, tabSize, expressionMap.size() == 1)
                                        + " : "
                                        + e.getValue().toCode(indent + tabSize, tabSize, true))
                        .collect(Collectors.joining(",\n")) + '}';
            }
        }
    }

    // Builder
    public interface ExpressionBuilder<T, R> {
        T add(SExpression expression);
        T addIdentifier(String name);
        T addOperator(String operator);
        T addNumber(Double number);
        T addNumber(Integer number);
        T addBoolean(Boolean value);
        T addString(String value);

        T addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder);

        default T addCall(String subject, Function<CallBuilder, CallBuilder> builder) {
            return addCall(new Identifier(subject), builder);
        }

        default T addCall(Double subject, Function<CallBuilder, CallBuilder> builder) {
            return addCall(new DoubleValue(subject), builder);
        }

        default T addCall(Integer subject, Function<CallBuilder, CallBuilder> builder) {
            return addCall(new DoubleValue(subject), builder);
        }

        default T addCall(Boolean subject, Function<CallBuilder, CallBuilder> builder) {
            return addCall(new BooleanValue(subject), builder);
        }

        default T addCall(Function<CallBuilder, CallBuilder> builder) {
            return addCall(new Void(), builder);
        }

        T addList(Function<ListBuilder, ListBuilder> builder);
        T addMap(Function<MapBuilder, MapBuilder> builder);
        R build();
    }

    public static class RootBuilder implements ExpressionBuilder<RootBuilder, Root> {
        private final Root root;

        private RootBuilder() {
            this.root = new Root(new ArrayList<>(), new ArrayList<>());
        }

        public static RootBuilder builder() {
            return new RootBuilder();
        }

        public RootBuilder add(PreProcessor preProcessor) {
            root.preProcessors.add(preProcessor);
            return this;
        }

        public RootBuilder add(SExpression expression) {
            root.expressions.add(expression);
            return this;
        }

        public RootBuilder addIdentifier(String name) {
            root.expressions.add(new Identifier(name));
            return this;
        }

        public RootBuilder addOperator(String operator) {
            root.expressions.add(new Operator(operator));
            return this;
        }

        public RootBuilder addNumber(Double number) {
            root.expressions.add(new DoubleValue(number));
            return this;
        }

        public RootBuilder addNumber(Integer number) {
            root.expressions.add(new IntegerValue(number));
            return this;
        }

        public RootBuilder addBoolean(Boolean value) {
            root.expressions.add(new BooleanValue(value));
            return this;
        }

        public RootBuilder addString(String value) {
            root.expressions.add(new StringExpression(value));
            return this;
        }

        public RootBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
            var call = new Call(subject, new ArrayList<>());
            root.expressions.add(builder.apply(new CallBuilder(call)).build());
            return this;
        }

        public RootBuilder addList(Function<ListBuilder, ListBuilder> builder) {
            var list = new ExpressionList(new ArrayList<>());
            root.expressions.add(builder.apply(new ListBuilder(list)).build());
            return this;
        }

        public RootBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
            var map = new MapExpression(new HashMap<>());
            root.expressions.add(builder.apply(new MapBuilder(map)).build());
            return this;
        }

        public Root build() {
            return root;
        }
    }

    public static class CallBuilder implements ExpressionBuilder<CallBuilder, Call> {
        private final Call call;

        private CallBuilder(Call call) {
            this.call = call;
        }

        public static CallBuilder builder(SExpression subject) {
            return new CallBuilder(new Call(subject, new ArrayList<>()));
        }

        public static CallBuilder builder(String subject) {
            return new CallBuilder(new Call(new Identifier(subject), new ArrayList<>()));
        }

        public static CallBuilder builder(Double subject) {
            return new CallBuilder(new Call(new DoubleValue(subject), new ArrayList<>()));
        }

        public static CallBuilder builder(Integer subject) {
            return new CallBuilder(new Call(new DoubleValue(subject), new ArrayList<>()));
        }

        public static CallBuilder builder(Boolean subject) {
            return new CallBuilder(new Call(new BooleanValue(subject), new ArrayList<>()));
        }

        public static CallBuilder builder() {
            return new CallBuilder(new Call(new Empty(), new ArrayList<>()));
        }

        public CallBuilder add(SExpression expression) {
            call.arguments.add(expression);
            return this;
        }

        public CallBuilder addIdentifier(String name) {
            call.arguments.add(new Identifier(name));
            return this;
        }

        public CallBuilder addOperator(String operator) {
            call.arguments.add(new Operator(operator));
            return this;
        }

        public CallBuilder addNumber(Double number) {
            call.arguments.add(new DoubleValue(number));
            return this;
        }

        public CallBuilder addNumber(Integer number) {
            call.arguments.add(new IntegerValue(number));
            return this;
        }

        public CallBuilder addBoolean(Boolean value) {
            call.arguments.add(new BooleanValue(value));
            return this;
        }

        public CallBuilder addString(String value) {
            call.arguments.add(new StringExpression(value));
            return this;
        }

        public CallBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
            var call = new Call(subject, new ArrayList<>());
            call.arguments.add(builder.apply(new CallBuilder(call)).build());
            return this;
        }

        public CallBuilder addList(Function<ListBuilder, ListBuilder> builder) {
            var list = new ExpressionList(new ArrayList<>());
            call.arguments.add(builder.apply(new ListBuilder(list)).build());
            return this;
        }

        public CallBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
            var map = new MapExpression(new HashMap<>());
            call.arguments.add(builder.apply(new MapBuilder(map)).build());
            return this;
        }

        public Call build() {
            return call;
        }
    }

    public static class ListBuilder implements ExpressionBuilder<ListBuilder, ExpressionList> {
        private final ExpressionList list;

        private ListBuilder(ExpressionList list) {
            this.list = list;
        }

        public static ListBuilder builder() {
            return new ListBuilder(new ExpressionList(new ArrayList<>()));
        }

        @Override
        public ListBuilder add(SExpression expression) {
            list.expressions.add(expression);
            return this;
        }

        public ListBuilder addIdentifier(String name) {
            list.expressions.add(new Identifier(name));
            return this;
        }

        public ListBuilder addOperator(String operator) {
            list.expressions.add(new Operator(operator));
            return this;
        }

        public ListBuilder addNumber(Double number) {
            list.expressions.add(new DoubleValue(number));
            return this;
        }

        public ListBuilder addNumber(Integer number) {
            list.expressions.add(new IntegerValue(number));
            return this;
        }

        public ListBuilder addBoolean(Boolean value) {
            list.expressions.add(new BooleanValue(value));
            return this;
        }

        @Override
        public ListBuilder addString(String value) {
            list.expressions.add(new StringExpression(value));
            return this;
        }

        public ListBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
            var call = new Call(subject, new ArrayList<>());
            list.expressions.add(builder.apply(new CallBuilder(call)).build());
            return this;
        }

        public ListBuilder addList(Function<ListBuilder, ListBuilder> builder) {
            var list = new ExpressionList(new ArrayList<>());
            list.expressions.add(builder.apply(new ListBuilder(list)).build());
            return this;
        }

        public ListBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
            var map = new MapExpression(new HashMap<>());
            list.expressions.add(builder.apply(new MapBuilder(map)).build());
            return this;
        }

        public ExpressionList build() {
            return list;
        }
    }

    public static class MapBuilder {
        private final MapExpression map;

        private MapBuilder(MapExpression map) {
            this.map = map;
        }

        public static MapBuilder builder() {
            return new MapBuilder(new MapExpression(new HashMap<>()));
        }

        public MapBuilder put(SExpression key, SExpression value) {
            map.expressionMap.put(key, value);
            return this;
        }

        public MapBuilder putIdentifier(String name, SExpression value) {
            map.expressionMap.put(new Identifier(name), value);
            return this;
        }

        public MapBuilder putOperator(String operator, SExpression value) {
            map.expressionMap.put(new Operator(operator), value);
            return this;
        }

        public MapBuilder putNumber(Double number, SExpression value) {
            map.expressionMap.put(new DoubleValue(number), value);
            return this;
        }

        public MapBuilder putNumber(Integer number, SExpression value) {
            map.expressionMap.put(new IntegerValue(number), value);
            return this;
        }

        public MapBuilder putBoolean(Boolean value, SExpression right) {
            map.expressionMap.put(new BooleanValue(value), right);
            return this;
        }


        public MapBuilder putString(String value, SExpression right) {
            map.expressionMap.put(new StringExpression(value), right);
            return this;
        }

        public MapExpression build() {
            return map;
        }
    }

    // Parsing

    public static class ParseError extends RuntimeException {
        public ParseError(String message) {
            super(message);
        }
    }

    private static class ErrorListerer implements ANTLRErrorListener {
        private ParseError error;

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            var errorMessage = String.format("error at %d:%d: %s", line, charPositionInLine, msg);
            var symbol = (Token) offendingSymbol;

            if (symbol.getText().equals("<EOF>")) {
                errorMessage += ". Have you forgot to close bracket?";
            }

            error = new ParseError(errorMessage);
        }

        @Override
        public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {

        }

        @Override
        public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {

        }

        @Override
        public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {

        }

        public void throwExceptionIfNeeded() {
            if (error != null) {
                throw error;
            }
        }
    }

    public static Root parse(String code) {
        var lexer = new lispLexer(CharStreams.fromString(code));
        lexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
        var parser = new lispParser(new CommonTokenStream(lexer));
        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);

        var listener = new ErrorListerer();
        parser.addErrorListener(listener);

        var root = visitRoot(parser.root());

        listener.throwExceptionIfNeeded();

        return root;
    }

    public static Root visitRoot(lispParser.RootContext rootContext) {
        return new Root(
                rootContext.preprocessor().stream()
                        .map(LispAST::visitPreProcessor)
                        .toList(),
                rootContext.sExpression().stream()
                        .map(LispAST::visitSExpression)
                        .toList()
        );
    }

    public static PreProcessor visitPreProcessor(lispParser.PreprocessorContext preprocessorContext) {
        if (preprocessorContext instanceof lispParser.MacroContext macroContext) {
            return new Macro(
                    macroContext.name.getText(),
                    macroContext.args.stream()
                            .map(Token::getText)
                            .toList(),
                    visitSExpression(macroContext.substitute)
            );
        }

        return null;
    }

    public static SExpression visitSExpression(lispParser.SExpressionContext expressionContext) {
        if (expressionContext.IDENTIFIER() != null) {
            return new Identifier(expressionContext.IDENTIFIER().getText());
        } else if (expressionContext.OPERATOR() != null) {
            return new Operator(expressionContext.OPERATOR().getText());
        } else if (expressionContext.INTEGER() != null) {
            return new IntegerValue(Integer.parseInt(expressionContext.INTEGER().getText()));
        } else if (expressionContext.FLOAT() != null) {
            return new DoubleValue(Double.parseDouble(expressionContext.FLOAT().getText()));
        } else if (expressionContext.STRING() != null) {
            var value = expressionContext.STRING().getText();
            value = value.substring(1, value.length() - 1);
            return new StringExpression(value);
        } else if (expressionContext.BOOLEAN() != null) {
            return new BooleanValue(expressionContext.BOOLEAN().getText().equals("true"));
        } else if (expressionContext.VOID() != null) {
            return new Void();
        } else if (expressionContext.EMPTY() != null) {
            return new Empty();
        }

        var call = expressionContext.call();
        if (call != null) {
            return visitCall(call);
        }

        var list = expressionContext.list();
        if (list != null) {
            return visitList(list);
        }

        var map = expressionContext.map();
        if (map != null) {
            return visitMap(map);
        }

        return null;
    }

    private static void throwAtToken(Token token, String message) {
        var line = token.getLine();
        var charPositionInLine = token.getCharPositionInLine();

        var msg = String.format("error at %d:%d: %s", line, charPositionInLine, message);

        throw new LispAST.ParseError(msg);
    }

    public static Call visitCall(lispParser.CallContext callContext) {
        var subject = visitSExpression(callContext.subject);

        return new Call(
                subject,
                callContext.sExpression().stream()
                        .skip(1)
                        .map(LispAST::visitSExpression)
                        .toList()
        );
    }

    public static ExpressionList visitList(lispParser.ListContext listContext) {
        return new ExpressionList(
                listContext.sExpression().stream()
                        .map(LispAST::visitSExpression)
                        .toList()
        );
    }

    public static MapExpression visitMap(lispParser.MapContext mapContext) {
        var map = new HashMap<SExpression, SExpression>();

        mapContext.mapEntry().forEach(entry -> map.put(visitSExpression(entry.key), visitSExpression(entry.value)));

        return new MapExpression(map);
    }
}
