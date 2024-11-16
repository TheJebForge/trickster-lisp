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

public class ListBuilder implements ExpressionBuilder<ListBuilder, ExpressionList> {
    private final ExpressionList list;

    private ListBuilder(ExpressionList list) {
        this.list = list;
    }

    public static ListBuilder builder() {
        return new ListBuilder(new ExpressionList(new ArrayList<>()));
    }

    @Override
    public ListBuilder add(SExpression expression) {
        list.getExpressions().add(expression);
        return this;
    }

    public ListBuilder addIdentifier(String name) {
        list.getExpressions().add(new Identifier(name));
        return this;
    }

    public ListBuilder addOperator(String operator) {
        list.getExpressions().add(new Operator(operator));
        return this;
    }

    public ListBuilder addNumber(Double number) {
        list.getExpressions().add(new DoubleValue(number));
        return this;
    }

    public ListBuilder addNumber(Integer number) {
        list.getExpressions().add(new IntegerValue(number));
        return this;
    }

    public ListBuilder addBoolean(Boolean value) {
        list.getExpressions().add(new BooleanValue(value));
        return this;
    }

    @Override
    public ListBuilder addString(String value) {
        list.getExpressions().add(new StringExpression(value));
        return this;
    }

    public ListBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
        list.getExpressions().add(builder.apply(CallBuilder.builder(subject)).build());
        return this;
    }

    @Override
    public ListBuilder addMacroCall(String name, Function<MacroCallBuilder, MacroCallBuilder> builder) {
        list.getExpressions().add(builder.apply(MacroCallBuilder.builder(name)).build());
        return this;
    }

    public ListBuilder addList(Function<ListBuilder, ListBuilder> builder) {
        list.getExpressions().add(builder.apply(ListBuilder.builder()).build());
        return this;
    }

    public ListBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
        list.getExpressions().add(builder.apply(MapBuilder.builder()).build());
        return this;
    }

    public ExpressionList build() {
        return list;
    }
}
