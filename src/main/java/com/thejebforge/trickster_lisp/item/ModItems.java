package com.thejebforge.trickster_lisp.item;

import com.thejebforge.trickster_lisp.TricksterLISP;
import com.thejebforge.trickster_lisp.item.component.ModComponents;
import com.thejebforge.trickster_lisp.item.component.RawCodeComponent;
import io.wispforest.lavender.book.LavenderBookItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class ModItems {
    public static final LavenderBookItem TOME_OF_LISPFOOLERY = LavenderBookItem.registerForBook(
            TricksterLISP.id("tome_of_lispfoolery"),
            new Item.Settings().maxCount(1)
    );

    public static final TranspilerItem TRANSPILER = register(
            "transpiler",
            new TranspilerItem(new Item.Settings()
                    .maxCount(1)
                    .component(ModComponents.RAW_CODE_COMPONENT, new RawCodeComponent("")))
    )
            ;
    public static final PaperAndPencilItem PAPER_AND_PENCIL = register(
            "paper_and_pencil",
            new PaperAndPencilItem(new Item.Settings()
                    .maxCount(16)
                    .component(ModComponents.RAW_CODE_COMPONENT, new RawCodeComponent("")))
    );

    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), TricksterLISP.id("item_group"));


    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, TricksterLISP.id(name), item);
    }

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.TRANSPILER))
                .displayName(Text.translatable("trickster_lisp.item_group"))
                .build());

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(context -> {
            context.add(ModItems.TOME_OF_LISPFOOLERY);
            context.add(ModItems.TRANSPILER);
            context.add(ModItems.PAPER_AND_PENCIL);
        });
    }
}
