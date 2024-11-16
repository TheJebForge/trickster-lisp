package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.ExpressionList;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.ListFragment;

public class ListToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var list = (ExpressionList) expression;

        return new ListFragment(list.getExpressions().stream()
                .map(SpellConverter::expressionToFragment)
                .toList());
    }
}
