package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.mojang.datafixers.util.Either;
import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.SlotFragment;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.UUID;

public class SlotToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var slotNumber = CallUtils.getIntegerArgument(call, 0);

        if (call.getArguments().size() == 1) {
            return new SlotFragment(slotNumber, Optional.empty());
        } else {
            if (call.getArguments().get(1) instanceof Call) {
                var vec = CallUtils.getNamedCallArgument(call, 1, "vec");

                var x = CallUtils.getIntegerArgument(vec, 0);
                var y = CallUtils.getIntegerArgument(vec, 1);
                var z = CallUtils.getIntegerArgument(vec, 2);

                var blockPos = new BlockPos(x, y, z);

                return new SlotFragment(slotNumber, Optional.of(Either.left(blockPos)));
            } else {
                var uuid = CallUtils.getStringArgument(call, 1);

                return new SlotFragment(slotNumber, Optional.of(Either.right(UUID.fromString(uuid))));
            }

        }
    }
}
