package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import dev.enjarai.trickster.EndecTomfoolery;
import io.wispforest.endec.StructEndec;

public class Void extends SExpression {
    public static final Void INSTANCE = new Void();
    public static final StructEndec<Void> ENDEC = EndecTomfoolery.unit(INSTANCE);

    private Void() {}

    @Override
    public String toString() {
        return "Void";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.VOID;
    }

    @Override
    public SExpression deepCopy() {
        return new Void();
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof Void;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Void;
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + "void";
    }
}
