package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.DimensionFragment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DimensionToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var dimensionName = CallUtils.getStringArgument(call, 0);
        var dimensionId = Identifier.tryParse(dimensionName);

        if (dimensionId == null)
            throw CallUtils.getConversionError(expression, "Invalid dimension name");

        var world = RegistryKey.of(RegistryKeys.WORLD, dimensionId);
        return new DimensionFragment(world);
    }
}
