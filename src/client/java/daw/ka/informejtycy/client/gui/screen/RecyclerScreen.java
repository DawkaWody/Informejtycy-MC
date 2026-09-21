package daw.ka.informejtycy.client.gui.screen;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.screen.handler.RecyclerScreenHandler;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class RecyclerScreen extends AbstractContainerScreen<RecyclerScreenHandler> {
    private static final Identifier GUI_TEXTURE = InformejtycyRegistry.id("textures/gui/recycler/recycler_gui.png");
    private static final Identifier ARROW_TEXTURE = InformejtycyRegistry.id("textures/gui/recycler/progress_arrow.png");

    public RecyclerScreen(RecyclerScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderProgressArrow(context, x, y);
    }


    private void renderProgressArrow(GuiGraphicsExtractor context, int x, int y) {
        if (menu.isCrafting()) {
            context.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 74, y + 40, 0, 0, 26, menu.getScaledArrowProgress(), 32, 32);
        }
    }
}
