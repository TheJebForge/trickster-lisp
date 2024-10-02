package com.thejebforge.trickster_lisp.transpiler.util;

import com.thejebforge.trickster_lisp.transpiler.LispAST;

public abstract class CallUtils {
    public static class ConversionError extends RuntimeException {
        public ConversionError(String message) {
            super(message);
        }
    }

    public static boolean matchCall(LispAST.Call call, String id) {
        return call.getSubject() instanceof LispAST.Identifier identifier && identifier.getName().equals(id);
    }

    public static void expectArgumentCount(LispAST.Call call, int argumentCount) {
        if (call.getArguments().size() < argumentCount)
            throw getConversionError(call, String.format("Expected at least %d arguments", argumentCount));
    }

    public static String getStringArgument(LispAST.Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof LispAST.StringExpression string) {
            return string.getValue();
        } else {
            throw getConversionError(call, String.format("Expected string at %d argument", index));
        }
    }

    public static Integer getIntegerArgument(LispAST.Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof LispAST.IntegerValue integerValue) {
            return integerValue.getNumber();
        } else if (call.getArguments().get(index) instanceof LispAST.DoubleValue doubleValue) {
            return (int) doubleValue.getNumber();
        } else {
            throw getConversionError(call, String.format("Expected integer at %d argument", index));
        }
    }

    public static Double getDoubleArgument(LispAST.Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof LispAST.IntegerValue integerValue) {
            return (double) integerValue.getNumber();
        } else if (call.getArguments().get(index) instanceof LispAST.DoubleValue doubleValue) {
            return doubleValue.getNumber();
        } else {
            throw getConversionError(call, String.format("Expected float at %d argument", index));
        }
    }

    public static LispAST.Call getCallArgument(LispAST.Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof LispAST.Call callArgument) {
            return callArgument;
        } else {
            throw getConversionError(call, String.format("Expected call at %d argument", index));
        }
    }

    public static LispAST.Call getNamedCallArgument(LispAST.Call call, int index, String name) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof LispAST.Call callArgument) {
            if (!CallUtils.matchCall(callArgument, name))
                throw getConversionError(call, String.format("Expected '%s' call at %d argument", name, index));

            return callArgument;
        } else {
            throw getConversionError(call, String.format("Expected call at %d argument", index));
        }
    }

    public static ConversionError getConversionError(LispAST.SExpression expression, String reason) {
        return new ConversionError(String.format("Couldn't convert expression to spell: %s\n%s", reason, expression.toCode(0)));
    }
}
