package com.thejebforge.trickster_lisp.transpiler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.thejebforge.trickster_lisp.TricksterLISP;
import com.thejebforge.trickster_lisp.transpiler.fragment.*;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.PatternGlyph;
import dev.enjarai.trickster.spell.SpellPart;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.trick.Tricks;
import net.minecraft.util.Identifier;

import java.util.*;

public abstract class SpellConverter {
    private static String getTrickId(Trick trick) {
        return Objects.requireNonNull(Tricks.REGISTRY.getId(trick)).getPath();
    }

    public static final BiMap<String, String> OPERATOR_MAPPING = HashBiMap.create();

    static {
        OPERATOR_MAPPING.put(getTrickId(Tricks.ADD), "+");
        OPERATOR_MAPPING.put(getTrickId(Tricks.SUBTRACT), "-");
        OPERATOR_MAPPING.put(getTrickId(Tricks.MULTIPLY), "*");
        OPERATOR_MAPPING.put(getTrickId(Tricks.DIVIDE), "/");
        OPERATOR_MAPPING.put(getTrickId(Tricks.MODULO), "%");
        OPERATOR_MAPPING.put(getTrickId(Tricks.IF_ELSE), "?");
        OPERATOR_MAPPING.put(getTrickId(Tricks.EQUALS), "==");
        OPERATOR_MAPPING.put(getTrickId(Tricks.NOT_EQUALS), "!=");
        OPERATOR_MAPPING.put(getTrickId(Tricks.GREATER_THAN), ">");
        OPERATOR_MAPPING.put(getTrickId(Tricks.LESSER_THAN), "<");
        OPERATOR_MAPPING.put(getTrickId(Tricks.ALL), "&");
        OPERATOR_MAPPING.put(getTrickId(Tricks.ANY), "|");
        OPERATOR_MAPPING.put(getTrickId(Tricks.NONE), "!|");
    }

    public static final String LOAD_ARGUMENT_PART = "load_argument_";

    public static final Map<String, ASTToFragment> CALL_ID_CONVERTERS = new HashMap<>(){{
        put("block_type", new BlockTypeToFragment());
        put("dimension", new DimensionToFragment());
        put("entity", new EntityToFragment());
        put("entity_type", new EntityTypeToFragment());
        put("item_type", new ItemTypeToFragment());
        put("slot", new SlotToFragment());
        put("pattern", new PatternToFragment());
        put("type", new TypeToFragment());
        put("vec", new VectorToFragment());
        put("void", new VoidToFragment());
        put("zalgo", new ZalgoToFragment());
        put("arg", new ArgToFragment());
        put("if", new IfStatementToFragment());
        put("get_glyph", new GetGlyphToFragment());
    }};

    public static final Map<Class<?>, ASTToFragment> PRIMITIVE_CONVERTERS = new HashMap<>(){{
        put(LispAST.IntegerValue.class, new NumberToFragment());
        put(LispAST.DoubleValue.class, new NumberToFragment());
        put(LispAST.BooleanValue.class, new BooleanToFragment());
        put(LispAST.ExpressionList.class, new ListToFragment());
        put(LispAST.Void.class, new VoidToFragment());
    }};

    public static LispAST.Root spellToAST(SpellPart spell) {
        TricksterLISP.LOGGER.info(spell.toString());

        return LispAST.RootBuilder.builder()
                .add(fragmentToExpression(spell))
                .build()
                .simplifyRoot();
    }

    public static LispAST.SExpression fragmentToExpression(Fragment frag) {
        try {
            if (frag instanceof FragmentToAST toAST) {
                var potentialAST = toAST.trickster_lisp$convert();

                if (potentialAST.isPresent()) {
                    return potentialAST.get();
                }
            }
        } catch (ClassCastException ignored) {}

        throw new IllegalArgumentException("Unknown fragment type: " + frag.getClass().getName());
    }

    public static boolean isIdentifierValid(LispAST.Identifier id) {
        var splits = id.getName().split(":");

        var trick_id = splits.length > 1 ?
                net.minecraft.util.Identifier.of(splits[0], splits[1])
                : net.minecraft.util.Identifier.of("trickster", splits[0]);

        return SpellConverter.CALL_ID_CONVERTERS.containsKey(id.getName())
                || Tricks.REGISTRY.containsId(trick_id);
    }

    public static boolean isOperatorValid(LispAST.Operator op) {
        return SpellConverter.OPERATOR_MAPPING.containsValue(op.getType());
    }

    // TODO: Don't accept strings in AST -> SpellPart

    private static Pattern idToTrickPattern(LispAST.SExpression parent, LispAST.Identifier id) {
        var split = id.getName().split(":");
        var trickId = split.length > 1 ?
                Identifier.of(split[0], split[1])
                : Identifier.of("trickster", split[0]);

        var trick = Tricks.REGISTRY.get(trickId);

        if (trick == null)
            throw CallUtils.getConversionError(parent, "Unknown trick");

        return trick.getPattern();
    }

    public static Fragment expressionToFragment(LispAST.SExpression expr) {
        // Map operators back to proper functions
        if (expr instanceof LispAST.Call call && call.getSubject() instanceof LispAST.Operator op) {
            var callBuilder = LispAST.CallBuilder.builder(OPERATOR_MAPPING.inverse().get(op.getType()));
            call.getArguments().forEach(callBuilder::add);
            expr = callBuilder.build();
        }

        // Now actually map things
        if (expr instanceof LispAST.Call call && call.getSubject() instanceof LispAST.Identifier callId) {
            if (CALL_ID_CONVERTERS.containsKey(callId.getName())) {
                var converter = CALL_ID_CONVERTERS.get(callId.getName());

                return converter.apply(call);
            } else {
                return new SpellPart(
                        new PatternGlyph(idToTrickPattern(call, callId)),
                        call.getArguments().stream()
                                .map(SpellConverter::expressionToFragment)
                                .map(SpellConverter::wrap)
                                .toList()
                );
            }
        } else if (PRIMITIVE_CONVERTERS.containsKey(expr.getClass())) {
            var converter = PRIMITIVE_CONVERTERS.get(expr.getClass());

            return converter.apply(expr);
        } else if (expr instanceof LispAST.Identifier id) {
            return new SpellPart(new PatternGlyph(idToTrickPattern(expr, id)));
        } else if (expr instanceof LispAST.Operator op) {
            var underlyingId = new LispAST.Identifier(OPERATOR_MAPPING.inverse().get(op.getType()));
            return new SpellPart(new PatternGlyph(idToTrickPattern(expr, underlyingId)));
        } else if (expr instanceof LispAST.Call mainCall && mainCall.getSubject() instanceof LispAST.Call childCall) {
            TricksterLISP.LOGGER.info("Got inner circle! main: {}, child: {}", mainCall, childCall);
            return new SpellPart(
                    wrap(expressionToFragment(childCall)),
                    mainCall.getArguments().stream()
                            .map(SpellConverter::expressionToFragment)
                            .map(SpellConverter::wrap)
                            .toList()
            );
        } else if (expr instanceof LispAST.Call call) {
            return new SpellPart(
                    expressionToFragment(call.getSubject()),
                    call.getArguments().stream()
                            .map(SpellConverter::expressionToFragment)
                            .map(SpellConverter::wrap)
                            .toList()
            );
        } else {
            throw CallUtils.getConversionError(expr, "Couldn't convert expression to fragment");
        }
    }

    public static SpellPart wrap(Fragment fragment) {
        if (!(fragment instanceof SpellPart)) {
            return new SpellPart(fragment);
        } else return (SpellPart) fragment;
    }

    public static SpellPart astToSpell(LispAST.Root root) {
        root = root.runPreProcessors();

        if (root.expressions().isEmpty()) {
            return new SpellPart();
        } else if (root.expressions().size() == 1) {
            var expression = root.expressions().getFirst();

            return wrap(expressionToFragment(expression));
        } else {
            return new SpellPart(
                    new PatternGlyph(),
                    root.expressions().stream()
                            .map(SpellConverter::expressionToFragment)
                            .map(SpellConverter::wrap)
                            .toList()
            );
        }
    }
}
