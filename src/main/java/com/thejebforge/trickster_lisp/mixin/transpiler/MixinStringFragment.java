package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.StringFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(StringFragment.class)
public class MixinStringFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert() {
        return Optional.of(new LispAST.StringExpression(((StringFragment) (Object) this).value()));
    }
}
