package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.TypeFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(TypeFragment.class)
public class MixinTypeFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var id = FragmentType.REGISTRY.getId(((TypeFragment) (Object) this).typeType());

        return Optional.ofNullable(LispAST.CallBuilder.builder("type")
                .addString(id != null ? id.toString() : "unknown")
                .build());
    }
}
