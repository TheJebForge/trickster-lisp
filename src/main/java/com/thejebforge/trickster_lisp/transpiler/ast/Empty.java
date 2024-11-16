package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import dev.enjarai.trickster.EndecTomfoolery;
import io.wispforest.endec.StructEndec;

public class Empty extends SExpression {
    public static final Empty INSTANCE = new Empty();
    public static final StructEndec<Empty> ENDEC = EndecTomfoolery.unit(INSTANCE);

    private Empty() {}

    @Override
    public String toString() {
        return "Empty";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.EMPTY;
    }

    @Override
    public SExpression deepCopy() {
        return new Empty();
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof Empty;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Empty;
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.addIndent(indent, inline) + "_";
    }
}
