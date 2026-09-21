package daw.ka.informejtycy.client;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.client.anticheat.InformejtycyAnticheatClient;
import daw.ka.informejtycy.client.entity.projectile.milk.MilkProjectileRenderer;
import daw.ka.informejtycy.client.entity.zarzyk.ZarzykEntityModel;
import daw.ka.informejtycy.client.entity.zarzyk.ZarzykEntityRenderer;
import daw.ka.informejtycy.client.entity.zmysio.ZmysioEntityModel;
import daw.ka.informejtycy.client.entity.zmysio.ZmysioEntityRenderer;
import daw.ka.informejtycy.client.gui.TitleScreenVideo;
import daw.ka.informejtycy.client.gui.screen.BrainrotTableScreen;
import daw.ka.informejtycy.client.gui.screen.RecyclerScreen;
import daw.ka.informejtycy.client.gui.screen.TheoryForgeScreen;
import daw.ka.informejtycy.client.particle.StinkAreaParticle;
import daw.ka.informejtycy.client.particle.StinkParticle;
import daw.ka.informejtycy.entity.CustomEntities;
import daw.ka.informejtycy.particle.CustomParticles;
import daw.ka.informejtycy.screen.InformejtycyScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class InformejtycyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Informejtycy.LOGGER.info("Initializing Informejtycy Client");

        ModelLayerRegistry.registerModelLayer(ZmysioEntityModel.ZMYSIO, ZmysioEntityModel::getTexturedModelData);
		ModelLayerRegistry.registerModelLayer(ZarzykEntityModel.ZARZYK, ZarzykEntityModel::getTexturedModelData);
        EntityRenderers.register(CustomEntities.ZMYSIO_BOSS, ZmysioEntityRenderer::new);
		EntityRenderers.register(CustomEntities.ZARZYK, ZarzykEntityRenderer::new);
        EntityRenderers.register(CustomEntities.MILK_PROJECTILE, MilkProjectileRenderer::new);

        ParticleProviderRegistry.getInstance().register(CustomParticles.STINK_PARTICLE, StinkParticle.Factory::new);
        ParticleProviderRegistry.getInstance().register(CustomParticles.STINK_AREA_PARTICLE, StinkAreaParticle.Factory::new);

		MenuScreens.register(InformejtycyScreenHandlers.THEORY_FORGE_SCREEN_HANDLER, TheoryForgeScreen::new);
		MenuScreens.register(InformejtycyScreenHandlers.BRAINROT_TABLE_SCREEN_HANDLER, BrainrotTableScreen::new);
		MenuScreens.register(InformejtycyScreenHandlers.RECYCLER_SCREEN_HANDLER, RecyclerScreen::new);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.level != null && TitleScreenVideo.isPlaying()) {
				TitleScreenVideo.stop();
			}
		});

		ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipFlag type, java.util.List<Component> lines) -> {
			Item item = stack.getItem();
			String translationKey = item.getDescriptionId();
			int lastDot = translationKey.lastIndexOf('.');
			String path = lastDot >= 0 ? translationKey.substring(lastDot + 1) : translationKey;
			switch (path) {
				case "silver_wolf_ore":
					lines.add(Component.translatable("tooltip.informejtycy.silver_wolf_ore")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY)));
					break;
				case "glinianka_block":
					lines.add(Component.translatable("tooltip.informejtycy.glinianka_block.line1")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.glinianka_block.line2")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					break;
				case "trash_can":
					lines.add(Component.translatable("tooltip.informejtycy.trash_can.line1")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.trash_can.line2")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withBold(true)));
					break;
				case "silver_wolf":
					lines.add(Component.translatable("tooltip.informejtycy.silver_wolf.line1")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.silver_wolf.line2")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					break;
				case "golden_wolf":
					lines.add(Component.translatable("tooltip.informejtycy.golden_wolf.line1")
							.withStyle(style -> style.withColor(ChatFormatting.AQUA)));
					lines.add(Component.translatable("tooltip.informejtycy.golden_wolf.line2")
							.withStyle(style -> style.withColor(ChatFormatting.AQUA)));
					break;
				case "balls_under_magnifier":
					lines.add(Component.translatable("tooltip.informejtycy.balls_under_magnifier.line1")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.balls_under_magnifier.line2")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.balls_under_magnifier.line3")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					break;
				case "pitch_contest_trophy":
					lines.add(Component.translatable("tooltip.informejtycy.pitch_contest_trophy")
							.withStyle(style -> style.withColor(ChatFormatting.GOLD).withBold(true)));
					break;
				case "sticky_notes":
					lines.add(Component.translatable("tooltip.informejtycy.sticky_notes.line1")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.sticky_notes.line2")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.sticky_notes.line3")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.sticky_notes.line4")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					break;
				case "light_food":
					lines.add(Component.translatable("tooltip.informejtycy.light_food.line1"));
					lines.add(Component.translatable("tooltip.informejtycy.light_food.line2"));
					break;
				case "zmysio_milk_bucket":
					lines.add(Component.translatable("tooltip.informejtycy.zmysio_milk_bucket")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY)));
					break;
				case "zarzyk_gel_bottle":
				case "zarzyk_gel":
					lines.add(Component.translatable("tooltip.informejtycy.zarzyk_gel_bottle.line1")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					lines.add(Component.translatable("tooltip.informejtycy.zarzyk_gel_bottle.line2")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					break;
				case "talisman_of_shriek":
					lines.add(Component.translatable("tooltip.informejtycy.talisman_of_shriek")
							.withStyle(style -> style.withColor(ChatFormatting.DARK_AQUA)));
					break;
				case "recyclable_bottle":
					lines.add(Component.translatable("tooltip.informejtycy.recyclable_bottle")
							.withStyle(style -> style.withColor(ChatFormatting.GRAY).withItalic(true)));
					break;
				default:
					break;
			}
		});

        InformejtycyAnticheatClient.init();
		InformejtycyDiscordRP.init();
		Thread discordThread = new Thread(() -> {
			while (true) {
				try {
					if (Minecraft.getInstance().isRunning()) {
						InformejtycyDiscordRP.update();
					}
				} catch (RuntimeException e) {
					Informejtycy.LOGGER.warn("Failed to update Discord Rich Presence", e);
				}

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					InformejtycyDiscordRP.stop();
					break;
				}
			}
		}, "Informejtycy-DiscordRP");
		discordThread.setDaemon(true);
		discordThread.start();
	}
}
