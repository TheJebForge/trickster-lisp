package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.Objects;

public class Operator extends SExpression {
    public static final StructEndec<Operator> ENDEC = StructEndecBuilder.of(
            StructEndec.STRING.fieldOf("op", Operator::getType),
            Operator::new
    );

    private String type;

    public Operator(String name) {
        this.type = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator operator)) return false;
        return Objects.equals(type, operator.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public String toString() {
        return "Operator{\n" +
                "type='" + type + '\'' +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.OPERATOR;
    }

    @Override
    public SExpression deepCopy() {
        return new Operator(type);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return equals(other);
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + type;
    }
}
