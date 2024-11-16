package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.Objects;

public class DoubleValue extends SExpression {
    public static final StructEndec<DoubleValue> ENDEC = StructEndecBuilder.of(
            StructEndec.DOUBLE.fieldOf("v", DoubleValue::getNumber),
            DoubleValue::new
    );

    private double number;

    public DoubleValue(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleValue that)) return false;
        return Double.compare(number, that.number) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        return "Double{\n" +
                "number=" + number +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.DOUBLE;
    }

    @Override
    public SExpression deepCopy() {
        return new DoubleValue(number);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return equals(other);
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + number;
    }
}
