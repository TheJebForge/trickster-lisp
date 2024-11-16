package com.thejebforge.trickster_lisp.transpiler.ast.builder;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Empty;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Void;

import java.util.function.Function;

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
        return addCall(Empty.INSTANCE, builder);
    }

    T addMacroCall(String name, Function<MacroCallBuilder, MacroCallBuilder> builder);

    T addList(Function<ListBuilder, ListBuilder> builder);

    T addMap(Function<MapBuilder, MapBuilder> builder);

    R build();
}
