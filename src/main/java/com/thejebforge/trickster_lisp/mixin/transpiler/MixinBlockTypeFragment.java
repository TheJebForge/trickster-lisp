package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.BlockTypeFragment;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BlockTypeFragment.class)
public class MixinBlockTypeFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var id = Registries.BLOCK.getId(((BlockTypeFragment) (Object) this).block());

        return Optional.of(CallBuilder.builder("block_type")
                .addString(id.toString())
                .build());
    }
}
