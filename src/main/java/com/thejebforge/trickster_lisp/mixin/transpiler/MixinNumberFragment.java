package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(NumberFragment.class)
public class MixinNumberFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.of(new LispAST.DoubleValue(((NumberFragment) (Object) this).number()));
    }
}
