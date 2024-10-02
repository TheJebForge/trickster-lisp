package com.thejebforge.trickster_lisp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RawCodeComponent(String content) {
    public static final Codec<RawCodeComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("content").forGetter(RawCodeComponent::content)
    ).apply(instance, RawCodeComponent::new));
}
