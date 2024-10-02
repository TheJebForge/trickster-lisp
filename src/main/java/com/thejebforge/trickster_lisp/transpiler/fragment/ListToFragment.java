package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.ListFragment;

public class ListToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var list = (LispAST.ExpressionList) expression;

        return new ListFragment(list.getExpressions().stream()
                .map(SpellConverter::expressionToFragment)
                .toList());
    }
}
