package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.SpellPart;

public class GetGlyphToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        CallUtils.expectArgumentCount(call, 1);

        var fragment = SpellConverter.expressionToFragment(call.getArguments().getFirst());

        if (fragment instanceof SpellPart spellPart) {
            return spellPart.glyph;
        } else return fragment;
    }
}
