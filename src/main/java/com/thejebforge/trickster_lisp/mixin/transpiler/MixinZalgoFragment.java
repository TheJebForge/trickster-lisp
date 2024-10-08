package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.ZalgoFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(ZalgoFragment.class)
public class MixinZalgoFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert() {
        return Optional.ofNullable(LispAST.CallBuilder.builder("zalgo")
                .addString(((ZalgoFragment) (Object) this).string())
                .build());
    }
}
