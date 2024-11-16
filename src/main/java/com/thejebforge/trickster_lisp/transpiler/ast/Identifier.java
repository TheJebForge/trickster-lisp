package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.Objects;

public class Identifier extends SExpression {
    public static final StructEndec<Identifier> ENDEC = StructEndecBuilder.of(
            StructEndec.STRING.fieldOf("name", Identifier::getName),
            Identifier::new
    );

    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identifier that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Identifier{\n" +
                "name='" + name + '\'' +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.IDENTIFIER;
    }

    @Override
    public SExpression deepCopy() {
        return new Identifier(name);
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return equals(other);
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + name;
    }
}
