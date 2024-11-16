package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.Objects;

public class StringExpression extends SExpression {
    public static final StructEndec<StringExpression> ENDEC = StructEndecBuilder.of(
            StructEndec.STRING.fieldOf("v", StringExpression::getValue),
            StringExpression::new
    );

    private String value;

    public StringExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringExpression that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "StringExpression{\n" +
                "value='" + value + '\'' +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.STRING;
    }

    @Override
    public SExpression deepCopy() {
        return new StringExpression(value);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return equals(other);
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + '"' + value + '"';
    }
}
