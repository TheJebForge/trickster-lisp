package com.thejebforge.trickster_lisp.screen;

import com.thejebforge.trickster_lisp.screen.transpiler.TranspilerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModHandledScreens {
    public static void register() {
        HandledScreens.register(ModScreenHandlers.TRANSPILER, TranspilerScreen::new);
    }
}
