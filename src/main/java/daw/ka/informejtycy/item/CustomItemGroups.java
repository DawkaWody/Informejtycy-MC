package daw.ka.informejtycy.item;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class CustomItemGroups {
	public static ItemGroup INFORMEJTYCY_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(CustomItems.SILVER_WOLF))
			.displayName(Text.translatable("itemgroup.informejtycy.informejtycy"))
			.entries((displayContext, entries) -> {
				// Blocks
				entries.add(CustomBlocks.SILVER_WOLF_ORE);
				entries.add(CustomBlocks.DARK_GLOWSTONE);
				entries.add(CustomBlocks.THEORY_FORGE_BLOCK);
				entries.add(CustomBlocks.BRAINROT_TABLE_BLOCK);
				entries.add(CustomBlocks.SILVER_WOLF_BLOCK);
				entries.add(CustomBlocks.GOLDEN_WOLF_BLOCK);
                entries.add(CustomBlocks.GLINIANKA_BLOCK);
                entries.add(CustomBlocks.TRASH_CAN);
				entries.add(CustomBlocks.BOMBARDINO_COCODRILO);
				entries.add(CustomBlocks.TRALALERO_TRALALA);
				entries.add(CustomBlocks.TUNG_TUNG_SAHUR);
				entries.add(CustomBlocks.CHIMPANZINI_BANANINI);
				// Items
				entries.add(CustomItems.SILVER_WOLF);
				entries.add(CustomItems.GOLDEN_WOLF);
				entries.add(CustomItems.BALLS_UNDER_MAGNIFIER);
				entries.add(CustomItems.PITCH_CONTEST_TROPHY);
				entries.add(CustomItems.STICKY_NOTES);
				entries.add(CustomItems.LIGHT_FOOD);
                entries.add(CustomItems.ZMYSIO_MILK_BUCKET);
				entries.add(CustomItems.ZARZYK_GEL);
				entries.add(CustomItems.CONCENTRATED_ZARZYK_GEL);
				entries.add(CustomItems.DARK_GLOWSTONE_DUST);
				entries.add(CustomItems.TALISMAN_OF_SHRIEK);
				entries.add(CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC);
				entries.add(CustomItems.HOLY_WARS_MUSIC_DISC);
				entries.add(CustomItems.YOU_MUST_BURN_MUSIC_DISC);
				entries.add(CustomItems.NO_MORE_TEARS_MUSIC_DISC);
                entries.add(CustomItems.ZALEWIX_BEAT_MUSIC_DISC);
				entries.add(CustomItems.STELLA_MUSIC_DISC);
				entries.add(CustomItems.INFORMEJTYCY_SWORD);
				entries.add(CustomItems.INFORMEJTYCY_PICKAXE);
				entries.add(CustomItems.INFORMEJTYCY_SHOVEL);
				entries.add(CustomItems.INFORMEJTYCY_AXE);
				entries.add(CustomItems.INFORMEJTYCY_HOE);
				entries.add(CustomItems.INFORMEJTYCY_HELMET);
				entries.add(CustomItems.INFORMEJTYCY_CHESTPLATE);
				entries.add(CustomItems.INFORMEJTYCY_LEGGINGS);
				entries.add(CustomItems.INFORMEJTYCY_BOOTS);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_SWORD);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_AXE);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_HOE);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_HELMET);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS);
				entries.add(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS);
                entries.add(CustomItems.ZMYSIO_SWORD);
                entries.add(CustomItems.ZMYSIO_ELYTRA);
				entries.add(CustomItems.PRESIDENT_HELMET);
			}).build();

	public static void registerAll() {
		INFORMEJTYCY_GROUP = InformejtycyRegistry.registerMenuItemGroup("informejtycy", INFORMEJTYCY_GROUP);
	}
}
