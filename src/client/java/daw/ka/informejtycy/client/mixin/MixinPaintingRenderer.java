package daw.ka.informejtycy.client.mixin;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.state.PaintingRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.SpriteContents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.resources.Identifier;
import com.mojang.math.Axis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PaintingRenderer.class)
public abstract class MixinPaintingRenderer extends EntityRenderer<Painting, PaintingRenderState> {
	protected MixinPaintingRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

    @Shadow @Final private TextureAtlas paintingsAtlas;
	@Shadow protected abstract void renderPainting(PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, RenderType renderLayer, int[] is, int i, int j, TextureAtlasSprite sprite, TextureAtlasSprite sprite2);
	@Shadow protected static void vertex(PoseStack.Pose matrix, VertexConsumer vertexConsumer, float x, float y, float u, float v, float z, int normalX, int normalY, int normalZ, int light) {
		throw new AssertionError();
	}

	@Inject(method = "submit", at = @At("HEAD"), cancellable = true)
	private void makePaintingTransparent(PaintingRenderState paintingEntityRenderState, PoseStack matrixStack,
                                         SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState,
                                         CallbackInfo ci) {
		PaintingVariant paintingVariant = paintingEntityRenderState.variant;
		if (paintingVariant != null) {
			matrixStack.pushPose();
			matrixStack.rotate(Axis.YP.rotationDegrees((float)(180 - paintingEntityRenderState.direction.get2DDataValue() * 90)));
			TextureAtlasSprite backSprite = paintingsAtlas.getSprite(Identifier.withDefaultNamespace("back"));
            TextureAtlasSprite frontSprite = paintingsAtlas.getSprite(paintingVariant.assetId());
			this.renderPainting(matrixStack,
                    orderedRenderCommandQueue,
                    RenderTypes.entityTranslucent(backSprite.atlasLocation()),
                    paintingEntityRenderState.lightCoordsPerBlock,
                    paintingVariant.width(),
                    paintingVariant.height(),
                    frontSprite,
                    backSprite);
			matrixStack.popPose();
			super.submit(paintingEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
		}

		ci.cancel();
	}

	@Inject(method = "renderPainting", at = @At("HEAD"), cancellable = true)
	private void handleBackRendering(PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue,
                                     RenderType renderLayer, int[] lightmapCoordinates, int width, int height,
                                     TextureAtlasSprite paintingSprite, TextureAtlasSprite backSprite, CallbackInfo ci) {
		orderedRenderCommandQueue.submitCustomGeometry(matrixStack, renderLayer, ((entry, vertexConsumer) -> {
            float f = (float)(-width) / 2.0F;
            float g = (float)(-height) / 2.0F;
            float i = backSprite.getU0();
            float j = backSprite.getU1();
            float k = backSprite.getV0();
            float l = backSprite.getV1();
            float m = backSprite.getU0();
            float n = backSprite.getU1();
            float o = backSprite.getV0();
            float p = backSprite.getV(0.0625F);
            float q = backSprite.getU0();
            float r = backSprite.getU(0.0625F);
            float s = backSprite.getV0();
            float t = backSprite.getV1();
            double d = (double)1.0F / (double)width;
            double e = (double)1.0F / (double)height;

            SpriteContents contents = paintingSprite.contents();

            for(int u = 0; u < width; ++u) {
                for(int v = 0; v < height; ++v) {
                    float w = f + (float)(u + 1);
                    float x = f + (float)u;
                    float y = g + (float)(v + 1);
                    float z = g + (float)v;
                    int aa = lightmapCoordinates[u + v * width];
                    float ab = paintingSprite.getU((float)(d * (double)(width - u)));
                    float ac = paintingSprite.getU((float)(d * (double)(width - (u + 1))));
                    float ad = paintingSprite.getV((float)(e * (double)(height - v)));
                    float ae = paintingSprite.getV((float)(e * (double)(height - (v + 1))));

                    vertex(entry, vertexConsumer, w, z, ac, ad, -0.03125F, 0, 0, -1, aa);
                    vertex(entry, vertexConsumer, x, z, ab, ad, -0.03125F, 0, 0, -1, aa);
                    vertex(entry, vertexConsumer, x, y, ab, ae, -0.03125F, 0, 0, -1, aa);
                    vertex(entry, vertexConsumer, w, y, ac, ae, -0.03125F, 0, 0, -1, aa);

                    if (!contents.isTransparent(0, u, v)) {
                        vertex(entry, vertexConsumer, w, y, j, k, 0.03125F, 0, 0, 1, aa);
                        vertex(entry, vertexConsumer, x, y, i, k, 0.03125F, 0, 0, 1, aa);
                        vertex(entry, vertexConsumer, x, z, i, l, 0.03125F, 0, 0, 1, aa);
                        vertex(entry, vertexConsumer, w, z, j, l, 0.03125F, 0, 0, 1, aa);

                        vertex(entry, vertexConsumer, w, y, m, o, -0.03125F, 0, 1, 0, aa);
                        vertex(entry, vertexConsumer, x, y, n, o, -0.03125F, 0, 1, 0, aa);
                        vertex(entry, vertexConsumer, x, y, n, p, 0.03125F, 0, 1, 0, aa);
                        vertex(entry, vertexConsumer, w, y, m, p, 0.03125F, 0, 1, 0, aa);

                        vertex(entry, vertexConsumer, w, z, m, o, 0.03125F, 0, -1, 0, aa);
                        vertex(entry, vertexConsumer, x, z, n, o, 0.03125F, 0, -1, 0, aa);
                        vertex(entry, vertexConsumer, x, z, n, p, -0.03125F, 0, -1, 0, aa);
                        vertex(entry, vertexConsumer, w, z, m, p, -0.03125F, 0, -1, 0, aa);

                        vertex(entry, vertexConsumer, w, y, r, s, 0.03125F, -1, 0, 0, aa);
                        vertex(entry, vertexConsumer, w, z, r, t, 0.03125F, -1, 0, 0, aa);
                        vertex(entry, vertexConsumer, w, z, q, t, -0.03125F, -1, 0, 0, aa);
                        vertex(entry, vertexConsumer, w, y, q, s, -0.03125F, -1, 0, 0, aa);

                        vertex(entry, vertexConsumer, x, y, r, s, -0.03125F, 1, 0, 0, aa);
                        vertex(entry, vertexConsumer, x, z, r, t, -0.03125F, 1, 0, 0, aa);
                        vertex(entry, vertexConsumer, x, z, q, t, 0.03125F, 1, 0, 0, aa);
                        vertex(entry, vertexConsumer, x, y, q, s, 0.03125F, 1, 0, 0, aa);
                    }
                }
            }
        }));

		ci.cancel();
	}
}
