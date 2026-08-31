package daw.ka.informejtycy.client;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.client.anticheat.InformejtycyAnticheatClient;
import daw.ka.informejtycy.client.entity.projectile.milk.MilkProjectileRenderer;
import daw.ka.informejtycy.client.entity.zarzyk.ZarzykEntityModel;
import daw.ka.informejtycy.client.entity.zarzyk.ZarzykEntityRenderer;
import daw.ka.informejtycy.client.entity.zmysio.ZmysioEntityModel;
import daw.ka.informejtycy.client.entity.zmysio.ZmysioEntityRenderer;
import daw.ka.informejtycy.client.gui.screen.BrainrotTableScreen;
import daw.ka.informejtycy.client.gui.screen.RecyclerScreen;
import daw.ka.informejtycy.client.gui.screen.TheoryForgeScreen;
import daw.ka.informejtycy.client.particle.StinkAreaParticle;
import daw.ka.informejtycy.client.particle.StinkParticle;
import daw.ka.informejtycy.entity.CustomEntities;
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
		HandledScreens.register(InformejtycyScreenHandlers.RECYCLER_SCREEN_HANDLER, RecyclerScreen::new);

		ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipType type, java.util.List<Text> lines) -> {
			Item item = stack.getItem();
			String translationKey = item.getTranslationKey();
			int lastDot = translationKey.lastIndexOf('.');
			String path = lastDot >= 0 ? translationKey.substring(lastDot + 1) : translationKey;
			switch (path) {
				case "silver_wolf_ore":
					lines.add(Text.translatable("tooltip.informejtycy.silver_wolf_ore")
							.styled(style -> style.withColor(Formatting.GRAY)));
					break;
				case "glinianka_block":
					lines.add(Text.translatable("tooltip.informejtycy.glinianka_block.line1")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.glinianka_block.line2")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					break;
				case "trash_can":
					lines.add(Text.translatable("tooltip.informejtycy.trash_can.line1")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.trash_can.line2")
							.styled(style -> style.withColor(Formatting.GRAY).withBold(true)));
					break;
				case "silver_wolf":
					lines.add(Text.translatable("tooltip.informejtycy.silver_wolf.line1")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.silver_wolf.line2")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					break;
				case "golden_wolf":
					lines.add(Text.translatable("tooltip.informejtycy.golden_wolf.line1")
							.styled(style -> style.withColor(Formatting.AQUA)));
					lines.add(Text.translatable("tooltip.informejtycy.golden_wolf.line2")
							.styled(style -> style.withColor(Formatting.AQUA)));
					break;
				case "balls_under_magnifier":
					lines.add(Text.translatable("tooltip.informejtycy.balls_under_magnifier.line1")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.balls_under_magnifier.line2")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.balls_under_magnifier.line3")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					break;
				case "pitch_contest_trophy":
					lines.add(Text.translatable("tooltip.informejtycy.pitch_contest_trophy")
							.styled(style -> style.withColor(Formatting.GOLD).withBold(true)));
					break;
				case "sticky_notes":
					lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line1")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line2")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line3")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.sticky_notes.line4")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					break;
				case "light_food":
					lines.add(Text.translatable("tooltip.informejtycy.light_food.line1"));
					lines.add(Text.translatable("tooltip.informejtycy.light_food.line2"));
					break;
				case "zmysio_milk_bucket":
					lines.add(Text.translatable("tooltip.informejtycy.zmysio_milk_bucket")
							.styled(style -> style.withColor(Formatting.GRAY)));
					break;
				case "zarzyk_gel_bottle":
				case "zarzyk_gel":
					lines.add(Text.translatable("tooltip.informejtycy.zarzyk_gel_bottle.line1")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					lines.add(Text.translatable("tooltip.informejtycy.zarzyk_gel_bottle.line2")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					break;
				case "talisman_of_shriek":
					lines.add(Text.translatable("tooltip.informejtycy.talisman_of_shriek")
							.styled(style -> style.withColor(Formatting.DARK_AQUA)));
					break;
				case "recyclable_bottle":
					lines.add(Text.translatable("tooltip.informejtycy.recyclable_bottle")
							.styled(style -> style.withColor(Formatting.GRAY).withItalic(true)));
					break;
				default:
					break;
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
