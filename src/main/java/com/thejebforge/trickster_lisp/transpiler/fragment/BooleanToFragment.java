package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;

public class BooleanToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var bool = (BooleanValue) expression;

        return BooleanFragment.of(bool.getValue());
    }
}
