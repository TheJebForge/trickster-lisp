package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import dev.enjarai.trickster.spell.Fragment;

public interface ASTToFragment {
    Fragment apply(LispAST.SExpression expression);
}
