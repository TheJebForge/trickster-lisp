package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;

import java.util.Optional;

public interface FragmentToAST {
    default Optional<SExpression> trickster_lisp$convert() {
        return trickster_lisp$convert(false);
    }

    default Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.empty();
    }
}
