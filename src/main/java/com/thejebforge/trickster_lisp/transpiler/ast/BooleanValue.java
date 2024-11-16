package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.Objects;

public class BooleanValue extends SExpression {
    public static final StructEndec<BooleanValue> ENDEC = StructEndecBuilder.of(
            Endec.BOOLEAN.fieldOf("v", BooleanValue::getValue),
            BooleanValue::new
    );

    private Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanValue that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "value=" + value +
                '}';
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.BOOLEAN;
    }

    @Override
    public SExpression deepCopy() {
        return new BooleanValue(value);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return equals(other);
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + value;
    }
}
