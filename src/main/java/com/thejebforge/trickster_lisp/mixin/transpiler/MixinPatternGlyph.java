package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.PatternGlyph;
import dev.enjarai.trickster.spell.trick.Tricks;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

import static com.thejebforge.trickster_lisp.transpiler.SpellConverter.LOAD_ARGUMENT_PART;
import static com.thejebforge.trickster_lisp.transpiler.SpellConverter.OPERATOR_MAPPING;

@Mixin(PatternGlyph.class)
public class MixinPatternGlyph implements FragmentToAST {
    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        var pattern = ((PatternGlyph) (Object) this).pattern();

        var trick = Tricks.lookup(pattern);

        if (trick == null) {
            return Optional.of(CallBuilder.builder("pattern")
                    .addNumber(pattern.toInt())
                    .build());
        } else {
            var callBuilder = CallBuilder.builder("get_glyph");
            var trickId = Tricks.REGISTRY.getId(trick);

            assert trickId != null;
            if (trickId.getPath().startsWith(SpellConverter.LOAD_ARGUMENT_PART)) {
                var number = Integer.parseInt(trickId.getPath().substring(LOAD_ARGUMENT_PART.length()));

                callBuilder.add(CallBuilder.builder("arg")
                        .addNumber(number)
                        .build());
            } else {
                var id = trickId.getNamespace().equals("trickster") ?
                        OPERATOR_MAPPING.containsKey(trickId.getPath()) ?
                                new Operator(OPERATOR_MAPPING.get(trickId.getPath()))
                                : new Identifier(trickId.getPath())
                        : new Identifier(trickId.toString());

                callBuilder.add(id);
            }

            return Optional.of(callBuilder.build());
        }
    }
}
