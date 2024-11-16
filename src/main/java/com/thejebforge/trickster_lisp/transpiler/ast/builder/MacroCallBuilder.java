package com.thejebforge.trickster_lisp.transpiler.ast.builder;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
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

public class MacroCallBuilder implements ExpressionBuilder<MacroCallBuilder, MacroCall> {
    private final MacroCall call;

    private MacroCallBuilder(MacroCall call) {
        this.call = call;
    }

    public static MacroCallBuilder builder(String name) {
        return new MacroCallBuilder(new MacroCall(name, new ArrayList<>()));
    }

    public MacroCallBuilder add(SExpression expression) {
        call.getArguments().add(expression);
        return this;
    }

    public MacroCallBuilder addIdentifier(String name) {
        call.getArguments().add(new Identifier(name));
        return this;
    }

    public MacroCallBuilder addOperator(String operator) {
        call.getArguments().add(new Operator(operator));
        return this;
    }

    public MacroCallBuilder addNumber(Double number) {
        call.getArguments().add(new DoubleValue(number));
        return this;
    }

    public MacroCallBuilder addNumber(Integer number) {
        call.getArguments().add(new IntegerValue(number));
        return this;
    }

    public MacroCallBuilder addBoolean(Boolean value) {
        call.getArguments().add(new BooleanValue(value));
        return this;
    }

    public MacroCallBuilder addString(String value) {
        call.getArguments().add(new StringExpression(value));
        return this;
    }

    public MacroCallBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
        this.call.getArguments().add(builder.apply(CallBuilder.builder(subject)).build());
        return this;
    }

    @Override
    public MacroCallBuilder addMacroCall(String name, Function<MacroCallBuilder, MacroCallBuilder> builder) {
        call.getArguments().add(builder.apply(MacroCallBuilder.builder(name)).build());
        return this;
    }

    public MacroCallBuilder addList(Function<ListBuilder, ListBuilder> builder) {
        call.getArguments().add(builder.apply(ListBuilder.builder()).build());
        return this;
    }

    public MacroCallBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
        call.getArguments().add(builder.apply(MapBuilder.builder()).build());
        return this;
    }

    public MacroCall build() {
        return call;
    }
}
