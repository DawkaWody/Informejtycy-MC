package daw.ka.informejtycy.item;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class CustomItemGroups {
	public static CreativeModeTab INFORMEJTYCY_GROUP = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(CustomItems.SILVER_WOLF))
			.title(Component.translatable("itemgroup.informejtycy.informejtycy"))
			.displayItems((displayContext, entries) -> {
				// Blocks
				entries.accept(CustomBlocks.SILVER_WOLF_ORE);
				entries.accept(CustomBlocks.DARK_GLOWSTONE);
				entries.accept(CustomBlocks.THEORY_FORGE_BLOCK);
				entries.accept(CustomBlocks.BRAINROT_TABLE_BLOCK);
				entries.accept(CustomBlocks.RECYCLER_BLOCK);
				entries.accept(CustomBlocks.SILVER_WOLF_BLOCK);
				entries.accept(CustomBlocks.GOLDEN_WOLF_BLOCK);
                entries.accept(CustomBlocks.GLINIANKA_BLOCK);
                entries.accept(CustomBlocks.TRASH_CAN);
				entries.accept(CustomBlocks.BOMBARDINO_COCODRILO);
				entries.accept(CustomBlocks.TRALALERO_TRALALA);
				entries.accept(CustomBlocks.TUNG_TUNG_SAHUR);
				entries.accept(CustomBlocks.CHIMPANZINI_BANANINI);
				// Items
				entries.accept(CustomItems.SILVER_WOLF);
				entries.accept(CustomItems.GOLDEN_WOLF);
				entries.accept(CustomItems.BALLS_UNDER_MAGNIFIER);
				entries.accept(CustomItems.PITCH_CONTEST_TROPHY);
				entries.accept(CustomItems.STICKY_NOTES);
				entries.accept(CustomItems.LIGHT_FOOD);
                entries.accept(CustomItems.ZMYSIO_MILK_BUCKET);
				entries.accept(CustomItems.ZARZYK_GEL);
				entries.accept(CustomItems.CONCENTRATED_ZARZYK_GEL);
				entries.accept(CustomItems.DARK_GLOWSTONE_DUST);
				entries.accept(CustomItems.TALISMAN_OF_SHRIEK);
				entries.accept(CustomItems.PLUS);
				entries.accept(CustomItems.RECYCLABLE_BOTTLE);
				entries.accept(CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC);
				entries.accept(CustomItems.HOLY_WARS_MUSIC_DISC);
				entries.accept(CustomItems.YOU_MUST_BURN_MUSIC_DISC);
				entries.accept(CustomItems.NO_MORE_TEARS_MUSIC_DISC);
                entries.accept(CustomItems.ZALEWIX_BEAT_MUSIC_DISC);
				entries.accept(CustomItems.STELLA_MUSIC_DISC);
				entries.accept(CustomItems.MEGAMIKSKLASA2_MUSIC_DISC);
				entries.accept(CustomItems.INFORMEJTYCY_SWORD);
				entries.accept(CustomItems.INFORMEJTYCY_PICKAXE);
				entries.accept(CustomItems.INFORMEJTYCY_SHOVEL);
				entries.accept(CustomItems.INFORMEJTYCY_AXE);
				entries.accept(CustomItems.INFORMEJTYCY_HOE);
				entries.accept(CustomItems.INFORMEJTYCY_HELMET);
				entries.accept(CustomItems.INFORMEJTYCY_CHESTPLATE);
				entries.accept(CustomItems.INFORMEJTYCY_LEGGINGS);
				entries.accept(CustomItems.INFORMEJTYCY_BOOTS);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_SWORD);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_AXE);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_HOE);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_HELMET);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS);
				entries.accept(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS);
                entries.accept(CustomItems.ZMYSIO_SWORD);
                entries.accept(CustomItems.ZMYSIO_ELYTRA);
				entries.accept(CustomItems.PRESIDENT_HELMET);
			}).build();

	public static void registerAll() {
		INFORMEJTYCY_GROUP = InformejtycyRegistry.registerMenuItemGroup("informejtycy", INFORMEJTYCY_GROUP);
	}
}
