package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.BlockTypeFragment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BlockTypeToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var blockType = CallUtils.getStringArgument(call, 0);
        var blockId = Identifier.tryParse(blockType);

        var block = Registries.BLOCK.get(blockId);

        return new BlockTypeFragment(block);
    }
}
