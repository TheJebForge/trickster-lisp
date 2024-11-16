package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.SlotFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(SlotFragment.class)
public class MixinSlotFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var fragment = ((SlotFragment) (Object) this);

        if (fragment.source().isPresent()) {
            var blockPos = fragment.source().get();

            return Optional.ofNullable((SExpression) blockPos.map(
                    bp -> CallBuilder.builder("slot")
                            .addNumber(fragment.slot())
                            .addCall("vec", call -> call
                                    .addNumber(bp.getX())
                                    .addNumber(bp.getY())
                                    .addNumber(bp.getZ()))
                            .build(),
                    uuid -> CallBuilder.builder("slot")
                            .addNumber(fragment.slot())
                            .addString(uuid.toString())
            ));
        } else {
            return Optional.ofNullable(CallBuilder.builder("slot")
                    .addNumber(fragment.slot())
                    .build());
        }
    }
}
