package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import dev.enjarai.trickster.EndecTomfoolery;
import io.wispforest.endec.StructEndec;

public class Greedy extends SExpression {
    public static final Greedy INSTANCE = new Greedy();
    public static final StructEndec<Greedy> ENDEC = EndecTomfoolery.unit(INSTANCE);

    private Greedy() {}

    @Override
    public String toString() {
        return "Greedy";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.GREEDY;
    }

    @Override
    public SExpression deepCopy() {
        return new Greedy();
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof Greedy;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Greedy;
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + "...";
    }
}
