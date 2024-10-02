package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.SlotFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(SlotFragment.class)
public class MixinSlotFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert() {
        var fragment = ((SlotFragment) (Object) this);

        if (fragment.source().isPresent()) {
            var blockPos = fragment.source().get();

            return Optional.ofNullable(LispAST.CallBuilder.builder("slot")
                    .addNumber(fragment.slot())
                    .addCall("vec", call -> call
                            .addNumber(blockPos.getX())
                            .addNumber(blockPos.getY())
                            .addNumber(blockPos.getZ()))
                    .build());
        } else {
            return Optional.ofNullable(LispAST.CallBuilder.builder("slot")
                    .addNumber(fragment.slot())
                    .build());
        }
    }
}
