package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.PatternGlyph;

public class EmptyToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        return new PatternGlyph();
    }
}
