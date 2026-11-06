package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Map;
import java.util.Optional;

public class InformejtycyModelProvider extends FabricModelProvider {
	private static final TexturedModel.Provider CUBE_PER_FACE = TexturedModel.createDefault(block -> new TextureMapping()
			.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_north"))
			.put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_north"))
			.put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_east"))
			.put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_south"))
			.put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_west"))
			.put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_top"))
			.put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_bottom")), ModelTemplates.CUBE);
	private static final TexturedModel.Provider BIDON_CAP = TexturedModel.createDefault(block -> new TextureMapping()
			.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
			.put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
			.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(CustomBlocks.BIDON_BLOCK, "_top")),
			new ModelTemplate(Optional.of(InformejtycyRegistry.id("block/template_bidon_cap")), Optional.empty(),
					TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM));

	public InformejtycyModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createTrivialCube(CustomBlocks.SILVER_WOLF_ORE);
		blockStateModelGenerator.createTrivialCube(CustomBlocks.DARK_GLOWSTONE);
		blockStateModelGenerator.createTrivialCube(CustomBlocks.SILVER_WOLF_BLOCK);
		blockStateModelGenerator.createTrivialCube(CustomBlocks.GOLDEN_WOLF_BLOCK);
		blockStateModelGenerator.createTrivialBlock(CustomBlocks.BIDON_BLOCK, TexturedModel.COLUMN);
		blockStateModelGenerator.createTrivialBlock(CustomBlocks.BIDON_CAP_BLOCK, BIDON_CAP);

		blockStateModelGenerator.family(CustomBlocks.ISTANBUL_PLANKS)
				.stairs(CustomBlocks.ISTANBUL_STAIRS)
				.slab(CustomBlocks.ISTANBUL_SLAB)
				.fence(CustomBlocks.ISTANBUL_FENCE)
				.fenceGate(CustomBlocks.ISTANBUL_FENCE_GATE)
				.pressurePlate(CustomBlocks.ISTANBUL_PRESSURE_PLATE)
				.button(CustomBlocks.ISTANBUL_BUTTON);
		blockStateModelGenerator.family(CustomBlocks.ZARZYK_PLANKS)
				.stairs(CustomBlocks.ZARZYK_STAIRS)
				.slab(CustomBlocks.ZARZYK_SLAB)
				.fence(CustomBlocks.ZARZYK_FENCE)
				.fenceGate(CustomBlocks.ZARZYK_FENCE_GATE)
				.pressurePlate(CustomBlocks.ZARZYK_PRESSURE_PLATE)
				.button(CustomBlocks.ZARZYK_BUTTON);

		blockStateModelGenerator.createOrientableTrapdoor(CustomBlocks.ISTANBUL_TRAPDOOR);
		blockStateModelGenerator.createOrientableTrapdoor(CustomBlocks.ZARZYK_TRAPDOOR);
		blockStateModelGenerator.createDoor(CustomBlocks.ISTANBUL_DOOR);
		blockStateModelGenerator.createDoor(CustomBlocks.ZARZYK_DOOR);

		blockStateModelGenerator.createTrivialBlock(CustomBlocks.ISTANBUL_LEAVES, TexturedModel.LEAVES);
		blockStateModelGenerator.createTrivialBlock(CustomBlocks.ZARZYK_LEAVES, TexturedModel.LEAVES);

		blockStateModelGenerator.woodProvider(CustomBlocks.ISTANBUL_LOG).log(CustomBlocks.ISTANBUL_LOG).wood(CustomBlocks.ISTANBUL_WOOD);
		blockStateModelGenerator.woodProvider(CustomBlocks.STRIPPED_ISTANBUL_LOG).log(CustomBlocks.STRIPPED_ISTANBUL_LOG).wood(CustomBlocks.STRIPPED_ISTANBUL_WOOD);
		blockStateModelGenerator.woodProvider(CustomBlocks.ZARZYK_LOG).log(CustomBlocks.ZARZYK_LOG).wood(CustomBlocks.ZARZYK_WOOD);
		blockStateModelGenerator.woodProvider(CustomBlocks.STRIPPED_ZARZYK_LOG).log(CustomBlocks.STRIPPED_ZARZYK_LOG).wood(CustomBlocks.STRIPPED_ZARZYK_WOOD);

		blockStateModelGenerator.createPlantWithDefaultItem(CustomBlocks.ISTANBUL_SAPLING, CustomBlocks.POTTED_ISTANBUL_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
		blockStateModelGenerator.createPlantWithDefaultItem(CustomBlocks.ZARZYK_SAPLING, CustomBlocks.POTTED_ZARZYK_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);

		blockStateModelGenerator.createFurnace(CustomBlocks.THEORY_FORGE_BLOCK, TexturedModel.ORIENTABLE_ONLY_TOP);
		blockStateModelGenerator.createHorizontallyRotatedBlock(CustomBlocks.BRAINROT_TABLE_BLOCK, CUBE_PER_FACE);
		blockStateModelGenerator.createHorizontallyRotatedBlock(CustomBlocks.GLINIANKA_BLOCK, CUBE_PER_FACE);

		blockStateModelGenerator.createNonTemplateHorizontalBlock(CustomBlocks.RECYCLER_BLOCK);
		blockStateModelGenerator.createNonTemplateHorizontalBlock(CustomBlocks.BOMBARDINO_COCODRILO);
		blockStateModelGenerator.createNonTemplateHorizontalBlock(CustomBlocks.TRALALERO_TRALALA);
		blockStateModelGenerator.createNonTemplateHorizontalBlock(CustomBlocks.TUNG_TUNG_SAHUR);
		blockStateModelGenerator.createNonTemplateHorizontalBlock(CustomBlocks.CHIMPANZINI_BANANINI);
		blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(CustomBlocks.TRASH_CAN,
						BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(CustomBlocks.TRASH_CAN)))
				.with(PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
						.select(Direction.NORTH, BlockModelGenerators.Y_ROT_90)
						.select(Direction.EAST, BlockModelGenerators.Y_ROT_180)
						.select(Direction.SOUTH, BlockModelGenerators.Y_ROT_270)
						.select(Direction.WEST, BlockModelGenerators.NOP)));
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
		itemModelGenerator.generateFlatItem(CustomItems.VAPE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(CustomItems.PLUS, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.RECYCLABLE_BOTTLE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(CustomItems.RARE_RECYCLABLE_BOTTLE, ModelTemplates.FLAT_ITEM);
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
