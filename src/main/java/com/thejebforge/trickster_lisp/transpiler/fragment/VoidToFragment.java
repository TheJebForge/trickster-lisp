package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.VoidFragment;

public class VoidToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        return new VoidFragment();
    }
}
