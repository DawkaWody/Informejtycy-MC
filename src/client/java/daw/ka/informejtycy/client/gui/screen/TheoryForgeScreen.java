package daw.ka.informejtycy.client.gui.screen;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.screen.handler.TheoryForgeScreenHandler;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TheoryForgeScreen extends AbstractContainerScreen<TheoryForgeScreenHandler> {
	private static final Identifier GUI_TEXTURE = InformejtycyRegistry.id("textures/gui/theory_forge/theory_forge_gui.png");
	private static final Identifier ARROW_TEXTURE = InformejtycyRegistry.id("textures/gui/theory_forge/progress_arrow.png");
	private static final Identifier LAVA_TANK_TEXTURE = InformejtycyRegistry.id("textures/gui/theory_forge/lava_tank_fill.png");

	public TheoryForgeScreen(TheoryForgeScreenHandler handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		context.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
		renderProgressArrow(context, x, y);
		renderLavaTankFill(context, x, y);
	}


	private void renderProgressArrow(GuiGraphicsExtractor context, int x, int y) {
		if (menu.isCrafting()) {
			context.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 106, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 32, 32);
		}
	}

	private void renderLavaTankFill(GuiGraphicsExtractor context, int x, int y) {
		context.blit(RenderPipelines.GUI_TEXTURED, LAVA_TANK_TEXTURE, x + 53, y + 54, 0, 0, menu.getScaledLavaFill(), 16, 64, 16);
	}
}
