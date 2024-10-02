package com.thejebforge.trickster_lisp.screen.transpiler;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.util.function.Function;

public class TextRendererWithStyle extends TextRenderer {
    // I tried using unifont here, but it's not monospace :<
    // Guess I'll just keep using default font until me (or someone else)
    // figures out how to get a custom monospace font
    private static final Identifier UNIFORM = Identifier.ofVanilla("default");

    public TextRendererWithStyle(Function<Identifier, FontStorage> fontStorageAccessor, boolean validateAdvance) {
        super(fontStorageAccessor, validateAdvance);
    }

    public TextRendererWithStyle(TextRenderer originalTextRenderer) {
        super(originalTextRenderer.fontStorageAccessor, originalTextRenderer.validateAdvance);
    }

    @Override
    public int draw(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, TextLayerType layerType, int backgroundColor, int light, boolean rightToLeft) {
        return super.draw(Text.literal(text).styled(style -> style.withFont(UNIFORM)), x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light);
    }

    @Override
    public int getWidth(String text) {
        return super.getWidth(Text.literal(text).styled(style -> style.withFont(UNIFORM)));
    }
}
