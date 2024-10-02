package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BooleanFragment.class)
public class MixinBooleanFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert() {
        return Optional.of(new LispAST.BooleanValue(((BooleanFragment) (Object) this).bool()));
    }
}
