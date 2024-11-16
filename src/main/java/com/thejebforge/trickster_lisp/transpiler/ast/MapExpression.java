package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapExpression extends SExpression {
    public static final StructEndec<MapExpression> ENDEC = StructEndecBuilder.of(
            Endec.map(
                    SExpression.ENDEC,
                    SExpression.ENDEC
            ).fieldOf("map", MapExpression::getExpressionMap),
            MapExpression::new
    );

    private HashMap<SExpression, SExpression> expressionMap;

    public MapExpression(Map<SExpression, SExpression> expressionMap) {
        this.expressionMap = new HashMap<>(expressionMap);
    }

    public HashMap<SExpression, SExpression> getExpressionMap() {
        return expressionMap;
    }

    public void setExpressionMap(HashMap<SExpression, SExpression> expressionMap) {
        this.expressionMap = expressionMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapExpression that)) return false;
        return Objects.equals(expressionMap, that.expressionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expressionMap);
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.MAP;
    }

    @Override
    public SExpression deepCopy() {
        var newMap = new HashMap<SExpression, SExpression>();

        expressionMap.forEach((k, v) -> newMap.put(k.deepCopy(), v.deepCopy()));

        return new MapExpression(newMap);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof MapExpression;
    }

    @Override
    public String toCode(int indent, int tabSize, boolean inline) {
        if (expressionMap.isEmpty()) {
            return LispUtils.addIndent(indent, inline) + "{}";
        } else {
            return LispUtils.addIndent(indent, inline) + "{" + (expressionMap.size() == 1 ? "" : '\n')
                    + expressionMap.entrySet().stream()
                    .map(e ->
                            e.getKey().toCode(indent + tabSize, tabSize, expressionMap.size() == 1)
                                    + " : "
                                    + e.getValue().toCode(indent + tabSize, tabSize, true))
                    .collect(Collectors.joining(",\n")) + '}';
        }
    }
}
