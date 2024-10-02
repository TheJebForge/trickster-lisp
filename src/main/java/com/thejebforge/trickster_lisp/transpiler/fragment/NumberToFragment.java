package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.NumberFragment;

public class NumberToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        if (expression instanceof LispAST.IntegerValue integerValue) {
            return new NumberFragment(integerValue.getNumber());
        } else if (expression instanceof LispAST.DoubleValue doubleValue) {
            return new NumberFragment(doubleValue.getNumber());
        } else {
            throw CallUtils.getConversionError(expression, "How did you even get here?");
        }
    }
}
