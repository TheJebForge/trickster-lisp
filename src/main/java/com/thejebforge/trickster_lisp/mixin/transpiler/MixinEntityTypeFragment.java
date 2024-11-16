package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.EntityTypeFragment;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(EntityTypeFragment.class)
public class MixinEntityTypeFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var id = Registries.ENTITY_TYPE.getId(((EntityTypeFragment) (Object) this).entityType());

        return Optional.ofNullable(CallBuilder.builder("entity_type")
                .addString(id.toString())
                .build());
    }
}
