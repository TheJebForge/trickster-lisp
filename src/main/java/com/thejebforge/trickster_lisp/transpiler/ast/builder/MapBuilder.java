package com.thejebforge.trickster_lisp.transpiler.ast.builder;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.IntegerValue;
import com.thejebforge.trickster_lisp.transpiler.ast.MapExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.StringExpression;

import java.util.HashMap;

public class MapBuilder {
    private final MapExpression map;

    private MapBuilder(MapExpression map) {
        this.map = map;
    }

    public static MapBuilder builder() {
        return new MapBuilder(new MapExpression(new HashMap<>()));
    }

    public MapBuilder put(SExpression key, SExpression value) {
        map.getExpressionMap().put(key, value);
        return this;
    }

    public MapBuilder putIdentifier(String name, SExpression value) {
        map.getExpressionMap().put(new Identifier(name), value);
        return this;
    }

    public MapBuilder putOperator(String operator, SExpression value) {
        map.getExpressionMap().put(new Operator(operator), value);
        return this;
    }

    public MapBuilder putNumber(Double number, SExpression value) {
        map.getExpressionMap().put(new DoubleValue(number), value);
        return this;
    }

    public MapBuilder putNumber(Integer number, SExpression value) {
        map.getExpressionMap().put(new IntegerValue(number), value);
        return this;
    }

    public MapBuilder putBoolean(Boolean value, SExpression right) {
        map.getExpressionMap().put(new BooleanValue(value), right);
        return this;
    }

    public MapBuilder putString(String value, SExpression right) {
        map.getExpressionMap().put(new StringExpression(value), right);
        return this;
    }

    public MapExpression build() {
        return map;
    }
}
