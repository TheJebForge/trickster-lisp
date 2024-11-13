package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.UUID;

@Mixin(EntityFragment.class)
public class MixinEntityFragment implements FragmentToAST {
    @Final
    @Shadow(remap = false)
    private UUID uuid;

    @Final
    @Shadow(remap = false)
    private Text name;

    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.ofNullable(LispAST.CallBuilder.builder("entity")
                        .addString(uuid.toString())
                        .addString(name.getString())
                        .build());
    }
}
