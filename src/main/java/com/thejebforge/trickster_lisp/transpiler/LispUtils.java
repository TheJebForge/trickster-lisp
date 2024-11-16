package com.thejebforge.trickster_lisp.transpiler;

import com.thejebforge.trickster_lisp.parser.lispLexer;
import com.thejebforge.trickster_lisp.parser.lispParser;
import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Empty;
import com.thejebforge.trickster_lisp.transpiler.ast.ExpressionList;
import com.thejebforge.trickster_lisp.transpiler.ast.Greedy;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.IntegerValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Macro;
import com.thejebforge.trickster_lisp.transpiler.ast.MacroCall;
import com.thejebforge.trickster_lisp.transpiler.ast.MapExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.PreProcessor;
import com.thejebforge.trickster_lisp.transpiler.ast.Root;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.StringExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Void;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;
import java.util.stream.Collectors;

// Parsing and utils for AST stuff
public abstract class LispUtils {
    public static final int DEFAULT_TAB_SIZE = 4;

    public static String addIndent(int indent, boolean inline) {
        return inline ? "" : " ".repeat(indent);
    }

    public static String toCallCode(int indent, int tabSize, boolean inline, String subjectCode, List<SExpression> arguments) {
        List<String> codeArguments = arguments.stream()
                .map(expression -> expression.toCode(indent + tabSize, tabSize, true))
                .toList();

        int argumentsLength = codeArguments.stream()
                .map(String::length)
                .reduce(0, (result, len) -> result + len + 1);

        if (argumentsLength > 40) {
            return addIndent(indent, inline) + '(' + subjectCode + '\n'
                    + codeArguments.stream()
                    .map(arg -> addIndent(indent + tabSize, false) + arg)
                    .collect(Collectors.joining("\n"))
                    + '\n' + " ".repeat(indent) + ')';
        } else {
            return addIndent(indent, inline) + '(' + subjectCode
                    + (codeArguments.isEmpty() ? "" : ' ')
                    + String.join(" ", codeArguments) + ')';
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
                        .map(LispUtils::visitPreProcessor)
                        .collect(Collectors.toCollection(ArrayList::new)),
                rootContext.sExpression().stream()
                        .map(c -> visitSExpression(c, false))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public static PreProcessor visitPreProcessor(lispParser.PreprocessorContext preprocessorContext) {
        if (preprocessorContext instanceof lispParser.MacroContext macroContext) {
            return new Macro(
                    macroContext.name.getText(),
                    macroContext.args.stream()
                            .map(Token::getText)
                            .toList(),
                    macroContext.GREEDY() != null,
                    visitSExpression(macroContext.substitute, true)
            );
        }

        return null;
    }

    public static SExpression visitSExpression(lispParser.SExpressionContext expressionContext, boolean inMacro) {
        if (expressionContext.GREEDY() != null && !inMacro) {
            throwAtToken(
                    expressionContext,
                    "Greedy argument operator is not allowed outside of macro definitions"
            );
        }

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
            return Void.INSTANCE;
        } else if (expressionContext.EMPTY() != null) {
            return Empty.INSTANCE;
        } else if (expressionContext.GREEDY() != null) {
            return Greedy.INSTANCE;
        }

        var call = expressionContext.call();
        if (call != null) {
            return visitCall(call, inMacro);
        }

        var macroCall = expressionContext.macroCall();
        if (macroCall != null) {
            return visitMacroCall(macroCall, inMacro);
        }

        var list = expressionContext.list();
        if (list != null) {
            return visitList(list, inMacro);
        }

        var map = expressionContext.map();
        if (map != null) {
            return visitMap(map, inMacro);
        }

        return null;
    }

    private static void throwAtToken(ParserRuleContext scope, String message) {
        var line = scope.start.getLine();
        var charPositionInLine = scope.start.getCharPositionInLine();

        var msg = String.format("error at %d:%d: %s\nin '%s'", line, charPositionInLine, message,
                scope.children.stream().map(ParseTree::getText).collect(Collectors.joining(" ")));

        throw new LispUtils.ParseError(msg);
    }

    private static void checkForGreedy(ParserRuleContext scope, List<SExpression> scopeElements) {
        if (scopeElements.stream().filter(e -> e instanceof Greedy).count() > 1) {
            throwAtToken(scope, "More than one greedy argument operator in scope");
        }
    }

    public static Call visitCall(lispParser.CallContext callContext, boolean inMacro) {
        var subject = visitSExpression(callContext.subject, inMacro);
        var args = callContext.sExpression().stream()
                .skip(1)
                .map(c -> visitSExpression(c, inMacro))
                .toList();

        if (subject instanceof Greedy) {
            throwAtToken(callContext.subject, "Greedy argument operator cannot be the subject of the call");
        }
        checkForGreedy(callContext, args);

        return new Call(
                subject,
                args
        );
    }

    public static MacroCall visitMacroCall(lispParser.MacroCallContext callContext, boolean inMacro) {
        var args = callContext.sExpression().stream()
                .map(c -> visitSExpression(c, inMacro))
                .toList();

        checkForGreedy(callContext, args);

        return new MacroCall(
                callContext.name.getText(),
                args
        );
    }

    public static ExpressionList visitList(lispParser.ListContext listContext, boolean inMacro) {
        var list = listContext.sExpression().stream()
                .map(c -> visitSExpression(c, inMacro))
                .toList();

        checkForGreedy(listContext, list);

        return new ExpressionList(
                list
        );
    }

    private static SExpression yellAtGreedy(ParserRuleContext context, SExpression expression) {
        if (expression instanceof Greedy) {
            throwAtToken(context, "Greedy argument operator cannot be used as keys or values of a map");
        }

        return expression;
    }

    public static MapExpression visitMap(lispParser.MapContext mapContext, boolean inMacro) {
        var map = new HashMap<SExpression, SExpression>();

        mapContext.mapEntry().forEach(entry -> map.put(
                yellAtGreedy(entry.key, visitSExpression(entry.key, inMacro)),
                yellAtGreedy(entry.value, visitSExpression(entry.value, inMacro))
        ));

        return new MapExpression(map);
    }
}
