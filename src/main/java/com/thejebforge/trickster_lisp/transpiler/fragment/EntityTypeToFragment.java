package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.EntityTypeFragment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EntityTypeToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var potentialEntityTypeId = CallUtils.getStringArgument(call, 0);
        var entityTypeId = Identifier.of(potentialEntityTypeId);

        var entityType = Registries.ENTITY_TYPE.get(entityTypeId);

        return new EntityTypeFragment(entityType);
    }
}
