package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.TypeFragment;
import net.minecraft.util.Identifier;

public class TypeToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var typeName = CallUtils.getStringArgument(call, 0);
        var split = typeName.split(":");
        var typeId = split.length > 1 ?
                Identifier.of(split[0], split[1])
                : Identifier.of("trickster", split[0]);

        var type = FragmentType.REGISTRY.get(typeId);

        if (type == null)
            throw CallUtils.getConversionError(call, "Unknown type");

        return new TypeFragment(type);
    }
}
