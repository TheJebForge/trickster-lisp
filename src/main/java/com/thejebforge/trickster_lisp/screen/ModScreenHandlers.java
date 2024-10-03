package com.thejebforge.trickster_lisp.screen;

import com.thejebforge.trickster_lisp.TricksterLISP;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static final ScreenHandlerType<TranspilerScreenHandler> TRANSPILER =
        new ScreenHandlerType<>(TranspilerScreenHandler::new, FeatureSet.empty());
    public static final ScreenHandlerType<PaperAndPencilScreenHandler> PAPER_AND_PENCIL =
            new ScreenHandlerType<>(PaperAndPencilScreenHandler::new, FeatureSet.empty());

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER, TricksterLISP.id("transpiler"), TRANSPILER);
        Registry.register(Registries.SCREEN_HANDLER, TricksterLISP.id("paper_and_pencil"), PAPER_AND_PENCIL);
    }
}
