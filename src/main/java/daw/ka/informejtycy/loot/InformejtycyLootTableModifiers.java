package daw.ka.informejtycy.loot;

import daw.ka.informejtycy.loot.modifier.*;

public class InformejtycyLootTableModifiers {
	public static void registerAll() {
		MobLootTableModifier.register();
		MelonLootTableModifier.register();
		ReinforcedDeepslateLootTableModifier.register();
        DungeonChestLootTableModifier.register();
	}
}
