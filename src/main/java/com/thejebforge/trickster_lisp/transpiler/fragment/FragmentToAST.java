package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;

import java.util.Optional;

public interface FragmentToAST {
    default Optional<LispAST.SExpression> trickster_lisp$convert() {
        return Optional.empty();
    }
}
