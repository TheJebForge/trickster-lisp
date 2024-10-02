package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;

public class BooleanToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var bool = (LispAST.BooleanValue) expression;

        return new BooleanFragment(bool.getValue());
    }
}
