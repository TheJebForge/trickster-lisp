package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.MapFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(MapFragment.class)
public class MixinMapFragment implements FragmentToAST {
    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var builder = LispAST.MapBuilder.builder();

        ((MapFragment) (Object) this).map().forEach(entry -> {
            var keyAST = ((FragmentToAST) entry.getKey()).trickster_lisp$convert(true)
                    .orElse(new LispAST.Identifier("unknown"));
            var valueAST = ((FragmentToAST) entry.getValue()).trickster_lisp$convert(true)
                    .orElse(new LispAST.Identifier("unknown"));

            builder.put(keyAST, valueAST);
        });

        return Optional.of(builder.build());
    }
}
