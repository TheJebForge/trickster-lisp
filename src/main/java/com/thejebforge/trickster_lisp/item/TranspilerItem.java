package com.thejebforge.trickster_lisp.item;

import com.thejebforge.trickster_lisp.item.component.MacroDefinitionComponent;
import com.thejebforge.trickster_lisp.item.component.RawCodeComponent;
import com.thejebforge.trickster_lisp.screen.TranspilerScreenHandler;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class TranspilerItem extends Item {
    public TranspilerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var otherStack = user.getStackInHand(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);
        var macroList = MacroDefinitionComponent.findUserDefinedMacros(user, "ring")
                .orElseGet(HashSet::empty);

        if (!world.isClient) {
            user.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.translatable("trickster_lisp.screen.transpiler");
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new TranspilerScreenHandler(syncId, inv, player, hand, stack, otherStack, macroList);
                }
            });
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        RawCodeComponent.appendTooltip(stack, tooltip);
    }
}
