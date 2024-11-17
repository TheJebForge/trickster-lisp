package com.thejebforge.trickster_lisp.screen;

import com.thejebforge.trickster_lisp.item.ModItems;
import com.thejebforge.trickster_lisp.item.component.MacroDefinitionComponent;
import com.thejebforge.trickster_lisp.item.component.ModComponents;
import com.thejebforge.trickster_lisp.item.component.RawCodeComponent;
import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import com.thejebforge.trickster_lisp.transpiler.ast.Macro;
import com.thejebforge.trickster_lisp.transpiler.util.CallUtils;
import dev.enjarai.trickster.item.component.FragmentComponent;
import io.vavr.collection.HashSet;
import io.wispforest.owo.client.screens.SyncedProperty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

import static dev.enjarai.trickster.item.component.ModComponents.FRAGMENT;

public class TranspilerScreenHandler extends ScreenHandler {

    public final ItemStack currentStack;
    public ItemStack otherHandStack;

    public final SyncedProperty<String> validationText = createProperty(String.class, "");
    public final SyncedProperty<String> initialRawCode = createProperty(String.class, "");
    public final SyncedProperty<HashSet<Macro>> macros = createProperty(null, MacroDefinitionComponent.MACRO_SET_ENDEC, HashSet.empty());

    public Consumer<String> replaceCodeCallback;

    public TranspilerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, playerInventory.player, null, null, null, HashSet.empty());
    }

    public TranspilerScreenHandler(
            int syncId,
            PlayerInventory playerInventory,
            PlayerEntity player,
            Hand hand,
            ItemStack currentStack,
            ItemStack otherHandStack,
            HashSet<Macro> externalMacros
    ) {
        super(ModScreenHandlers.TRANSPILER, syncId);
        this.currentStack = currentStack;
        this.otherHandStack = otherHandStack;

        this.macros.set(externalMacros);

        if (currentStack != null) {
            var code = currentStack.get(ModComponents.RAW_CODE_COMPONENT);
            if (code != null) {
                initialRawCode.set(code.content());
            }
        }

        addServerboundMessage(LoadCode.class, msg -> {
            if (otherHandStack != null) {
                var code = otherHandStack.get(ModComponents.RAW_CODE_COMPONENT);

                if (code != null) {
                    sendMessage(new ReplaceCode(code.content()));
                    validationText.set("Code loaded successfully");
                    return;
                }

                var macros = otherHandStack.get(ModComponents.MACRO_DEFINITION_COMPONENT);

                var spell = otherHandStack.get(FRAGMENT);

                if (spell != null && !spell.closed()) {
                    sendMessage(new ReplaceCode(
                            SpellConverter.spellToAST(
                                    spell.value(),
                                    macros != null ? macros.macros() : Collections.emptyList(),
                                    this.macros.get().toJavaList()
                            ).toCode()
                    ));
                    validationText.set("Spell loaded successfully");
                    return;
                }
            }

            validationText.set("Couldn't load spell from offhand");
        });

        addServerboundMessage(ValidateCode.class, msg -> {
            validationText.set("");

            saveCode(msg.code);

            try {
                var ast = LispUtils.parse(msg.code);
                validationText.set("SUCCESS");
                sendMessage(new ReplaceCode(ast.toCode()));
            } catch (LispUtils.ParseError e) {
                validationText.set(e.getMessage());
            }
        });

        addServerboundMessage(SaveCode.class, msg -> saveCode(msg.code));

        addServerboundMessage(StoreCode.class, msg -> {
            validationText.set("");

            saveCode(msg.code);

            if (otherHandStack == null || otherHandStack.isEmpty()) {
                validationText.set("No item in other hand is found");
                return;
            }

            if (otherHandStack.isOf(ModItems.PAPER_AND_PENCIL)) {
                otherHandStack.set(ModComponents.RAW_CODE_COMPONENT, new RawCodeComponent(msg.code));
                validationText.set("Printed code to paper");
                return;
            }

            try {
                var ast = LispUtils.parse(msg.code);
                var spellPart = SpellConverter.astToFinalFragment(ast, this.macros.get().toJavaList());

                FragmentComponent.setValue(otherHandStack, spellPart, Optional.empty(), false);

                var macros = ast.retrieveMacros();
                if (!macros.isEmpty()) {
                    otherHandStack.set(ModComponents.MACRO_DEFINITION_COMPONENT, new MacroDefinitionComponent(macros));
                }

                validationText.set("SUCCESS");
            } catch (LispUtils.ParseError | CallUtils.ConversionError e) {
                validationText.set(e.getMessage());
            }
        });

        addClientboundMessage(ReplaceCode.class, msg -> {
            if (replaceCodeCallback != null) {
                replaceCodeCallback.accept(msg.code);
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

    public record ReplaceCode(String code) {}

    public record LoadCode() {}
    public record ValidateCode(String code) {}
    public record SaveCode(String code) {}
    public record StoreCode(String code) {}
}
