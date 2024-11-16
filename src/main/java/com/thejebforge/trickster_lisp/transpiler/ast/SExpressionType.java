package com.thejebforge.trickster_lisp.transpiler.ast;

import com.mojang.serialization.Lifecycle;
import com.thejebforge.trickster_lisp.TricksterLISP;
import io.wispforest.endec.StructEndec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;

public record SExpressionType<T extends SExpression>(StructEndec<T> endec) {
    public static final RegistryKey<Registry<SExpressionType<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(TricksterLISP.id("s_type"));
    public static final Registry<SExpressionType<?>> REGISTRY = FabricRegistryBuilder.from(
            new SimpleRegistry<>(
                    REGISTRY_KEY,
                    Lifecycle.stable()
            )
    ).buildAndRegister();

    public static final SExpressionType<BooleanValue> BOOLEAN = register("bool", BooleanValue.ENDEC);
    public static final SExpressionType<Call> CALL = register("call", Call.ENDEC);
    public static final SExpressionType<DoubleValue> DOUBLE = register("double", DoubleValue.ENDEC);
    public static final SExpressionType<Empty> EMPTY = register("empty", Empty.ENDEC);
    public static final SExpressionType<ExpressionList> LIST = register("list", ExpressionList.ENDEC);
    public static final SExpressionType<Greedy> GREEDY = register("greedy", Greedy.ENDEC);
    public static final SExpressionType<Identifier> IDENTIFIER = register("id", Identifier.ENDEC);
    public static final SExpressionType<IntegerValue> INTEGER = register("int", IntegerValue.ENDEC);
    public static final SExpressionType<MacroCall> MACRO_CALL = register("macro", MacroCall.ENDEC);
    public static final SExpressionType<MapExpression> MAP = register("map", MapExpression.ENDEC);
    public static final SExpressionType<Operator> OPERATOR = register("op", Operator.ENDEC);
    public static final SExpressionType<StringExpression> STRING = register("str", StringExpression.ENDEC);
    public static final SExpressionType<Void> VOID = register("void", Void.ENDEC);

    private static <T extends SExpression> SExpressionType<T> register(String name, StructEndec<T> codec) {
        return Registry.register(REGISTRY, TricksterLISP.id(name), new SExpressionType<>(codec));
    }

    public static void register() {
        // init the class :NoiceCat:
    }
}
