package com.thejebforge.trickster_lisp.item.component;

import com.thejebforge.trickster_lisp.transpiler.ast.Macro;
import io.vavr.collection.HashSet;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record MacroDefinitionComponent(List<Macro> macros) {
    public static final StructEndec<MacroDefinitionComponent> ENDEC = StructEndecBuilder.of(
            Macro.ENDEC.listOf().fieldOf("m", MacroDefinitionComponent::macros),
            MacroDefinitionComponent::new
    );

    public static void appendTooltip(ItemStack stack, List<Text> tooltip) {
        if (stack.contains(ModComponents.MACRO_DEFINITION_COMPONENT)) {
            var macroComponent = stack.get(ModComponents.MACRO_DEFINITION_COMPONENT);

            if (macroComponent != null && !macroComponent.macros().isEmpty()) {
                tooltip.add(Text.translatable(
                        "trickster_lisp.tooltip.macros",
                        macroComponent.macros().size()
                ).formatted(Formatting.DARK_GRAY));
            }
        }
    }

    private static Optional<List<Macro>> getMacroList(ItemStack stack) {
        return Optional.ofNullable(stack)
                .filter(stack2 -> stack2.contains(ModComponents.MACRO_DEFINITION_COMPONENT))
                .map(stack2 -> stack2.get(ModComponents.MACRO_DEFINITION_COMPONENT))
                .map(MacroDefinitionComponent::macros);
    }

    public static Optional<HashSet<Macro>> findUserDefinedMacros(PlayerEntity user, String type) {
        var capability = user.accessoriesCapability();

        if (capability == null)
            return Optional.empty();

        var ringContainer = capability.getContainers().get(type);

        if (ringContainer == null)
            return Optional.empty();

        var result = HashSet.<Macro>empty();

        for (var pair : ringContainer.getAccessories()) {
            result = result.addAll(getMacroList(pair.getSecond()).orElseGet(Collections::emptyList));
        }

        return Optional.of(result);
    }

    public static final Endec<HashSet<Macro>> MACRO_SET_ENDEC = Macro.ENDEC.listOf().xmap(HashSet::ofAll, HashSet::toJavaList);
}
