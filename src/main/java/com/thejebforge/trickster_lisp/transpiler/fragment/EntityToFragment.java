package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.EntityFragment;
import net.minecraft.text.Text;
import net.minecraft.util.Uuids;

import java.util.UUID;

public class EntityToFragment implements ASTToFragment {
    @Override
    public Fragment apply(LispAST.SExpression expression) {
        var call = (LispAST.Call) expression;

        var potentialUuid = CallUtils.getStringArgument(call, 0);
        var uuid = UUID.fromString(potentialUuid);

        var potentialText = CallUtils.getStringArgument(call, 1);
        var text = Text.of(potentialText);

        return new EntityFragment(uuid, text);

    }
}
