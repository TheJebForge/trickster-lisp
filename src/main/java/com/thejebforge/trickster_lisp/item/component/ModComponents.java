package com.thejebforge.trickster_lisp.item.component;

import com.thejebforge.trickster_lisp.TricksterLISP;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {
    public static final ComponentType<RawCodeComponent> RAW_CODE_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            TricksterLISP.id("raw_code"),
            ComponentType.<RawCodeComponent>builder()
                    .codec(RawCodeComponent.CODEC).build()
    );

    public static void register() {}
}
