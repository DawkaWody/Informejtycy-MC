package daw.ka.informejtycy.loot;

import daw.ka.informejtycy.loot.modifier.DungeonChestLootTableModifier;
import daw.ka.informejtycy.loot.modifier.MelonLootTableModifier;
import daw.ka.informejtycy.loot.modifier.ReinforcedDeepslateLootTableModifier;
import daw.ka.informejtycy.loot.modifier.WardenLootTableModifier;

public class InformejtycyLootTableModifiers {
	public static void registerAll() {
		WardenLootTableModifier.register();
		MelonLootTableModifier.register();
		ReinforcedDeepslateLootTableModifier.register();
        DungeonChestLootTableModifier.register();
	}
}
