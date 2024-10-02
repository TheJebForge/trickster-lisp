package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.PatternGlyph;
import dev.enjarai.trickster.spell.SpellPart;
import dev.enjarai.trickster.spell.trick.Tricks;

import java.util.List;

public class IfStatementToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        CallUtils.expectArgumentCount(call, 3);

        var condition = call.getArguments().getFirst();
        var trueCase = call.getArguments().get(1);
        var falseCase = call.getArguments().get(2);

        return new SpellPart(
                new PatternGlyph(Tricks.EXECUTE_SAME_SCOPE.getPattern()),
                List.of(
                        new SpellPart(
                                new PatternGlyph(Tricks.IF_ELSE.getPattern()),
                                List.of(
                                        SpellConverter.wrap(SpellConverter.expressionToFragment(condition)),
                                        new SpellPart(SpellConverter.wrap(SpellConverter.expressionToFragment(trueCase))),
                                        new SpellPart(SpellConverter.wrap(SpellConverter.expressionToFragment(falseCase)))
                                )
                        )
                )
        );
    }
}
