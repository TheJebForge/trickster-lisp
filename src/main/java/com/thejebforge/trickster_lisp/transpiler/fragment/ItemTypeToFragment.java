package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.ItemTypeFragment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemTypeToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var itemType = CallUtils.getStringArgument(call, 0);
        var itemId = Identifier.tryParse(itemType);

        var item = Registries.ITEM.get(itemId);

        return new ItemTypeFragment(item);
    }
}
