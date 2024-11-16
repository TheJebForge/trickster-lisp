package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.builder.MapBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.MapFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(MapFragment.class)
public class MixinMapFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var builder = MapBuilder.builder();

        ((MapFragment) (Object) this).map().forEach(entry -> {
            var keyAST = ((FragmentToAST) entry._1()).trickster_lisp$convert(true)
                    .orElse(new Identifier("unknown"));
            var valueAST = ((FragmentToAST) entry._2()).trickster_lisp$convert(true)
                    .orElse(new Identifier("unknown"));

            builder.put(keyAST, valueAST);
        });

        return Optional.of(builder.build());
    }
}
