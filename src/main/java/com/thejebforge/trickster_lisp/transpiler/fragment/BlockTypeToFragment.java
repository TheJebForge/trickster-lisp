package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.BlockTypeFragment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BlockTypeToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var blockType = CallUtils.getStringArgument(call, 0);
        var blockId = Identifier.tryParse(blockType);

        var block = Registries.BLOCK.get(blockId);

        return new BlockTypeFragment(block);
    }
}
