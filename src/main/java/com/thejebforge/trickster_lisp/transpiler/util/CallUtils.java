package com.thejebforge.trickster_lisp.transpiler.util;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.IntegerValue;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.StringExpression;

public abstract class CallUtils {
    public static class ConversionError extends RuntimeException {
        public ConversionError(String message) {
            super(message);
        }
    }

    public static boolean matchCall(Call call, String id) {
        return call.getSubject() instanceof Identifier identifier && identifier.getName().equals(id);
    }

    public static void expectArgumentCount(Call call, int argumentCount) {
        if (call.getArguments().size() < argumentCount)
            throw getConversionError(call, String.format("Expected at least %d arguments", argumentCount));
    }

    public static String getStringArgument(Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof StringExpression string) {
            return string.getValue();
        } else {
            throw getConversionError(call, String.format("Expected string at %d argument", index));
        }
    }

    public static Integer getIntegerArgument(Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof IntegerValue integerValue) {
            return integerValue.getNumber();
        } else if (call.getArguments().get(index) instanceof DoubleValue doubleValue) {
            return (int) doubleValue.getNumber();
        } else {
            throw getConversionError(call, String.format("Expected integer at %d argument", index));
        }
    }

    public static Double getDoubleArgument(Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof IntegerValue integerValue) {
            return (double) integerValue.getNumber();
        } else if (call.getArguments().get(index) instanceof DoubleValue doubleValue) {
            return doubleValue.getNumber();
        } else {
            throw getConversionError(call, String.format("Expected float at %d argument", index));
        }
    }

    public static Call getCallArgument(Call call, int index) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof Call callArgument) {
            return callArgument;
        } else {
            throw getConversionError(call, String.format("Expected call at %d argument", index));
        }
    }

    public static Call getNamedCallArgument(Call call, int index, String name) {
        if (call.getArguments().size() < (index + 1))
            throw getConversionError(call, String.format("Expected at least %d arguments", index + 1));

        if (call.getArguments().get(index) instanceof Call callArgument) {
            if (!CallUtils.matchCall(callArgument, name))
                throw getConversionError(call, String.format("Expected '%s' call at %d argument", name, index));

            return callArgument;
        } else {
            throw getConversionError(call, String.format("Expected call at %d argument", index));
        }
    }

    public static ConversionError getConversionError(SExpression expression, String reason) {
        return new ConversionError(String.format("Couldn't convert expression to spell: %s\n%s", reason, expression.toCode(0)));
    }
}
