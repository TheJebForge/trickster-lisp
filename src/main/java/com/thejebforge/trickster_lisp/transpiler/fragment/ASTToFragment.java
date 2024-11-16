package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import dev.enjarai.trickster.spell.Fragment;

public interface ASTToFragment {
    Fragment apply(SExpression expression);
}
