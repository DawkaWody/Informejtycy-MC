package daw.ka.informejtycy.client.mixin;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.function.IntSupplier;

import static net.minecraft.client.gl.RenderPipelines.POSITION_TEX_COLOR_SNIPPET;

@Mixin(SplashOverlay.class)
public abstract class MixinSplashScreen {
	@Unique private static final Identifier CUSTOM_LOGO = InformejtycyRegistry.id("textures/gui/banner.png");
	@Unique private static final RenderPipeline CUSTOM_LOGO_PIPELINE = RenderPipelines.register(
			RenderPipeline.builder(POSITION_TEX_COLOR_SNIPPET)
					.withLocation("pipeline/mojang_logo")
					.withBlend(new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA)).build()
	);
	@Unique private static final IntSupplier BACKGROUND_COLOR = () -> ColorHelper.getArgb(255, 44, 56, 87);

	@Shadow private float progress;
	@Shadow private long reloadCompleteTime;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		TextureManager textureManager = client.getTextureManager();
		ResourceTexture texture = new ResourceTexture(CUSTOM_LOGO);
		textureManager.registerTexture(CUSTOM_LOGO, texture);
		try {
			texture.loadContents(client.getResourceManager());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Redirect(
			method = "render",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/gui/screen/SplashOverlay;LOGO:Lnet/minecraft/util/Identifier;")
	)
	private Identifier replaceLogo() {
		return CUSTOM_LOGO;
	}

	@Redirect(
			method = "render",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/gui/screen/SplashOverlay;BRAND_ARGB:Ljava/util/function/IntSupplier;")
	)
	private IntSupplier replaceBackgroundColor() {
		return BACKGROUND_COLOR;
	}

	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIIIIII)V")
	)
	private void removeLogoTint(DrawContext context, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
		context.drawTexture(CUSTOM_LOGO_PIPELINE, sprite, x, y, u, v, width, height, regionWidth, regionHeight, textureWidth, textureHeight, ColorHelper.getArgb(Math.round(calculateOpacity() * 255), 255, 255, 255));
	}

	@Unique
	private float calculateOpacity() {
		long l = Util.getMeasuringTimeMs();
		float f = reloadCompleteTime > -1L ? (float)(l - reloadCompleteTime) / 1000.0F : -1.0F;
		return 1.0F - MathHelper.clamp(f, 0.0F, 1.0F);
	}
}
