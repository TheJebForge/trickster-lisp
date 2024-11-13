package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.ItemTypeFragment;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(ItemTypeFragment.class)
public class MixinItemTypeFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var id = Registries.ITEM.getId(((ItemTypeFragment) (Object) this).item());

        return Optional.ofNullable(LispAST.CallBuilder.builder("item_type")
                .addString(id.toString())
                .build());
    }
}
