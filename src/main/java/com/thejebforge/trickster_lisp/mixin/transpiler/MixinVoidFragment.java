package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.Void;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(VoidFragment.class)
public class MixinVoidFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.of(Void.INSTANCE);
    }
}
