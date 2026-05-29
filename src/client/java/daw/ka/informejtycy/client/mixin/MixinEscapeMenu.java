package daw.ka.informejtycy.client.mixin;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URI;

@Mixin(value = GameMenuScreen.class)
public abstract class MixinEscapeMenu {
	@Unique private static final URI INFORMEJTYCY_URI = URI.create("https://informejtycy.pl/");
	@Unique private static final Identifier TOP_LOGO = InformejtycyRegistry.id("textures/gui/pause_menu_logo.png");
	@Unique private static final int TOP_LOGO_WIDTH = 2048;
	@Unique private static final int TOP_LOGO_HEIGHT = 350;

	@Unique private static final Identifier BOTTOM_LOGO = InformejtycyRegistry.id("textures/gui/pause_menu_logo2.png");
	@Unique private static final int BOTTOM_LOGO_WIDTH = 1400;
	@Unique private static final int BOTTOM_LOGO_HEIGHT = 450;

	@Shadow @Final private boolean showMenu;
	@Shadow protected abstract void initWidgets();
	@Shadow public abstract boolean shouldShowMenu();

	@Shadow @Final private static Text SEND_FEEDBACK_TEXT;

	@Shadow @Final private static Text REPORT_BUGS_TEXT;

	@Inject(method = "init", at = @At("HEAD"), cancellable = true)
	private void removeText(CallbackInfo ci) {
		if (this.showMenu) {
			this.initWidgets();
		}
		ci.cancel();
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void renderLogo(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
		if (this.shouldShowMenu()) {
			drawLogo(context, TOP_LOGO_WIDTH, TOP_LOGO_HEIGHT, 275, 50, 10, TOP_LOGO);
			drawLogo(context, BOTTOM_LOGO_WIDTH, BOTTOM_LOGO_HEIGHT, 165, 50, 195, BOTTOM_LOGO);
		}
	}

	@Inject(method = "addFeedbackAndBugsButtons", at = @At("HEAD"), cancellable = true)
	private static void swapButtonLinks(Screen parentScreen, GridWidget.Adder gridAdder, CallbackInfo ci) {
		gridAdder.add(ButtonWidget.builder(SEND_FEEDBACK_TEXT, ConfirmLinkScreen.opening(parentScreen, INFORMEJTYCY_URI)).width(98).build());
		gridAdder.add(ButtonWidget.builder(REPORT_BUGS_TEXT, ConfirmLinkScreen.opening(parentScreen, INFORMEJTYCY_URI)).width(98).build()).active = !SharedConstants.getGameVersion().dataVersion().isNotMainSeries();
		ci.cancel();
	}

	@Unique
	private void drawLogo(DrawContext context, int width, int height, int drawWidth, int drawHeight, int y, Identifier logo) {
		float scaleX = (float) drawWidth / width;
		float scaleY = (float) drawHeight / height;
		int x = context.getScaledWindowWidth() / 2 - drawWidth / 2;
		context.getMatrices().pushMatrix();
		context.getMatrices().translate(x, y);
		context.getMatrices().scale(scaleX, scaleY);
		context.drawTexture(RenderPipelines.GUI_TEXTURED,
				logo, 0, 0, 0, 0, width, height, width, height);
		context.getMatrices().popMatrix();
	}
}
