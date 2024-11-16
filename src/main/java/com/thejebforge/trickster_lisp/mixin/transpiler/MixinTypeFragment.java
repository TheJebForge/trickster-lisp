package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.TypeFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(TypeFragment.class)
public class MixinTypeFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var id = FragmentType.REGISTRY.getId(((TypeFragment) (Object) this).typeType());

        return Optional.ofNullable(CallBuilder.builder("type")
                .addString(id != null ?
                        id.getNamespace().equals("trickster") ?
                                id.getPath()
                                : id.toString()
                        : "unknown")
                .build());
    }
}
