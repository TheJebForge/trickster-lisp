package com.thejebforge.trickster_lisp.transpiler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.thejebforge.trickster_lisp.transpiler.ast.BooleanValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Call;
import com.thejebforge.trickster_lisp.transpiler.ast.Macro;
import com.thejebforge.trickster_lisp.transpiler.ast.MacroCall;
import com.thejebforge.trickster_lisp.transpiler.ast.PreProcessor;
import com.thejebforge.trickster_lisp.transpiler.ast.builder.CallBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.DoubleValue;
import com.thejebforge.trickster_lisp.transpiler.ast.Empty;
import com.thejebforge.trickster_lisp.transpiler.ast.ExpressionList;
import com.thejebforge.trickster_lisp.transpiler.ast.Greedy;
import com.thejebforge.trickster_lisp.transpiler.ast.IntegerValue;
import com.thejebforge.trickster_lisp.transpiler.ast.MapExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Operator;
import com.thejebforge.trickster_lisp.transpiler.ast.Root;
import com.thejebforge.trickster_lisp.transpiler.ast.builder.RootBuilder;
import com.thejebforge.trickster_lisp.transpiler.ast.SExpression;
import com.thejebforge.trickster_lisp.transpiler.ast.Void;
import com.thejebforge.trickster_lisp.transpiler.fragment.*;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.PatternGlyph;
import dev.enjarai.trickster.spell.SpellPart;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.VoidFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.trick.Tricks;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Stream;

public abstract class SpellConverter {
    private static String getTrickId(Trick<?> trick) {
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

    public static final Set<String> FRAGMENT_IDS = new HashSet<>() {{
        add("block_type");
        add("dimension");
        add("entity");
        add("entity_type");
        add("item_type");
        add("slot");
        add("pattern");
        add("pattern_literal");
        add("type");
        add("vec");
        add("void");
        add("zalgo");
    }};

    public static final Map<String, ASTToFragment> CALL_ID_CONVERTERS = new HashMap<>(){{
        put("block_type", new BlockTypeToFragment());
        put("dimension", new DimensionToFragment());
        put("entity", new EntityToFragment());
        put("entity_type", new EntityTypeToFragment());
        put("item_type", new ItemTypeToFragment());
        put("slot", new SlotToFragment());
        put("pattern", new PatternToFragment());
        put("pattern_literal", new PatternLiteralToFragment());
        put("type", new TypeToFragment());
        put("vec", new VectorToFragment());
        put("void", new VoidToFragment());
        put("zalgo", new ZalgoToFragment());
        put("arg", new ArgToFragment());
        put("get_glyph", new GetGlyphToFragment());
    }};

    public static final Map<Class<?>, ASTToFragment> PRIMITIVE_CONVERTERS = new HashMap<>(){{
        put(IntegerValue.class, new NumberToFragment());
        put(DoubleValue.class, new NumberToFragment());
        put(BooleanValue.class, new BooleanToFragment());
        put(ExpressionList.class, new ListToFragment());
        put(MapExpression.class, new MapToFragment());
        put(Void.class, new VoidToFragment());
        put(Empty.class, new EmptyToFragment());
        put(Greedy.class, new GreedyToException());
        put(MacroCall.class, new MacroCallToException());
    }};
    public static Map<FragmentType<?>, CustomFragmentToAST> CUSTOM_FRAGMENT_CONVERTERS = new HashMap<>();

    public static SExpression wrapExpressionIfNeeded(Fragment spell) {
        var expression = fragmentToExpression(spell);

        if (spell instanceof SpellPart) {
            if (expression instanceof Call call) {
                if (call.getSubject() instanceof com.thejebforge.trickster_lisp.transpiler.ast.Identifier id
                    && FRAGMENT_IDS.contains(id.getName())) {
                    return CallBuilder.builder(expression).build();
                }

                return expression;
            }

            return CallBuilder.builder(expression).build();
        }

        return expression;
    }

    public static Root spellToAST(Fragment spell, List<Macro> savedMacros, List<Macro> macros) {
        return RootBuilder.builder()
                .add(wrapExpressionIfNeeded(spell))
                .build()
                .reverseMacros(Stream.concat(macros.stream(), savedMacros.stream()).toList())
                .appendMacros(savedMacros)
                .simplifyRoot();
    }

    public static SExpression fragmentToExpression(Fragment frag) {
        return fragmentToExpression(frag, false);
    }

    public static SExpression fragmentToExpression(Fragment frag, boolean preserveSpellParts) {
        try {
            if (frag instanceof FragmentToAST toAST) {
                var potentialAST = toAST.trickster_lisp$convert(preserveSpellParts);

                if (potentialAST.isPresent()) {
                    return potentialAST.get();
                }
            }
        } catch (ClassCastException ignored) {}

        var ty = frag.type();
        if (CUSTOM_FRAGMENT_CONVERTERS.containsKey(ty)) {
            var converter = CUSTOM_FRAGMENT_CONVERTERS.get(ty);
            return converter.apply(frag);
        }

        throw new IllegalArgumentException("Unknown fragment type: " + frag.getClass().getName());
    }

    public static boolean isIdentifierValid(com.thejebforge.trickster_lisp.transpiler.ast.Identifier id) {
        var splits = id.getName().split(":");

        var trick_id = splits.length > 1 ?
                net.minecraft.util.Identifier.of(splits[0], splits[1])
                : net.minecraft.util.Identifier.of("trickster", splits[0]);

        return SpellConverter.CALL_ID_CONVERTERS.containsKey(id.getName())
                || Tricks.REGISTRY.containsId(trick_id);
    }

    public static boolean isOperatorValid(Operator op) {
        return SpellConverter.OPERATOR_MAPPING.containsValue(op.getType());
    }

    // TODO: Don't accept strings in AST -> SpellPart

    private static Pattern idToTrickPattern(SExpression parent, com.thejebforge.trickster_lisp.transpiler.ast.Identifier id) {
        var split = id.getName().split(":");
        var trickId = split.length > 1 ?
                Identifier.of(split[0], split[1])
                : Identifier.of("trickster", split[0]);

        var trick = Tricks.REGISTRY.get(trickId);

        if (trick == null)
            throw CallUtils.getConversionError(parent, "Unknown trick");

        return trick.getPattern();
    }

    public static Fragment expressionToFragment(SExpression expr) {
        // Map operators back to proper functions
        if (expr instanceof Call call && call.getSubject() instanceof Operator op) {
            var mappedOperator = OPERATOR_MAPPING.inverse().get(op.getType());

            if (mappedOperator == null)
                throw CallUtils.getConversionError(op, "Unknown operator");

            var callBuilder = CallBuilder.builder(mappedOperator);
            call.getArguments().forEach(callBuilder::add);
            expr = callBuilder.build();
        }

        // Now actually map things
        if (expr instanceof Call call && call.getSubject() instanceof com.thejebforge.trickster_lisp.transpiler.ast.Identifier callId) {
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
        } else if (expr instanceof com.thejebforge.trickster_lisp.transpiler.ast.Identifier id) {
            return new SpellPart(new PatternGlyph(idToTrickPattern(expr, id)));
        } else if (expr instanceof Operator op) {
            var underlyingId = new com.thejebforge.trickster_lisp.transpiler.ast.Identifier(OPERATOR_MAPPING.inverse().get(op.getType()));
            return new SpellPart(new PatternGlyph(idToTrickPattern(expr, underlyingId)));
//        } else if (expr instanceof LispAST.Call mainCall && mainCall.getSubject() instanceof LispAST.Call childCall) {
//            return new SpellPart(
//                    expressionToFragment(childCall),
//                    mainCall.getArguments().stream()
//                            .map(SpellConverter::expressionToFragment)
//                            .map(SpellConverter::wrap)
//                            .toList()
//            );
        } else if (expr instanceof Call call) {
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

    public static Fragment astToFinalFragment(Root root, List<Macro> macros) {
        root = root
                .runPreProcessors(macros.stream().map(PreProcessor.class::cast).toList())
                .runPreProcessors();

        if (root.expressions().isEmpty()) {
            return new VoidFragment();
        } else if (root.expressions().size() == 1) {
            var expression = root.expressions().getFirst();

            return expressionToFragment(expression);
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
