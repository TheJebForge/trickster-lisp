package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.ItemTypeFragment;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(ItemTypeFragment.class)
public class MixinItemTypeFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var id = Registries.ITEM.getId(((ItemTypeFragment) (Object) this).item());

        return Optional.ofNullable(CallBuilder.builder("item_type")
                .addString(id.toString())
                .build());
    }
}
