package com.thejebforge.trickster_lisp.item;

import com.thejebforge.trickster_lisp.TricksterLISP;
import com.thejebforge.trickster_lisp.item.component.ModComponents;
import com.thejebforge.trickster_lisp.item.component.RawCodeComponent;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final TranspilerItem TRANSPILER = register(
            "transpiler",
            new TranspilerItem(new Item.Settings()
                    .maxCount(1)
                    .component(ModComponents.RAW_CODE_COMPONENT, new RawCodeComponent(""))));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, TricksterLISP.id(name), item);
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register(context -> {
                    context.add(TRANSPILER.getDefaultStack());
                });
    }
}
