package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.MapExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.MapFragment;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;


public class MapToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var map = (MapExpression) expression;

        return new MapFragment(HashMap.ofAll(
                map.getExpressionMap().entrySet()
                        .stream()
                        .map(entry -> new Tuple2<>(
                                SpellConverter.expressionToFragment(entry.getKey()),
                                SpellConverter.expressionToFragment(entry.getValue())
                        )),
                entry -> entry
        ));
    }
}
