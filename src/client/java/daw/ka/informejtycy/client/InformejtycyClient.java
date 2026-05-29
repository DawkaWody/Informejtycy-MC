package daw.ka.informejtycy.client;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.client.anticheat.InformejtycyAnticheatClient;
import daw.ka.informejtycy.client.entity.projectile.milk.MilkProjectileRenderer;
import daw.ka.informejtycy.client.entity.zarzyk.ZarzykEntityModel;
import daw.ka.informejtycy.client.entity.zarzyk.ZarzykEntityRenderer;
import daw.ka.informejtycy.client.entity.zmysio.ZmysioEntityModel;
import daw.ka.informejtycy.client.entity.zmysio.ZmysioEntityRenderer;
import daw.ka.informejtycy.client.gui.screen.BrainrotTableScreen;
import daw.ka.informejtycy.client.gui.screen.TheoryForgeScreen;
import daw.ka.informejtycy.client.particle.StinkAreaParticle;
import daw.ka.informejtycy.client.particle.StinkParticle;
import daw.ka.informejtycy.entity.CustomEntities;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.particle.CustomParticles;
import daw.ka.informejtycy.screen.InformejtycyScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class InformejtycyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Informejtycy.LOGGER.info("Initializing Informejtycy Client");

        EntityModelLayerRegistry.registerModelLayer(ZmysioEntityModel.ZMYSIO, ZmysioEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ZarzykEntityModel.ZARZYK, ZarzykEntityModel::getTexturedModelData);
        EntityRendererFactories.register(CustomEntities.ZMYSIO_BOSS, ZmysioEntityRenderer::new);
		EntityRendererFactories.register(CustomEntities.ZARZYK, ZarzykEntityRenderer::new);
        EntityRendererFactories.register(CustomEntities.MILK_PROJECTILE, MilkProjectileRenderer::new);

        ParticleFactoryRegistry.getInstance().register(CustomParticles.STINK_PARTICLE, StinkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(CustomParticles.STINK_AREA_PARTICLE, StinkAreaParticle.Factory::new);

		HandledScreens.register(InformejtycyScreenHandlers.THEORY_FORGE_SCREEN_HANDLER, TheoryForgeScreen::new);
		HandledScreens.register(InformejtycyScreenHandlers.BRAINROT_TABLE_SCREEN_HANDLER, BrainrotTableScreen::new);

		ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipType type, java.util.List<Text> lines) -> {
			if (stack.getItem() == CustomBlocks.SILVER_WOLF_ORE.asItem()) {
				lines.add(Text.translatable("tooltip.informejtycy.silver_wolf_ore")
						.styled(style -> style.withColor(Formatting.GRAY)));
			} else if (stack.getItem() == CustomBlocks.GLINIANKA_BLOCK.asItem()) {
                lines.add(Text.translatable("tooltip.informejtycy.glinianka_block.line1")
                        .styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
                lines.add(Text.translatable("tooltip.informejtycy.glinianka_block.line2")
                        .styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
            } else if (stack.getItem() == CustomBlocks.TRASH_CAN.asItem()) {
                lines.add(Text.translatable("tooltip.informejtycy.trash_can.line1")
                        .styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
                lines.add(Text.translatable("tooltip.informejtycy.trash_can.line2")
                        .styled(style -> style.withColor(Formatting.GRAY).withBold(true)));
            } else if (stack.getItem() == CustomItems.SILVER_WOLF) {
				lines.add(Text.translatable("tooltip.informejtycy.silver_wolf.line1")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.silver_wolf.line2")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
			} else if (stack.getItem() == CustomItems.GOLDEN_WOLF) {
				lines.add(Text.translatable("tooltip.informejtycy.golden_wolf.line1")
						.styled(style -> style.withColor(Formatting.AQUA)));
				lines.add(Text.translatable("tooltip.informejtycy.golden_wolf.line2")
						.styled(style -> style.withColor(Formatting.AQUA)));
			} else if (stack.getItem() == CustomItems.BALLS_UNDER_MAGNIFIER) {
				lines.add(Text.translatable("tooltip.informejtycy.balls_under_magnifier.line1")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.balls_under_magnifier.line2")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.balls_under_magnifier.line3")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
			} else if (stack.getItem() == CustomItems.PITCH_CONTEST_TROPHY) {
				lines.add(Text.translatable("tooltip.informejtycy.pitch_contest_trophy")
						.styled(style -> style.withColor(Formatting.GOLD).withBold(true)));
			} else if (stack.getItem() == CustomItems.STICKY_NOTES) {
				lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line1")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line2")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line3")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line4")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
			} else if (stack.getItem() == CustomItems.LIGHT_FOOD) {
				lines.add(Text.translatable("tooltip.informejtycy.light_food.line1"));
				lines.add(Text.translatable("tooltip.informejtycy.light_food.line2"));
			} else if (stack.getItem() == CustomItems.ZMYSIO_MILK_BUCKET) {
                lines.add(Text.translatable("tooltip.informejtycy.zmysio_milk_bucket")
                        .styled(style -> style.withColor(Formatting.GRAY)));
            } else if (stack.getItem() == CustomItems.ZARZYK_GEL) {
				lines.add(Text.translatable("tooltip.informejtycy.zarzyk_gel_bottle.line1")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
				lines.add(Text.translatable("tooltip.informejtycy.zarzyk_gel_bottle.line2")
						.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
			} else if (stack.getItem() == CustomItems.TALISMAN_OF_SHRIEK) {
				lines.add(Text.translatable("tooltip.informejtycy.talisman_of_shriek")
						.styled(style -> style.withColor(Formatting.DARK_AQUA)));
			}
		});

        InformejtycyAnticheatClient.init();
		InformejtycyDiscordRP.init();
		new Thread(() -> {
			while (true) {
				try {
					if (MinecraftClient.getInstance().isRunning()) {
						InformejtycyDiscordRP.update();
					}
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					InformejtycyDiscordRP.stop();
					break;
				}
			}
		}, "Informejtycy-DiscordRP").start();
	}
}
