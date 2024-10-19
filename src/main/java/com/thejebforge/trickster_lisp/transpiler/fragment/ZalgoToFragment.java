package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.ZalgoFragment;

public class ZalgoToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var zalgo = CallUtils.getIntegerArgument(call, 0);

        return new ZalgoFragment(zalgo);
    }
}
