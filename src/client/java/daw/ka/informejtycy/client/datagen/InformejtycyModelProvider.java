package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class InformejtycyModelProvider extends FabricModelProvider {
	public InformejtycyModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleCubeAll(CustomBlocks.SILVER_WOLF_ORE);
		blockStateModelGenerator.registerSimpleCubeAll(CustomBlocks.DARK_GLOWSTONE);
		blockStateModelGenerator.registerSimpleCubeAll(CustomBlocks.SILVER_WOLF_BLOCK);
		blockStateModelGenerator.registerSimpleCubeAll(CustomBlocks.GOLDEN_WOLF_BLOCK);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(CustomItems.SILVER_WOLF, Models.GENERATED);
		itemModelGenerator.register(CustomItems.GOLDEN_WOLF, Models.GENERATED);
		itemModelGenerator.register(CustomItems.BALLS_UNDER_MAGNIFIER, Models.GENERATED);
		itemModelGenerator.register(CustomItems.STICKY_NOTES, Models.GENERATED);
		itemModelGenerator.register(CustomItems.LIGHT_FOOD, Models.GENERATED);
        itemModelGenerator.register(CustomItems.ZMYSIO_MILK_BUCKET, Models.GENERATED);
		itemModelGenerator.register(CustomItems.ZARZYK_GEL, Models.GENERATED);
		itemModelGenerator.register(CustomItems.CONCENTRATED_ZARZYK_GEL, Models.GENERATED);
        itemModelGenerator.register(CustomItems.DARK_GLOWSTONE_DUST, Models.GENERATED);
        itemModelGenerator.register(CustomItems.TALISMAN_OF_SHRIEK, Models.GENERATED);
        itemModelGenerator.register(CustomItems.PLUS, Models.GENERATED);
		itemModelGenerator.register(CustomItems.RECYCLABLE_BOTTLE, Models.GENERATED);
		itemModelGenerator.register(CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC, Models.GENERATED);
		itemModelGenerator.register(CustomItems.HOLY_WARS_MUSIC_DISC, Models.GENERATED);
		itemModelGenerator.register(CustomItems.YOU_MUST_BURN_MUSIC_DISC, Models.GENERATED);
		itemModelGenerator.register(CustomItems.NO_MORE_TEARS_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(CustomItems.ZALEWIX_BEAT_MUSIC_DISC, Models.GENERATED);
		itemModelGenerator.register(CustomItems.STELLA_MUSIC_DISC, Models.GENERATED);
		itemModelGenerator.register(CustomItems.MEGAMIKSKLASA2_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(CustomItems.ZMYSIO_ELYTRA, Models.GENERATED);

        itemModelGenerator.register(CustomItems.INFORMEJTYCY_SWORD, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.INFORMEJTYCY_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.INFORMEJTYCY_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.INFORMEJTYCY_AXE, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.INFORMEJTYCY_HOE, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.REINFORCED_INFORMEJTYCY_SWORD, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.REINFORCED_INFORMEJTYCY_AXE, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.REINFORCED_INFORMEJTYCY_HOE, Models.HANDHELD);
        itemModelGenerator.register(CustomItems.ZMYSIO_SWORD, Models.HANDHELD);

		itemModelGenerator.registerArmor(CustomItems.INFORMEJTYCY_HELMET, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("informejtycy")), ItemModelGenerator.HELMET_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.INFORMEJTYCY_CHESTPLATE, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("informejtycy")), ItemModelGenerator.CHESTPLATE_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.INFORMEJTYCY_LEGGINGS, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("informejtycy")), ItemModelGenerator.LEGGINGS_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.INFORMEJTYCY_BOOTS, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("informejtycy")), ItemModelGenerator.BOOTS_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.REINFORCED_INFORMEJTYCY_HELMET, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("reinforced_informejtycy")), ItemModelGenerator.HELMET_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("reinforced_informejtycy")), ItemModelGenerator.CHESTPLATE_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("reinforced_informejtycy")), ItemModelGenerator.LEGGINGS_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("reinforced_informejtycy")), ItemModelGenerator.BOOTS_TRIM_ID_PREFIX, false);
		itemModelGenerator.registerArmor(CustomItems.PRESIDENT_HELMET, RegistryKey.of(
				RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset")),
				InformejtycyRegistry.id("president_helmet")), ItemModelGenerator.HELMET_TRIM_ID_PREFIX, false);
	}
}
