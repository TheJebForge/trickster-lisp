package com.thejebforge.trickster_lisp.screen;

import com.thejebforge.trickster_lisp.item.component.ModComponents;
import com.thejebforge.trickster_lisp.item.component.RawCodeComponent;
import io.wispforest.owo.client.screens.SyncedProperty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class PaperAndPencilScreenHandler extends ScreenHandler {
    public final ItemStack currentStack;
    public final SyncedProperty<String> rawCode = createProperty(String.class, "");

    public PaperAndPencilScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, (ItemStack) null);
    }

    public PaperAndPencilScreenHandler(int syncId, ItemStack currentStack) {
        super(ModScreenHandlers.PAPER_AND_PENCIL, syncId);
        this.currentStack = currentStack;

        if (currentStack != null) {
            var code = currentStack.get(ModComponents.RAW_CODE_COMPONENT);
            if (code != null) {
                rawCode.set(code.content());
            }
        }

        addServerboundMessage(SaveCode.class, msg -> {
            if (currentStack != null) {
                currentStack.set(ModComponents.RAW_CODE_COMPONENT, new RawCodeComponent(msg.code));
            }
        });
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public record SaveCode(String code) {}
}
