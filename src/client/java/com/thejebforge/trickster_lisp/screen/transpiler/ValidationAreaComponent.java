package com.thejebforge.trickster_lisp.screen.transpiler;

import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.core.Sizing;

public class ValidationAreaComponent extends TextAreaComponent {
    protected ValidationAreaComponent(Sizing horizontalSizing, Sizing verticalSizing) {
        super(horizontalSizing, verticalSizing);
    }

    @Override
    public boolean canFocus(FocusSource source) {
        return false;
    }
}
