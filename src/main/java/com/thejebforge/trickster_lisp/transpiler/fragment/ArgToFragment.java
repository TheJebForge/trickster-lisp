package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.PatternGlyph;
import dev.enjarai.trickster.spell.SpellPart;
import dev.enjarai.trickster.spell.trick.Tricks;
import net.minecraft.util.Identifier;

public class ArgToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var index = CallUtils.getIntegerArgument(call, 0);

        if (index < 1 || index > 8)
            throw CallUtils.getConversionError(call, "Argument index is out of range");

        var argTrickId = Identifier.of("trickster", SpellConverter.LOAD_ARGUMENT_PART + index);
        var trick = Tricks.REGISTRY.get(argTrickId);

        if (trick == null)
            throw CallUtils.getConversionError(call, "Argument trick not found (this is not supposed to happen)");

        return new SpellPart(new PatternGlyph(trick.getPattern()));
    }
}
