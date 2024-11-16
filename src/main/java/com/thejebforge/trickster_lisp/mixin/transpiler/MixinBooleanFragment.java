package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BooleanFragment.class)
public class MixinBooleanFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.of(new BooleanValue(((BooleanFragment) (Object) this).bool));
    }
}
