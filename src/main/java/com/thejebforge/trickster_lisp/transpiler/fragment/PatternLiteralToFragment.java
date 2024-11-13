package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;

public class PatternLiteralToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var encodedPattern = CallUtils.getIntegerArgument(call, 0);

        return Pattern.from(encodedPattern);
    }
}
