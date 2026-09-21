package daw.ka.informejtycy.client.mixin;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class MixinSplashScreen {
	@Unique private static final Identifier CUSTOM_LOGO = InformejtycyRegistry.id("textures/gui/banner.png");
	@Unique private static final IntSupplier BACKGROUND_COLOR = () -> ARGB.color(255, 44, 56, 87);

	@Shadow private long fadeOutStart;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(CallbackInfo ci) {
		Minecraft client = Minecraft.getInstance();
		TextureManager textureManager = client.getTextureManager();
		textureManager.registerAndLoad(CUSTOM_LOGO, new SimpleTexture(CUSTOM_LOGO));
	}

	@Redirect(
			method = "extractRenderState",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;MOJANG_STUDIOS_LOGO_LOCATION:Lnet/minecraft/resources/Identifier;",
					opcode = Opcodes.GETSTATIC)
	)
	private Identifier replaceLogo() {
		return CUSTOM_LOGO;
	}

	@Redirect(
			method = "extractRenderState",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;BRAND_BACKGROUND:Ljava/util/function/IntSupplier;",
					opcode = Opcodes.GETSTATIC)
	)
	private IntSupplier replaceBackgroundColor() {
		return BACKGROUND_COLOR;
	}

	@Redirect(
			method = "extractRenderState",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V")
	)
	private void removeLogoTint(GuiGraphicsExtractor context, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
		context.blit(RenderPipelines.GUI_TEXTURED, sprite, x, y, u, v, width, height, regionWidth, regionHeight, textureWidth, textureHeight, ARGB.color(Math.round(calculateOpacity() * 255), 255, 255, 255));
	}

	@Unique
	private float calculateOpacity() {
		long l = Util.getMillis();
		float f = fadeOutStart > -1L ? (float)(l - fadeOutStart) / 1000.0F : -1.0F;
		return 1.0F - Mth.clamp(f, 0.0F, 1.0F);
	}
}
