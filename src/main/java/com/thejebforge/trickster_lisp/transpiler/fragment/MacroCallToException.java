package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;

public class MacroCallToException implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        throw CallUtils.getConversionError(expression, "Unrecognized macro call");
    }
}
