package com.thejebforge.trickster_lisp.mixin.transpiler;

import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.Empty;
import com.thejebforge.trickster_lisp.transpiler.ast.Identifier;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
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
    private SExpression spellPartToExpression(SpellPart part, boolean preserveSpellParts) {
        if (part.getGlyph() instanceof PatternGlyph pattern) {
            if (pattern.pattern().isEmpty()) {
                var builder = CallBuilder.builder();

                part.getSubParts().forEach(subpart -> {
                    var potentialAST = ((FragmentToAST) (Object) subpart).trickster_lisp$convert(preserveSpellParts);

                    if (potentialAST.isPresent()) {
                        builder.add(potentialAST.get());
                    } else {
                        builder.add(new Identifier("unknown"));
                    }
                });

                return builder.build();
            }

            var trick = Tricks.lookup(pattern.pattern());
            var id = Tricks.REGISTRY.getId(trick);

            // load_argument_# to (arg #)
            if (id != null && id.getPath().startsWith(LOAD_ARGUMENT_PART)) {
                var number = Integer.parseInt(id.getPath().substring(LOAD_ARGUMENT_PART.length()));

                return CallBuilder.builder("arg")
                        .addNumber(number)
                        .build();
            }

            if (id == null) {
                return CallBuilder.builder("pattern")
                        .addNumber(pattern.pattern().toInt())
                        .build();
            }

            SExpression idString = id.getNamespace().equals("trickster") ?
                                    OPERATOR_MAPPING.containsKey(id.getPath()) ?
                                            new Operator(OPERATOR_MAPPING.get(id.getPath()))
                                            : new Identifier(id.getPath())
                                    : new Identifier(id.toString());

            if (part.subParts.isEmpty()) {
                return idString;
            }

            var builder = CallBuilder.builder(idString);
            part.getSubParts().forEach(subpart -> builder.add(spellPartToExpression(subpart, preserveSpellParts)));
            return builder.build();
        } else {
            SExpression subject = Empty.INSTANCE;

            var potentialAST = ((FragmentToAST) part.glyph).trickster_lisp$convert(preserveSpellParts);

            if (potentialAST.isPresent()) {
                subject = potentialAST.get();
            }

            // TODO: double parenthesies on primitives get lost
            if (part.getSubParts().isEmpty() && !preserveSpellParts) {
                if (subject instanceof Call call) {
                    if (call.getSubject() instanceof Identifier id
                            && SpellConverter.FRAGMENT_IDS.contains(id.getName())) {
                        return subject;
                    }
                } else {
                    return subject;
                }
            }

            var builder = CallBuilder.builder(subject);

            part.getSubParts().forEach(subpart -> builder.add(spellPartToExpression(subpart, preserveSpellParts)));

            return builder.build();
        }
    }

    @Override
    public Optional<SExpression> trickster_lisp$convert(boolean preserveSpellParts) {
        return Optional.ofNullable(spellPartToExpression((SpellPart) (Object) this, preserveSpellParts));
    }
}
