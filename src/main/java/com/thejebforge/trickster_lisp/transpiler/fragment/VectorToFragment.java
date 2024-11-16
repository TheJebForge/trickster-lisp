package com.thejebforge.trickster_lisp.transpiler.fragment;

import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.fragment.VectorFragment;
import org.joml.Vector3d;

public class VectorToFragment implements ASTToFragment {
    @Override
    public Fragment apply(SExpression expression) {
        var call = (Call) expression;

        var x = CallUtils.getDoubleArgument(call, 0);
        var y = CallUtils.getDoubleArgument(call, 1);
        var z = CallUtils.getDoubleArgument(call, 2);

        return new VectorFragment(new Vector3d(x, y, z));
    }
}
