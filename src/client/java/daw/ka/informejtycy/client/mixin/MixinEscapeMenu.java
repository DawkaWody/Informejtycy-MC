package daw.ka.informejtycy.client.mixin;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URI;

@Mixin(value = PauseScreen.class)
public abstract class MixinEscapeMenu {
	@Unique private static final Identifier TOP_LOGO = InformejtycyRegistry.id("textures/gui/pause_menu_logo.png");
	@Unique private static final int TOP_LOGO_WIDTH = 2048;
	@Unique private static final int TOP_LOGO_HEIGHT = 350;

	@Unique private static final Identifier BOTTOM_LOGO = InformejtycyRegistry.id("textures/gui/pause_menu_logo2.png");
	@Unique private static final int BOTTOM_LOGO_WIDTH = 1400;
	@Unique private static final int BOTTOM_LOGO_HEIGHT = 450;

	@Unique private static final int LOGO_MARGIN = 4;
	@Unique private static final int MIN_LOGO_HEIGHT = 8;

	@Unique private static final URI INFORMEJTYCY_URI = URI.create("https://patrykniemczyk.github.io/informejtycy/");
	@Unique private static final URI GITHUB_URI = URI.create("https://github.com/DawkaWody/Informejtycy-MC/");

	@Shadow protected abstract void createPauseMenu();
	@Shadow public abstract boolean showsPauseMenu();

	@Inject(method = "init", at = @At("HEAD"), cancellable = true)
	private void removeText(CallbackInfo ci) {
		if (this.showsPauseMenu()) {
			this.createPauseMenu();
		}
		ci.cancel();
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void renderLogo(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
		if (!this.showsPauseMenu()) return;

		int menuTop = graphics.guiHeight();
		int menuBottom = 0;
		for (GuiEventListener child : ((Screen) (Object) this).children()) {
			if (child instanceof AbstractWidget widget && widget.visible) {
				menuTop = Math.min(menuTop, widget.getY());
				menuBottom = Math.max(menuBottom, widget.getY() + widget.getHeight());
			}
		}
		if (menuBottom <= 0) return;

		drawLogo(graphics, TOP_LOGO_WIDTH, TOP_LOGO_HEIGHT, 275, 50, 0, menuTop, TOP_LOGO);
		drawLogo(graphics, BOTTOM_LOGO_WIDTH, BOTTOM_LOGO_HEIGHT, 165, 50, menuBottom, graphics.guiHeight(), BOTTOM_LOGO);
	}

	@Unique
	private void drawLogo(GuiGraphicsExtractor context, int width, int height, int maxDrawWidth, int maxDrawHeight,
						  int bandTop, int bandBottom, Identifier logo) {
		int band = bandBottom - bandTop - LOGO_MARGIN * 2;
		if (band < MIN_LOGO_HEIGHT) return;

		int drawHeight = Math.min(maxDrawHeight, band);
		int drawWidth = maxDrawWidth * drawHeight / maxDrawHeight;
		int widthLimit = context.guiWidth() - LOGO_MARGIN * 2;
		if (drawWidth > widthLimit) {
			drawWidth = widthLimit;
			drawHeight = maxDrawHeight * drawWidth / maxDrawWidth;
		}
		if (drawHeight < MIN_LOGO_HEIGHT) return;

		float scaleX = (float) drawWidth / width;
		float scaleY = (float) drawHeight / height;
		int x = (context.guiWidth() - drawWidth) / 2;
		int y = bandTop + LOGO_MARGIN + (band - drawHeight) / 2;
		context.pose().pushMatrix();
		context.pose().translate(x, y);
		context.pose().scale(scaleX, scaleY);
		context.blit(RenderPipelines.GUI_TEXTURED,
				logo, 0, 0, 0, 0, width, height, width, height);
		context.pose().popMatrix();
	}

	@Redirect(method = "createPauseMenu", at = @At(value = "FIELD", target = "Lnet/minecraft/util/CommonLinks;RELEASE_FEEDBACK:Ljava/net/URI;", opcode = Opcodes.GETSTATIC))
	private URI feedbackUri() {
		return GITHUB_URI;
	}

	@Redirect(method = "createPauseMenu", at = @At(value = "FIELD", target = "Lnet/minecraft/util/CommonLinks;SNAPSHOT_BUGS_FEEDBACK:Ljava/net/URI;", opcode = Opcodes.GETSTATIC))
	private URI bugReportUri() {
		return INFORMEJTYCY_URI;
	}
}
