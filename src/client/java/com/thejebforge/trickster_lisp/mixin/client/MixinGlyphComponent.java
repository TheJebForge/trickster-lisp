package com.thejebforge.trickster_lisp.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.thejebforge.trickster_lisp.transpiler.SpellConverter;
import dev.enjarai.trickster.screen.owo.GlyphComponent;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.trick.Tricks;
import io.wispforest.owo.ui.base.BaseComponent;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import net.minecraft.text.Text;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlyphComponent.class)
public abstract class MixinGlyphComponent extends BaseComponent {
    @Shadow(remap = false) protected Pattern pattern;
    @Shadow(remap = false) protected int size;

    @ModifyExpressionValue(
            method = "draw(Lio/wispforest/owo/ui/core/OwoUIDrawContext;IIFF)V",
            at = @At(
                    value = "FIELD",
                    target = "Ldev/enjarai/trickster/screen/owo/GlyphComponent;size:I",
                    opcode = Opcodes.GETFIELD
            ),
            remap = false
    )
    private int trickster_lisp$modifySize(int original) {
        return original - 7;
    }

    @Inject(at = @At(value = "TAIL"), method = "draw(Lio/wispforest/owo/ui/core/OwoUIDrawContext;IIFF)V", remap = false)
    private void trickster_lisp$postDraw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta, CallbackInfo ci) {
        var trick = Tricks.lookup(pattern);
        var trickId = Tricks.REGISTRY.getId(trick);

        if (trickId != null) {
            var noNamespace = trickId.getNamespace().equals("trickster") ?
                    trickId.getPath()
                    : trickId.toString();

            var asCall = noNamespace.startsWith(SpellConverter.LOAD_ARGUMENT_PART) ?
                    String.format("(arg %s)", noNamespace.substring(SpellConverter.LOAD_ARGUMENT_PART.length()))
                    : SpellConverter.OPERATOR_MAPPING.getOrDefault(noNamespace, noNamespace);

            var text = String.format("LISP: %s", asCall);

            context.drawText(Text.literal(text), x, y + size, 0.5F, 0);
        }
    }
}
