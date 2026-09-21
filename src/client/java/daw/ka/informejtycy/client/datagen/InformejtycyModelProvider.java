package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import java.util.Map;

public class InformejtycyModelProvider extends FabricModelProvider {
	public InformejtycyModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createTrivialCube(CustomBlocks.SILVER_WOLF_ORE);
		blockStateModelGenerator.createTrivialCube(CustomBlocks.DARK_GLOWSTONE);
		blockStateModelGenerator.createTrivialCube(CustomBlocks.SILVER_WOLF_BLOCK);
		blockStateModelGenerator.createTrivialCube(CustomBlocks.GOLDEN_WOLF_BLOCK);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(CustomItems.SILVER_WOLF, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.GOLDEN_WOLF, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.BALLS_UNDER_MAGNIFIER, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.STICKY_NOTES, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.LIGHT_FOOD, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.ZMYSIO_MILK_BUCKET, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.ZARZYK_GEL, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.CONCENTRATED_ZARZYK_GEL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.DARK_GLOWSTONE_DUST, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.TALISMAN_OF_SHRIEK, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.PLUS, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.RECYCLABLE_BOTTLE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.HOLY_WARS_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.YOU_MUST_BURN_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.NO_MORE_TEARS_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.ZALEWIX_BEAT_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.STELLA_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.MEGAMIKSKLASA2_MUSIC_DISC, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.ZMYSIO_ELYTRA, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(CustomItems.INFORMEJTYCY_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.INFORMEJTYCY_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.INFORMEJTYCY_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.INFORMEJTYCY_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.INFORMEJTYCY_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.REINFORCED_INFORMEJTYCY_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.REINFORCED_INFORMEJTYCY_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.REINFORCED_INFORMEJTYCY_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.ZMYSIO_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);

		itemModelGenerator.generateTrimmableItem(CustomItems.INFORMEJTYCY_HELMET, ItemModelGenerators.TRIM_PREFIX_HELMET, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.INFORMEJTYCY_CHESTPLATE, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.INFORMEJTYCY_LEGGINGS, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.INFORMEJTYCY_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.REINFORCED_INFORMEJTYCY_HELMET, ItemModelGenerators.TRIM_PREFIX_HELMET, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.REINFORCED_INFORMEJTYCY_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of());
		itemModelGenerator.generateTrimmableItem(CustomItems.PRESIDENT_HELMET, ItemModelGenerators.TRIM_PREFIX_HELMET, false, Map.of());
	}
}
