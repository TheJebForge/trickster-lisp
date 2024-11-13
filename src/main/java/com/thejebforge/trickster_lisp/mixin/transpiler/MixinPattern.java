package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.Pattern;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(Pattern.class)
public class MixinPattern implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var pattern = ((Pattern) (Object) this);

        return Optional.of(LispAST.CallBuilder.builder("pattern_literal")
                .addNumber(pattern.toInt())
                .build());
    }
}
