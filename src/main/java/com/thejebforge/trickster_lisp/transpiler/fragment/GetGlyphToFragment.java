package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.SpellPart;

public class GetGlyphToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        CallUtils.expectArgumentCount(call, 1);

        var fragment = SpellConverter.expressionToFragment(call.getArguments().getFirst());

        if (fragment instanceof SpellPart spellPart) {
            return spellPart.glyph;
        } else return fragment;
    }
}
