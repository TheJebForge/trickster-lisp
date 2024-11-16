package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.EntityTypeFragment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EntityTypeToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var potentialEntityTypeId = CallUtils.getStringArgument(call, 0);
        var entityTypeId = Identifier.of(potentialEntityTypeId);

        var entityType = Registries.ENTITY_TYPE.get(entityTypeId);

        return new EntityTypeFragment(entityType);
    }
}
