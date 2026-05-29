package daw.ka.informejtycy.client.gui.screen;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.screen.handler.TheoryForgeScreenHandler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TheoryForgeScreen extends HandledScreen<TheoryForgeScreenHandler> {
	private static final Identifier GUI_TEXTURE = InformejtycyRegistry.id("textures/gui/theory_forge/theory_forge_gui.png");
	private static final Identifier ARROW_TEXTURE = InformejtycyRegistry.id("textures/gui/theory_forge/progress_arrow.png");
	private static final Identifier LAVA_TANK_TEXTURE = InformejtycyRegistry.id("textures/gui/theory_forge/lava_tank_fill.png");

	public TheoryForgeScreen(TheoryForgeScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		context.drawTexture(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
		renderProgressArrow(context, x, y);
		renderLavaTankFill(context, x, y);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}

	private void renderProgressArrow(DrawContext context, int x, int y) {
		if (handler.isCrafting()) {
			context.drawTexture(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 106, y + 35, 0, 0, handler.getScaledArrowProgress(), 16, 32, 32);
		}
	}

	private void renderLavaTankFill(DrawContext context, int x, int y) {
		context.drawTexture(RenderPipelines.GUI_TEXTURED, LAVA_TANK_TEXTURE, x + 53, y + 54, 0, 0, handler.getScaledLavaFill(), 16, 64, 16);
	}
}
