package com.thejebforge.trickster_lisp.mixin.client;

import dev.enjarai.trickster.screen.owo.GlyphComponent;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GlyphComponent.class)
public interface GlyphComponentAccessor {
    @Accessor(remap = false)
    Pattern getPattern();

    @Accessor(remap = false)
    int getSize();

    @Accessor(remap = false)
    List<Integer> getPatternList();
}
