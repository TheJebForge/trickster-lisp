package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.SlotFragment;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class SlotToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var slotNumber = CallUtils.getIntegerArgument(call, 0);

        if (call.getArguments().size() == 1) {
            return new SlotFragment(slotNumber, Optional.empty());
        } else {
            var vec = CallUtils.getNamedCallArgument(call, 1, "vec");

            var x = CallUtils.getIntegerArgument(vec, 0);
            var y = CallUtils.getIntegerArgument(vec, 1);
            var z = CallUtils.getIntegerArgument(vec, 2);

            return new SlotFragment(slotNumber, Optional.of(new BlockPos(x, y, z)));
        }
    }
}
