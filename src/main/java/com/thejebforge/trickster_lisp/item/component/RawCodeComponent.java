package com.thejebforge.trickster_lisp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public record RawCodeComponent(String content) {
    public static final Codec<RawCodeComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("content").forGetter(RawCodeComponent::content)
    ).apply(instance, RawCodeComponent::new));

    public static void appendTooltip(ItemStack stack, List<Text> tooltip) {
        if (stack.contains(ModComponents.RAW_CODE_COMPONENT)) {
            RawCodeComponent codeComponent = stack.get(ModComponents.RAW_CODE_COMPONENT);

            if (codeComponent != null && !codeComponent.content().trim().isEmpty()) {
                String content = codeComponent.content().trim();

                tooltip.add(Text.translatable("trickster_lisp.tooltip.code").formatted(Formatting.DARK_GRAY));

                Arrays.stream(content.split("\n"))
                        .limit(5)
                        .forEach(line -> {
                            if (line.length() > 50) {
                                tooltip.add(Text.literal(line.substring(0, 50) + "...").formatted(Formatting.DARK_GRAY));
                            } else {
                                tooltip.add(Text.literal(line).formatted(Formatting.DARK_GRAY));
                            }
                        });
            }
        }
    }
}
