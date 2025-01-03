package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.builder.ListBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.fragment.ListFragment;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(ListFragment.class)
public class MixinListFragment implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var builder = ListBuilder.builder();

        ((ListFragment) (Object) this).fragments().forEach(fragment -> {
            var potentialAST = ((FragmentToAST) fragment).trickster_lisp$convert(true);

            if (potentialAST.isPresent()) {
                builder.add(potentialAST.get());
            } else {
                builder.add(new Identifier("unknown"));
            }
        });

        return Optional.of(builder.build());
    }
}
