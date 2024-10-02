package com.thejebforge.trickster_lisp.transpiler;

import com.thejebforge.trickster_lisp.parser.lispLexer;
import com.thejebforge.trickster_lisp.parser.lispParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

// This one is manual since ANTLR does CST instead
public abstract class LispAST {
    private static final int DEFAULT_TAB_SIZE = 4;

    public record Root(List<SExpression> expressions) {
        public Root simplifyRoot() {
            if (expressions.size() == 1
                    && expressions.getFirst() instanceof Call call
                    && call.subject instanceof Void
            ) {
                return new Root(call.arguments);
            } else {
                return this;
            }
        }

        @Override
        public String toString() {
            return "Root{\n" +
                    "expressions=" + expressions +
                    "\n}";
        }

        public String toCode() {
            return toCode(DEFAULT_TAB_SIZE);
        }

        public String toCode(int tabSize) {
            return expressions.stream()
                    .map(e -> e.toCode(0, tabSize, false))
                    .collect(Collectors.joining("\n"));
        }
    }

    public abstract static class SExpression {
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

        public String toCode(int indent, int tabSize, boolean inline) {
            return addIndent(indent, inline) + "void";
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

        public String toCode(int indent, int tabSize, boolean inline) {
            if (expressions.isEmpty()) {
                return (inline ? "" : " ".repeat(indent)) + "[]";
            } else {
                return (inline ? "" : " ".repeat(indent)) + "[\n"
                        + expressions.stream()
                        .map(e -> e.toCode(indent + tabSize, tabSize, false))
                        .collect(Collectors.joining(",\n"))
                        + '\n' + " ".repeat(indent) + ']';
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
        R build();
    }

    public static class RootBuilder implements ExpressionBuilder<RootBuilder, Root> {
        private final Root root;

        private RootBuilder() {
            this.root = new Root(new ArrayList<>());
        }

        public static RootBuilder builder() {
            return new RootBuilder();
        }

        @Override
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

        @Override
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
            return new CallBuilder(new Call(new Void(), new ArrayList<>()));
        }

        @Override
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

        @Override
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

        public ExpressionList build() {
            return list;
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
        return new Root(rootContext.sExpression().stream().map(LispAST::visitSExpression).toList());
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
        }

        var call = expressionContext.call();
        if (call != null) {
            return visitCall(call);
        }

        var list = expressionContext.list();
        if (list != null) {
            return visitList(list);
        }

        return null;
    }

    private static void throwAtToken(Token token, String message) {
        var line = token.getLine();
        var charPositionInLine = token.getCharPositionInLine();

        var msg = String.format("error at %d:%d: %s", line, charPositionInLine, message);

        throw new LispAST.ParseError(msg);
    }

    private static void validateExpression(Token token, SExpression expression) {
        if (expression instanceof Identifier id) {
            if (!SpellConverter.isIdentifierValid(id)) {
                throwAtToken(token, String.format("unrecognized function '%s'", id.getName()));
            }
        } else if (expression instanceof Operator op) {
            if (!SpellConverter.isOperatorValid(op)) {
                throwAtToken(token, String.format("unrecognized operator '%s'", op.getType()));
            }
        }
    }

    public static Call visitCall(lispParser.CallContext callContext) {
        var subject = visitSExpression(callContext.subject);

        // Verify trick exists
        validateExpression(callContext.subject.start, subject);

        return new Call(
                subject,
                callContext.sExpression().stream()
                        .skip(1)
                        .map(expressionContext -> {
                            var expr = visitSExpression(expressionContext);
                            validateExpression(expressionContext.start, expr);
                            return expr;
                        })
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
}
