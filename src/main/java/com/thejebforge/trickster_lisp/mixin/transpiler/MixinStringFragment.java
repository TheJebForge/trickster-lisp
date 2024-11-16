package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.StringExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.StringFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(StringFragment.class)
public class MixinStringFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.of(new StringExpression(((StringFragment) (Object) this).value()));
    }
}
