package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;

import java.util.Optional;

public interface FragmentToAST {
    default Optional<LispAST.SExpression> trickster_lisp$convert() {
        return trickster_lisp$convert(false);
    }

    default Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.empty();
    }
}
