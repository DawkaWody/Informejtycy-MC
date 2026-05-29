package daw.ka.informejtycy.screen;

import daw.ka.informejtycy.screen.handler.BrainrotTableScreenHandler;
import daw.ka.informejtycy.screen.handler.TheoryForgeScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

import static daw.ka.informejtycy.InformejtycyRegistry.id;

public class InformejtycyScreenHandlers {
	public static ScreenHandlerType<TheoryForgeScreenHandler> THEORY_FORGE_SCREEN_HANDLER;
	public static ScreenHandlerType<BrainrotTableScreenHandler> BRAINROT_TABLE_SCREEN_HANDLER;

	public static void registerAll() {
		THEORY_FORGE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, RegistryKey.of(RegistryKeys.SCREEN_HANDLER,
				id("theory_forge_screen_handler")),
				new ExtendedScreenHandlerType<>(TheoryForgeScreenHandler::new, BlockPos.PACKET_CODEC));
		BRAINROT_TABLE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, RegistryKey.of(RegistryKeys.SCREEN_HANDLER,
				id("brainrot_table_screen_handler")),
				new ExtendedScreenHandlerType<>(BrainrotTableScreenHandler::new, BlockPos.PACKET_CODEC));
	}
}
