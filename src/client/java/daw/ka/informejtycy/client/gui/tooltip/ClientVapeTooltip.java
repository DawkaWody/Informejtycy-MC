package daw.ka.informejtycy.client.gui.tooltip;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.component.custom.VapeContents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class ClientVapeTooltip implements ClientTooltipComponent {
    private static final Identifier SLOT_BACKGROUND_SPRITE = InformejtycyRegistry.id("container/vape/slot_background");
    private static final Identifier SLOT_HIGHLIGHT_BACK_SPRITE = InformejtycyRegistry.id("container/vape/slot_highlight_back");
    private static final Identifier SLOT_HIGHLIGHT_FRONT_SPRITE = InformejtycyRegistry.id("container/vape/slot_highlight_front");
    private static final int SLOT_SIZE = 24;

    private final VapeContents contents;

    public ClientVapeTooltip(VapeContents contents) {
        this.contents = contents;
    }

    @Override
    public int getHeight(@NonNull Font font) {
        return SLOT_SIZE + 4;
    }

    @Override
    public int getWidth(@NonNull Font font) {
        return SLOT_SIZE * VapeContents.MAX_POTIONS;
    }

    @Override
    public boolean showTooltipWithItemInHand() {
        return true;
    }

    @Override
    public void extractImage(@NonNull Font font, int x, int y, int width, int height, @NonNull GuiGraphicsExtractor graphics) {
        int startX = x + (width - getWidth(font)) / 2;
        int nextIndex = contents.size() - 1;

        for (int i = 0; i < VapeContents.MAX_POTIONS; i++) {
            int slotX = startX + i * SLOT_SIZE;
            boolean highlighted = i == nextIndex;

            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, highlighted ? SLOT_HIGHLIGHT_BACK_SPRITE : SLOT_BACKGROUND_SPRITE, slotX, y, SLOT_SIZE, SLOT_SIZE);
            if (i < contents.size()) {
                ItemStack potion = contents.getPotion(i);
                graphics.item(potion, slotX + 4, y + 4, i);
                graphics.itemDecorations(font, potion, slotX + 4, y + 4);
            }
            if (highlighted) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, slotX, y, SLOT_SIZE, SLOT_SIZE);
            }
        }
    }
}
