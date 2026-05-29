package daw.ka.informejtycy.client.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PaintingEntityRenderer;
import net.minecraft.client.render.entity.state.PaintingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PaintingEntityRenderer.class)
public abstract class MixinPaintingRenderer extends EntityRenderer<PaintingEntity, PaintingEntityRenderState> {
	protected MixinPaintingRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

    @Shadow @Final private SpriteAtlasTexture paintingAtlases;
	@Shadow protected abstract void renderPainting(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, RenderLayer renderLayer, int[] is, int i, int j, Sprite sprite, Sprite sprite2);
	@Shadow protected abstract void vertex(MatrixStack.Entry matrix, VertexConsumer vertexConsumer, float x, float y, float u, float v, float z, int normalX, int normalY, int normalZ, int light);

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void makePaintingTransparent(PaintingEntityRenderState paintingEntityRenderState, MatrixStack matrixStack,
                                         OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState,
                                         CallbackInfo ci) {
		PaintingVariant paintingVariant = paintingEntityRenderState.variant;
		if (paintingVariant != null) {
			matrixStack.push();
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(180 - paintingEntityRenderState.facing.getHorizontalQuarterTurns() * 90)));
			Sprite backSprite = paintingAtlases.getSprite(Identifier.ofVanilla("back"));
            Sprite frontSprite = paintingAtlases.getSprite(paintingVariant.assetId());
			this.renderPainting(matrixStack,
                    orderedRenderCommandQueue,
                    RenderLayers.entityTranslucent(backSprite.getAtlasId()),
                    paintingEntityRenderState.lightmapCoordinates,
                    paintingVariant.width(),
                    paintingVariant.height(),
                    frontSprite,
                    backSprite);
			matrixStack.pop();
			super.render(paintingEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
		}

		ci.cancel();
	}

	@Inject(method = "renderPainting", at = @At("HEAD"), cancellable = true)
	private void handleBackRendering(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue,
                                     RenderLayer renderLayer, int[] lightmapCoordinates, int width, int height,
                                     Sprite paintingSprite, Sprite backSprite, CallbackInfo ci) {
		orderedRenderCommandQueue.submitCustom(matrixStack, renderLayer, ((entry, vertexConsumer) -> {
            float f = (float)(-width) / 2.0F;
            float g = (float)(-height) / 2.0F;
            float i = backSprite.getMinU();
            float j = backSprite.getMaxU();
            float k = backSprite.getMinV();
            float l = backSprite.getMaxV();
            float m = backSprite.getMinU();
            float n = backSprite.getMaxU();
            float o = backSprite.getMinV();
            float p = backSprite.getFrameV(0.0625F);
            float q = backSprite.getMinU();
            float r = backSprite.getFrameU(0.0625F);
            float s = backSprite.getMinV();
            float t = backSprite.getMaxV();
            double d = (double)1.0F / (double)width;
            double e = (double)1.0F / (double)height;

            SpriteContents contents = paintingSprite.getContents();

            for(int u = 0; u < width; ++u) {
                for(int v = 0; v < height; ++v) {
                    float w = f + (float)(u + 1);
                    float x = f + (float)u;
                    float y = g + (float)(v + 1);
                    float z = g + (float)v;
                    int aa = lightmapCoordinates[u + v * width];
                    float ab = paintingSprite.getFrameU((float)(d * (double)(width - u)));
                    float ac = paintingSprite.getFrameU((float)(d * (double)(width - (u + 1))));
                    float ad = paintingSprite.getFrameV((float)(e * (double)(height - v)));
                    float ae = paintingSprite.getFrameV((float)(e * (double)(height - (v + 1))));

                    this.vertex(entry, vertexConsumer, w, z, ac, ad, -0.03125F, 0, 0, -1, aa);
                    this.vertex(entry, vertexConsumer, x, z, ab, ad, -0.03125F, 0, 0, -1, aa);
                    this.vertex(entry, vertexConsumer, x, y, ab, ae, -0.03125F, 0, 0, -1, aa);
                    this.vertex(entry, vertexConsumer, w, y, ac, ae, -0.03125F, 0, 0, -1, aa);

                    if (!contents.isPixelTransparent(0, u, v)) {
                        this.vertex(entry, vertexConsumer, w, y, j, k, 0.03125F, 0, 0, 1, aa);
                        this.vertex(entry, vertexConsumer, x, y, i, k, 0.03125F, 0, 0, 1, aa);
                        this.vertex(entry, vertexConsumer, x, z, i, l, 0.03125F, 0, 0, 1, aa);
                        this.vertex(entry, vertexConsumer, w, z, j, l, 0.03125F, 0, 0, 1, aa);

                        this.vertex(entry, vertexConsumer, w, y, m, o, -0.03125F, 0, 1, 0, aa);
                        this.vertex(entry, vertexConsumer, x, y, n, o, -0.03125F, 0, 1, 0, aa);
                        this.vertex(entry, vertexConsumer, x, y, n, p, 0.03125F, 0, 1, 0, aa);
                        this.vertex(entry, vertexConsumer, w, y, m, p, 0.03125F, 0, 1, 0, aa);

                        this.vertex(entry, vertexConsumer, w, z, m, o, 0.03125F, 0, -1, 0, aa);
                        this.vertex(entry, vertexConsumer, x, z, n, o, 0.03125F, 0, -1, 0, aa);
                        this.vertex(entry, vertexConsumer, x, z, n, p, -0.03125F, 0, -1, 0, aa);
                        this.vertex(entry, vertexConsumer, w, z, m, p, -0.03125F, 0, -1, 0, aa);

                        this.vertex(entry, vertexConsumer, w, y, r, s, 0.03125F, -1, 0, 0, aa);
                        this.vertex(entry, vertexConsumer, w, z, r, t, 0.03125F, -1, 0, 0, aa);
                        this.vertex(entry, vertexConsumer, w, z, q, t, -0.03125F, -1, 0, 0, aa);
                        this.vertex(entry, vertexConsumer, w, y, q, s, -0.03125F, -1, 0, 0, aa);

                        this.vertex(entry, vertexConsumer, x, y, r, s, -0.03125F, 1, 0, 0, aa);
                        this.vertex(entry, vertexConsumer, x, z, r, t, -0.03125F, 1, 0, 0, aa);
                        this.vertex(entry, vertexConsumer, x, z, q, t, 0.03125F, 1, 0, 0, aa);
                        this.vertex(entry, vertexConsumer, x, y, q, s, 0.03125F, 1, 0, 0, aa);
                    }
                }
            }
        }));

		ci.cancel();
	}
}
