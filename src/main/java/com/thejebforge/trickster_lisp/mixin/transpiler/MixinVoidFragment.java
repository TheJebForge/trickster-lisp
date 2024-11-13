package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(VoidFragment.class)
public class MixinVoidFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.ofNullable(LispAST.CallBuilder.builder("void")
                .build());
    }
}
