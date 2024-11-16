package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;

public abstract class PreProcessor {
    public String toCode(int indent) {
        return toCode(indent, LispUtils.DEFAULT_TAB_SIZE, false);
    }

    public abstract String toCode(int indent, int tabSize, boolean inline);
}
