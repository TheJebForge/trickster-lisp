package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.ZalgoFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(ZalgoFragment.class)
public class MixinZalgoFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.ofNullable(CallBuilder.builder("zalgo")
                .addNumber(((ZalgoFragment) (Object) this).index())
                .build());
    }
}
