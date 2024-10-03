package com.thejebforge.trickster_lisp.screen.transpiler;

import com.thejebforge.trickster_lisp.screen.PaperAndPencilScreenHandler;
import com.thejebforge.trickster_lisp.screen.TranspilerScreenHandler;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class PaperAndPencilScreen extends BaseOwoScreen<FlowLayout> implements ScreenHandlerProvider<PaperAndPencilScreenHandler> {
    private final PaperAndPencilScreenHandler handler;
    private final CodeEditorComponent codeEditor;

    public PaperAndPencilScreen(PaperAndPencilScreenHandler handler, PlayerInventory ignored, Text title) {
        super(title);
        this.handler = handler;

        codeEditor = new CodeEditorComponent(Sizing.expand(), Sizing.fill(60));
        handler.rawCode.observe(codeEditor::setText);
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
                        .padding(Insets.of(5))
                        .surface(Surface.PANEL)
        );
    }

    @Override
    public void close() {
        handler.sendMessage(new PaperAndPencilScreenHandler.SaveCode(codeEditor.getText()));

        //noinspection DataFlowIssue
        this.client.player.closeHandledScreen();
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public PaperAndPencilScreenHandler getScreenHandler() {
        return handler;
    }
}
