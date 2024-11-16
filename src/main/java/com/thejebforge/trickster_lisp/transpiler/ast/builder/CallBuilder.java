package com.thejebforge.trickster_lisp.transpiler.ast.builder;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Empty;
import com.thejebforge.trickster_lisp.transpiler.ast.ExpressionList;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.IntegerValue;
import com.thejebforge.trickster_lisp.transpiler.ast.MacroCall;
import com.thejebforge.trickster_lisp.transpiler.ast.MapExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.StringExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class CallBuilder implements ExpressionBuilder<CallBuilder, Call> {
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
        return new CallBuilder(new Call(Empty.INSTANCE, new ArrayList<>()));
    }

    public CallBuilder add(SExpression expression) {
        call.getArguments().add(expression);
        return this;
    }

    public CallBuilder addIdentifier(String name) {
        call.getArguments().add(new Identifier(name));
        return this;
    }

    public CallBuilder addOperator(String operator) {
        call.getArguments().add(new Operator(operator));
        return this;
    }

    public CallBuilder addNumber(Double number) {
        call.getArguments().add(new DoubleValue(number));
        return this;
    }

    public CallBuilder addNumber(Integer number) {
        call.getArguments().add(new IntegerValue(number));
        return this;
    }

    public CallBuilder addBoolean(Boolean value) {
        call.getArguments().add(new BooleanValue(value));
        return this;
    }

    public CallBuilder addString(String value) {
        call.getArguments().add(new StringExpression(value));
        return this;
    }

    public CallBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
        this.call.getArguments().add(builder.apply(CallBuilder.builder(subject)).build());
        return this;
    }

    @Override
    public CallBuilder addMacroCall(String name, Function<MacroCallBuilder, MacroCallBuilder> builder) {
        call.getArguments().add(builder.apply(MacroCallBuilder.builder(name)).build());
        return this;
    }

    public CallBuilder addList(Function<ListBuilder, ListBuilder> builder) {
        call.getArguments().add(builder.apply(ListBuilder.builder()).build());
        return this;
    }

    public CallBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
        call.getArguments().add(builder.apply(MapBuilder.builder()).build());
        return this;
    }

    public Call build() {
        return call;
    }
}
