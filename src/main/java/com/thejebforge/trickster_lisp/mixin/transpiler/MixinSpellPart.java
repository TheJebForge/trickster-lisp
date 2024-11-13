package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.fragment.FragmentToAST;
import dev.enjarai.trickster.spell.PatternGlyph;
import dev.enjarai.trickster.spell.SpellPart;
import dev.enjarai.trickster.spell.trick.Tricks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

import static com.thejebforge.trickster_lisp.transpiler.SpellConverter.LOAD_ARGUMENT_PART;
import static com.thejebforge.trickster_lisp.transpiler.SpellConverter.OPERATOR_MAPPING;

@Mixin(SpellPart.class)
public class MixinSpellPart implements FragmentToAST {
    @Unique
    private LispAST.SExpression spellPartToExpression(SpellPart part, boolean preserveSpellParts) {
        if (part.getGlyph() instanceof PatternGlyph pattern) {
            if (pattern.pattern().isEmpty()) {
                var builder = LispAST.CallBuilder.builder();

                part.getSubParts().forEach(subpart -> {
                    var potentialAST = ((FragmentToAST) (Object) subpart).trickster_lisp$convert(preserveSpellParts);

                    if (potentialAST.isPresent()) {
                        builder.add(potentialAST.get());
                    } else {
                        builder.add(new LispAST.Identifier("unknown"));
                    }
                });

                return builder.build();
            }

            var trick = Tricks.lookup(pattern.pattern());
            var id = Tricks.REGISTRY.getId(trick);

            // load_argument_# to (arg #)
            if (id != null && id.getPath().startsWith(LOAD_ARGUMENT_PART)) {
                var number = Integer.parseInt(id.getPath().substring(LOAD_ARGUMENT_PART.length()));

                return LispAST.CallBuilder.builder("arg")
                        .addNumber(number)
                        .build();
            }

            // (execute_same_scope (? <cond> (<true case>) (<false case>))) to (if <cond> <true_case> <false_case>)
            if (Tricks.EXECUTE_SAME_SCOPE.getPattern().equals(pattern.pattern())
                    && part.subParts.size() == 1
                    && part.subParts.getFirst().glyph instanceof PatternGlyph innerGlyph
                    && Tricks.IF_ELSE.getPattern().equals(innerGlyph.pattern())
            ) {
                // We're close, just check if second and third subpart is nested
                var ifElsePart = part.subParts.getFirst();

                if (ifElsePart.subParts.size() == 3
                        && ifElsePart.subParts.get(1).glyph instanceof SpellPart trueCase
                        && ifElsePart.subParts.get(2).glyph instanceof SpellPart falseCase
                ) {
                    return LispAST.CallBuilder.builder("if")
                            .add(spellPartToExpression(ifElsePart.subParts.getFirst(), preserveSpellParts))
                            .add(spellPartToExpression(trueCase, preserveSpellParts))
                            .add(spellPartToExpression(falseCase, preserveSpellParts))
                            .build();
                }
            }

            if (id == null) {
                return LispAST.CallBuilder.builder("pattern")
                        .addNumber(pattern.pattern().toInt())
                        .build();
            }

            LispAST.SExpression idString = id.getNamespace().equals("trickster") ?
                                    OPERATOR_MAPPING.containsKey(id.getPath()) ?
                                            new LispAST.Operator(OPERATOR_MAPPING.get(id.getPath()))
                                            : new LispAST.Identifier(id.getPath())
                                    : new LispAST.Identifier(id.toString());

            if (part.subParts.isEmpty()) {
                return idString;
            }

            var builder = LispAST.CallBuilder.builder(idString);
            part.getSubParts().forEach(subpart -> builder.add(spellPartToExpression(subpart, preserveSpellParts)));
            return builder.build();
        } else {
            LispAST.SExpression subject = new LispAST.Empty();

            var potentialAST = ((FragmentToAST) part.glyph).trickster_lisp$convert(preserveSpellParts);

            if (potentialAST.isPresent()) {
                subject = potentialAST.get();
            }

            if (part.getSubParts().isEmpty() && !preserveSpellParts) {
                return subject;
            } else {
                var builder = LispAST.CallBuilder.builder(subject);

                part.getSubParts().forEach(subpart -> builder.add(spellPartToExpression(subpart, preserveSpellParts)));

                return builder.build();
            }
        }
    }

    @Override
    public Optional<LispAST.SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.ofNullable(spellPartToExpression((SpellPart) (Object) this, preserveSpellParts));
    }
}
