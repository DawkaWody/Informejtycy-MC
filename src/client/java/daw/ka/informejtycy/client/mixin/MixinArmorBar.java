package daw.ka.informejtycy.client.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class MixinArmorBar {
	@Unique private static final Identifier ARMOR_FULL = Identifier.withDefaultNamespace("hud/armor_full");
	@Unique private static final Identifier ARMOR_HALF = Identifier.withDefaultNamespace("hud/armor_half");

	@Inject(method = "extractArmor", at = @At("TAIL"))
	private static void extractExtraArmorRows(GuiGraphicsExtractor graphics, Player player, int healthY, int healthRows,
											  int rowSpacing, int x, CallbackInfo ci) {
		int armor = player.getArmorValue();
		int baseY = healthY - (healthRows - 1) * rowSpacing - 10;

		for (int row = 1; row * 20 < armor; row++) {
			int y = baseY - row * 10;
			int rowArmor = armor - row * 20;

			for (int i = 0; i < 10 && i * 2 < rowArmor; i++) {
				Identifier sprite = i * 2 + 1 < rowArmor ? ARMOR_FULL : ARMOR_HALF;
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x + i * 8, y, 9, 9);
			}
		}
	}
}
