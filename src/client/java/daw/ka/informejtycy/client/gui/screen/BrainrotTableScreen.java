package daw.ka.informejtycy.client.gui.screen;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.screen.handler.BrainrotTableScreenHandler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BrainrotTableScreen extends HandledScreen<BrainrotTableScreenHandler> {
	private static final Identifier GUI_TEXTURE = InformejtycyRegistry.id("textures/gui/brainrot_table/recycler_gui.png");
	private static final Identifier ARROW_TEXTURE = InformejtycyRegistry.id("textures/gui/brainrot_table/progress_arrow.png");

	public BrainrotTableScreen(BrainrotTableScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		context.drawTexture(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
		renderProgressArrow(context, x, y);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}

	private void renderProgressArrow(DrawContext context, int x, int y) {
		if (handler.isCrafting()) {
			context.drawTexture(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 78, y + 35, 0, 0, handler.getScaledArrowProgress(), 16, 32, 32);
		}
	}
}
