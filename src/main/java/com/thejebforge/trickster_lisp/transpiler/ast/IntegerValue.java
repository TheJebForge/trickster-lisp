package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.Objects;

public class IntegerValue extends SExpression {
    public static final StructEndec<IntegerValue> ENDEC = StructEndecBuilder.of(
            StructEndec.INT.fieldOf("v", IntegerValue::getNumber),
            IntegerValue::new
    );

    private int number;

    public IntegerValue(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerValue that)) return false;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        return "Integer{\n" +
                "number=" + number +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.INTEGER;
    }

    @Override
    public SExpression deepCopy() {
        return new IntegerValue(number);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return equals(other);
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + number;
    }
}
