package daw.ka.informejtycy.loot;

import daw.ka.informejtycy.loot.modifier.*;

public class InformejtycyLootTableModifiers {
	public static void registerAll() {
		MobLootTableModifiers.register();
		BlockLootTableModifiers.register();
        DungeonChestLootTableModifier.register();
	}
}
