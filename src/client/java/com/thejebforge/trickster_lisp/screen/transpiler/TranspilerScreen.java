package com.thejebforge.trickster_lisp.screen.transpiler;

import com.thejebforge.trickster_lisp.screen.TranspilerScreenHandler;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class TranspilerScreen extends BaseOwoScreen<FlowLayout> implements ScreenHandlerProvider<TranspilerScreenHandler> {
    private final TranspilerScreenHandler handler;
    private final CodeEditorComponent codeEditor;
    private final TextAreaComponent validationArea;

    public TranspilerScreen(TranspilerScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(title);
        this.handler = handler;
        codeEditor = new CodeEditorComponent(Sizing.expand(), Sizing.fill(50));
        validationArea = new ValidationAreaComponent(Sizing.expand(), Sizing.fixed(60));

        handler.validationText.observe(validationArea::setText);
        handler.rawCode.observe(codeEditor::setText);
    }

    @Override
    public void close() {
        handler.sendMessage(new TranspilerScreenHandler.SaveCode(codeEditor.getText()));

        //noinspection DataFlowIssue
        this.client.player.closeHandledScreen();
        super.close();
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalFlow(Sizing.fill(55), Sizing.content())
                        .child(codeEditor)
                        .child(Containers.horizontalFlow(Sizing.expand(), Sizing.content())
                                .child(validationArea)
                                .child(Containers.verticalFlow(Sizing.fixed(50), Sizing.content())
                                        .child(Components.button(Text.translatable("trickster_lisp.screen.transpiler.load"), button -> {
                                            handler.sendMessage(new TranspilerScreenHandler.LoadCode());
                                        }).horizontalSizing(Sizing.expand()))
                                        .child(Components.button(Text.translatable("trickster_lisp.screen.transpiler.validate"), button -> {
                                            handler.sendMessage(new TranspilerScreenHandler.ValidateCode(codeEditor.getText()));
                                        }).horizontalSizing(Sizing.expand()))
                                        .child(Components.button(Text.translatable("trickster_lisp.screen.transpiler.store"), button -> {
                                            handler.sendMessage(new TranspilerScreenHandler.StoreCode(codeEditor.getText()));
                                        }).horizontalSizing(Sizing.expand()))

                                        // TODO: Printed paper where you could store LISP code
                                )
                        )
                        .padding(Insets.of(5))
                        .surface(Surface.DARK_PANEL)
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public TranspilerScreenHandler getScreenHandler() {
        return handler;
    }
}
