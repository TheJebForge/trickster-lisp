package com.thejebforge.trickster_lisp.transpiler.ast.builder;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.ExpressionList;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.IntegerValue;
import com.thejebforge.trickster_lisp.transpiler.ast.MapExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.PreProcessor;
import com.thejebforge.trickster_lisp.transpiler.ast.Root;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.StringExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RootBuilder implements ExpressionBuilder<RootBuilder, Root> {
    private final Root root;

    private RootBuilder() {
        this.root = new Root(new ArrayList<>(), new ArrayList<>());
    }

    public static RootBuilder builder() {
        return new RootBuilder();
    }

    public RootBuilder add(PreProcessor preProcessor) {
        root.preProcessors().add(preProcessor);
        return this;
    }

    public RootBuilder add(SExpression expression) {
        root.expressions().add(expression);
        return this;
    }

    public RootBuilder addIdentifier(String name) {
        root.expressions().add(new Identifier(name));
        return this;
    }

    public RootBuilder addOperator(String operator) {
        root.expressions().add(new Operator(operator));
        return this;
    }

    public RootBuilder addNumber(Double number) {
        root.expressions().add(new DoubleValue(number));
        return this;
    }

    public RootBuilder addNumber(Integer number) {
        root.expressions().add(new IntegerValue(number));
        return this;
    }

    public RootBuilder addBoolean(Boolean value) {
        root.expressions().add(new BooleanValue(value));
        return this;
    }

    public RootBuilder addString(String value) {
        root.expressions().add(new StringExpression(value));
        return this;
    }

    public RootBuilder addCall(SExpression subject, Function<CallBuilder, CallBuilder> builder) {
        root.expressions().add(builder.apply(CallBuilder.builder(subject)).build());
        return this;
    }

    @Override
    public RootBuilder addMacroCall(String name, Function<MacroCallBuilder, MacroCallBuilder> builder) {
        root.expressions().add(builder.apply(MacroCallBuilder.builder(name)).build());
        return this;
    }

    public RootBuilder addList(Function<ListBuilder, ListBuilder> builder) {
        root.expressions().add(builder.apply(ListBuilder.builder()).build());
        return this;
    }

    public RootBuilder addMap(Function<MapBuilder, MapBuilder> builder) {
        root.expressions().add(builder.apply(MapBuilder.builder()).build());
        return this;
    }

    public Root build() {
        return root;
    }
}
