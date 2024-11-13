package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.MapFragment;
import dev.enjarai.trickster.util.Hamt;

import java.util.HashMap;

public class MapToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var map = (LispAST.MapExpression) expression;

        var fragmentMap = new HashMap<Fragment, Fragment>();
        map.getExpressionMap().forEach((k, v) -> fragmentMap.put(
                SpellConverter.expressionToFragment(k),
                SpellConverter.expressionToFragment(v)
        ));

        return new MapFragment(Hamt.fromMap(fragmentMap));
    }
}
