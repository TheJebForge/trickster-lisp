package com.thejebforge.trickster_lisp;

import com.thejebforge.trickster_lisp.screen.ModHandledScreens;
import net.fabricmc.api.ClientModInitializer;

public class TricksterLISPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModHandledScreens.register();
	}
}