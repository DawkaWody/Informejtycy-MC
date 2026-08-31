package daw.ka.informejtycy;

import daw.ka.informejtycy.event.InformejtycyEvents;
import daw.ka.informejtycy.loot.InformejtycyLootTableModifiers;
import daw.ka.informejtycy.screen.InformejtycyScreenHandlers;
import daw.ka.informejtycy.structure.InformejtycyStructureOverrides;
import daw.ka.informejtycy.world.gen.InformejtycyWorldGen;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Informejtycy implements ModInitializer {
	public static final String MOD_ID = "informejtycy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Informejtycy mod");

		InformejtycyRegistry.registerAll();
		InformejtycyWorldGen.registerGenerators();
		InformejtycyLootTableModifiers.registerAll();
		InformejtycyScreenHandlers.registerAll();
		InformejtycyEvents.registerAll();
		InformejtycyStructureOverrides.registerAll();
	}
}
