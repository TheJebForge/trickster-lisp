package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import dev.enjarai.trickster.spell.Fragment;

@FunctionalInterface
public interface CustomFragmentToAST {
    SExpression apply(Fragment fragment);
}
