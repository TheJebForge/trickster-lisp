package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.DimensionFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(DimensionFragment.class)
public class MixinDimensionFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert() {
        var id = ((DimensionFragment) (Object) this).world().getValue().toString();

        return Optional.ofNullable(LispAST.CallBuilder.builder("dimension")
                .addString(id)
                .build());
    }
}
