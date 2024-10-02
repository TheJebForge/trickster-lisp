package com.thejebforge.trickster_lisp.screen;

import com.thejebforge.trickster_lisp.TricksterLISP;
import com.thejebforge.trickster_lisp.item.component.ModComponents;
import com.thejebforge.trickster_lisp.item.component.RawCodeComponent;
import com.thejebforge.trickster_lisp.transpiler.LispAST;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import dev.enjarai.trickster.item.component.SpellComponent;
import dev.enjarai.trickster.spell.SpellPart;
import io.wispforest.owo.client.screens.SyncedProperty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;

import java.util.Optional;

public class TranspilerScreenHandler extends ScreenHandler {

    public final ItemStack currentStack;
    public final ItemStack otherHandStack;

    public final SyncedProperty<String> validationText = createProperty(String.class, "");
    public final SyncedProperty<SpellPart> spell = createProperty(SpellPart.class, SpellPart.ENDEC, null);
    public final SyncedProperty<String> rawCode = createProperty(String.class, "");

    public TranspilerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, playerInventory.player, null, null, null);
    }

    public TranspilerScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player, Hand hand, ItemStack currentStack, ItemStack otherHandStack) {
        super(ModScreenHandlers.TRANSPILER, syncId);
        this.currentStack = currentStack;
        this.otherHandStack = otherHandStack;

        if (otherHandStack != null) {
            var spell = otherHandStack.get(dev.enjarai.trickster.item.component.ModComponents.SPELL);

            if (spell != null && !spell.closed()) {
                SpellComponent.getSpellPart(otherHandStack).ifPresent(this.spell::set);
            }
        }

        if (currentStack != null) {
            var code = currentStack.get(ModComponents.RAW_CODE_COMPONENT);
            if (code != null) {
                rawCode.set(code.content());
            }
        }

        addServerboundMessage(LoadCode.class, msg -> {
            var currentSpell = spell.get();

            if (currentSpell != null) {
                validationText.set("Spell loaded successfully");
                rawCode.set(SpellConverter.spellToAST(currentSpell).toCode());
            } else {
                validationText.set("Couldn't load spell from offhand");
            }
        });

        addServerboundMessage(ValidateCode.class, msg -> {
            validationText.set("");

            saveCode(msg.code);

            try {
                var ast = LispAST.parse(msg.code);
                validationText.set("SUCCESS");
                rawCode.set(ast.toCode());
            } catch (LispAST.ParseError e) {
                validationText.set(e.getMessage());
            }
        });

        addServerboundMessage(SaveCode.class, msg -> {
            saveCode(msg.code);
        });

        addServerboundMessage(StoreCode.class, msg -> {
            validationText.set("");

            saveCode(msg.code);

            if (otherHandStack == null) {
                validationText.set("No item in other hand is found");
                return;
            }

            try {
                var ast = LispAST.parse(msg.code);
                var spellPart = SpellConverter.astToSpell(ast);

                SpellComponent.setSpellPart(otherHandStack, spellPart, Optional.empty(), false);

                validationText.set("SUCCESS");
            } catch (Exception e) {
                validationText.set(e.getMessage());
            }
        });
    }

    private void saveCode(String code) {
        if (currentStack != null) {
            currentStack.set(ModComponents.RAW_CODE_COMPONENT, new RawCodeComponent(code));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public record LoadCode() {}
    public record ValidateCode(String code) {}
    public record SaveCode(String code) {}
    public record StoreCode(String code) {}
}
