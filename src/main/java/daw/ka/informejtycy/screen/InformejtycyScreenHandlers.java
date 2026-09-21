package daw.ka.informejtycy.screen;

import daw.ka.informejtycy.screen.handler.BrainrotTableScreenHandler;
import daw.ka.informejtycy.screen.handler.RecyclerScreenHandler;
import daw.ka.informejtycy.screen.handler.TheoryForgeScreenHandler;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.BlockPos;

import static daw.ka.informejtycy.InformejtycyRegistry.id;

public class InformejtycyScreenHandlers {
	public static MenuType<TheoryForgeScreenHandler> THEORY_FORGE_SCREEN_HANDLER;
	public static MenuType<BrainrotTableScreenHandler> BRAINROT_TABLE_SCREEN_HANDLER;
	public static MenuType<RecyclerScreenHandler> RECYCLER_SCREEN_HANDLER;

	public static void registerAll() {
		THEORY_FORGE_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, ResourceKey.create(Registries.MENU,
				id("theory_forge_screen_handler")),
				new ExtendedMenuType<>(TheoryForgeScreenHandler::new, BlockPos.STREAM_CODEC));
		BRAINROT_TABLE_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, ResourceKey.create(Registries.MENU,
				id("brainrot_table_screen_handler")),
				new ExtendedMenuType<>(BrainrotTableScreenHandler::new, BlockPos.STREAM_CODEC));
		RECYCLER_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, ResourceKey.create(Registries.MENU,
				id("recycler_screen_handler")),
				new ExtendedMenuType<>(RecyclerScreenHandler::new, BlockPos.STREAM_CODEC));
	}
}
