package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(VectorFragment.class)
public class MixinVectorFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert() {
        var vector = ((VectorFragment) (Object) this).vector();

        return Optional.ofNullable(LispAST.CallBuilder.builder("vec")
                .addNumber(vector.x())
                .addNumber(vector.y())
                .addNumber(vector.z())
                .build());
    }
}
