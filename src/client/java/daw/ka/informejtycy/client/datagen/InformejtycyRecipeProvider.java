package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.util.RecipeHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class InformejtycyRecipeProvider extends FabricRecipeProvider {
	public InformejtycyRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected @NonNull RecipeGenerator getRecipeGenerator(RegistryWrapper.@NonNull WrapperLookup wrapperLookup, @NonNull RecipeExporter exporter) {
		return new RecipeGenerator(wrapperLookup, exporter) {
			@Override
			public void generate() {
				createShaped(RecipeCategory.MISC, CustomItems.PITCH_CONTEST_TROPHY, 1)
						.pattern("BGB")
						.pattern("GBG")
						.pattern("BGB")
						.input('B', CustomItems.BALLS_UNDER_MAGNIFIER)
						.input('G', Items.GOLD_BLOCK)
						.criterion(hasItem(CustomItems.BALLS_UNDER_MAGNIFIER), conditionsFromItem(CustomItems.BALLS_UNDER_MAGNIFIER))
						.offerTo(exporter);

				createShaped(RecipeCategory.BUILDING_BLOCKS, CustomBlocks.DARK_GLOWSTONE, 4)
						.pattern("SGS")
						.pattern("GNG")
						.pattern("SGS")
						.input('S', Items.SCULK)
						.input('G', Items.GLOWSTONE)
						.input('N', Items.NETHERITE_INGOT)
						.criterion(hasItem(Items.GLOWSTONE), conditionsFromItem(Items.GLOWSTONE))
						.offerTo(exporter, "dark_glowstone");

				createShaped(RecipeCategory.BUILDING_BLOCKS, CustomBlocks.DARK_GLOWSTONE, 1)
						.pattern("##")
						.pattern("##")
						.input('#', CustomItems.DARK_GLOWSTONE_DUST)
						.criterion(hasItem(CustomItems.DARK_GLOWSTONE_DUST), conditionsFromItem(CustomItems.DARK_GLOWSTONE_DUST))
						.offerTo(exporter, "dark_glowstone_from_dust");

				createShaped(RecipeCategory.MISC, CustomBlocks.THEORY_FORGE_BLOCK, 1)
						.pattern("GCG")
						.pattern("CSC")
						.pattern("BBB")
						.input('G', CustomBlocks.DARK_GLOWSTONE)
						.input('C', Items.MAGENTA_CONCRETE)
						.input('S', Items.BLAST_FURNACE)
						.input('B', Items.BLACKSTONE)
						.criterion(hasItem(CustomBlocks.DARK_GLOWSTONE), conditionsFromItem(CustomBlocks.DARK_GLOWSTONE))
						.offerTo(exporter);

				createShaped(RecipeCategory.MISC, CustomBlocks.BRAINROT_TABLE_BLOCK, 1)
						.pattern("GG")
						.pattern("PP")
						.pattern("PP")
						.input('G', CustomBlocks.DARK_GLOWSTONE)
						.input('P', ItemTags.PLANKS)
						.criterion(hasItem(CustomBlocks.DARK_GLOWSTONE), conditionsFromItem(CustomBlocks.DARK_GLOWSTONE))
						.offerTo(exporter);

				createShaped(RecipeCategory.MISC, CustomItems.NO_MORE_TEARS_MUSIC_DISC, 1)
						.pattern(" G ")
						.pattern("GDE")
						.pattern(" E ")
						.input('G', CustomItems.DARK_GLOWSTONE_DUST)
						.input('D', Items.MUSIC_DISC_TEARS)
						.input('E', Items.ECHO_SHARD)
						.criterion(hasItem(Items.MUSIC_DISC_TEARS), conditionsFromItem(Items.MUSIC_DISC_TEARS))
						.offerTo(exporter);

				createShaped(RecipeCategory.TOOLS, CustomItems.TALISMAN_OF_SHRIEK, 1)
						.pattern(" G ")
						.pattern("GAG")
						.pattern(" S ")
						.input('G', CustomBlocks.DARK_GLOWSTONE)
						.input('A', Items.AMETHYST_SHARD)
						.input('S', Items.SCULK_SHRIEKER)
						.criterion(hasItem(CustomBlocks.DARK_GLOWSTONE), conditionsFromItem(CustomBlocks.DARK_GLOWSTONE))
						.offerTo(exporter);

				createShaped(RecipeCategory.MISC, CustomBlocks.SILVER_WOLF_BLOCK, 1)
						.pattern("SSS")
						.pattern("SSS")
						.pattern("SSS")
						.input('S', CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter, "silver_wolf_block");

				createShaped(RecipeCategory.MISC, CustomBlocks.GOLDEN_WOLF_BLOCK, 1)
						.pattern("GGG")
						.pattern("GGG")
						.pattern("GGG")
						.input('G', CustomItems.GOLDEN_WOLF)
						.criterion(hasItem(CustomItems.GOLDEN_WOLF), conditionsFromItem(CustomItems.GOLDEN_WOLF))
						.offerTo(exporter, "golden_wolf_block");

                createShaped(RecipeCategory.BUILDING_BLOCKS, CustomBlocks.GLINIANKA_BLOCK, 1)
                        .pattern("CS")
                        .pattern("SC")
                        .input('C', Items.CLAY)
                        .input('S', CustomBlocks.SILVER_WOLF_BLOCK)
                        .criterion(hasItem(CustomBlocks.SILVER_WOLF_BLOCK), conditionsFromItem(CustomBlocks.SILVER_WOLF_BLOCK))
                        .offerTo(exporter, "glinianka_block");


                createShaped(RecipeCategory.MISC, CustomBlocks.TRASH_CAN, 1)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("DDD")
                        .input('I', Items.IRON_INGOT)
                        .input('D', Items.COARSE_DIRT)
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, "trash_can");

                createShaped(RecipeCategory.COMBAT, CustomItems.ZMYSIO_SWORD, 1)
                        .pattern(" P ")
                        .pattern("MSM")
                        .pattern(" T ")
                        .input('T', Items.GHAST_TEAR)
                        .input('M', CustomItems.ZMYSIO_MILK_BUCKET)
                        .input('S', CustomItems.REINFORCED_INFORMEJTYCY_SWORD)
                        .input('P', CustomItems.PLUS)
                        .criterion(hasItem(CustomItems.PLUS), conditionsFromItem(CustomItems.PLUS))
                        .offerTo(exporter, "zmysio_sword");

                createShaped(RecipeCategory.TRANSPORTATION, CustomItems.ZMYSIO_ELYTRA, 1)
                        .pattern("S S")
                        .pattern("PEP")
                        .pattern(" P ")
                        .input('S', Items.ARMADILLO_SCUTE)
                        .input('E', Items.ELYTRA)
                        .input('P', CustomItems.PLUS)
                        .criterion(hasItem(Items.ELYTRA), conditionsFromItem(Items.ELYTRA))
                        .offerTo(exporter, "zmysio_elytra");

				createShaped(RecipeCategory.COMBAT, CustomItems.PRESIDENT_HELMET, 1)
						.pattern("GCG")
						.pattern("GHG")
						.input('G', CustomItems.CONCENTRATED_ZARZYK_GEL)
						.input('C', Items.COOKIE)
						.input('H', CustomItems.REINFORCED_INFORMEJTYCY_HELMET)
						.criterion(hasItem(CustomItems.CONCENTRATED_ZARZYK_GEL), conditionsFromItem(CustomItems.CONCENTRATED_ZARZYK_GEL))
						.offerTo(exporter, "president_helmet");

				createShapeless(RecipeCategory.MISC, CustomItems.SILVER_WOLF, 9)
						.input(CustomBlocks.SILVER_WOLF_BLOCK)
						.criterion(hasItem(CustomBlocks.SILVER_WOLF_BLOCK), conditionsFromItem(CustomBlocks.SILVER_WOLF_BLOCK))
						.offerTo(exporter, "silver_wolf_from_block");

				createShapeless(RecipeCategory.MISC, CustomItems.GOLDEN_WOLF, 9)
						.input(CustomBlocks.GOLDEN_WOLF_BLOCK)
						.criterion(hasItem(CustomBlocks.GOLDEN_WOLF_BLOCK), conditionsFromItem(CustomBlocks.GOLDEN_WOLF_BLOCK))
						.offerTo(exporter, "golden_wolf_from_block");

				RecipeHelper.createHelmetRecipe(createShaped(RecipeCategory.COMBAT, CustomItems.INFORMEJTYCY_HELMET, 1),
						CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);

				RecipeHelper.createChestplateRecipe(createShaped(RecipeCategory.COMBAT, CustomItems.INFORMEJTYCY_CHESTPLATE, 1),
						CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);

				RecipeHelper.createLeggingsRecipe(createShaped(RecipeCategory.COMBAT, CustomItems.INFORMEJTYCY_LEGGINGS, 1),
						CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);

				RecipeHelper.createBootsRecipe(createShaped(RecipeCategory.COMBAT, CustomItems.INFORMEJTYCY_BOOTS, 1),
						CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);

				RecipeHelper.createSwordRecipe(createShaped(RecipeCategory.TOOLS, CustomItems.INFORMEJTYCY_SWORD, 1),
						CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);
				RecipeHelper.createPickaxeRecipe(createShaped(RecipeCategory.TOOLS, CustomItems.INFORMEJTYCY_PICKAXE, 1),
								CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);
				RecipeHelper.createShovelRecipe(createShaped(RecipeCategory.TOOLS, CustomItems.INFORMEJTYCY_SHOVEL, 1),
								CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);
				RecipeHelper.createAxeRecipe(createShaped(RecipeCategory.TOOLS, CustomItems.INFORMEJTYCY_AXE, 1),
								CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);
				RecipeHelper.createHoeRecipe(createShaped(RecipeCategory.TOOLS, CustomItems.INFORMEJTYCY_HOE, 1),
								CustomItems.SILVER_WOLF)
						.criterion(hasItem(CustomItems.SILVER_WOLF), conditionsFromItem(CustomItems.SILVER_WOLF))
						.offerTo(exporter);

				SmithingTransformRecipeJsonBuilder.create(
						Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
						Ingredient.ofItems(CustomItems.INFORMEJTYCY_HELMET),
						Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
						RecipeCategory.COMBAT,
						CustomItems.REINFORCED_INFORMEJTYCY_HELMET
				)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_HELMET), conditionsFromItem(CustomItems.INFORMEJTYCY_HELMET))
						.offerTo(exporter, "reinforced_informejtycy_helmet");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_CHESTPLATE),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_CHESTPLATE), conditionsFromItem(CustomItems.INFORMEJTYCY_CHESTPLATE))
						.offerTo(exporter, "reinforced_informejtycy_chestplate");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_LEGGINGS),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_LEGGINGS), conditionsFromItem(CustomItems.INFORMEJTYCY_LEGGINGS))
						.offerTo(exporter, "reinforced_informejtycy_leggings");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_BOOTS),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_BOOTS
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_BOOTS), conditionsFromItem(CustomItems.INFORMEJTYCY_BOOTS))
						.offerTo(exporter, "reinforced_informejtycy_boots");

				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_SWORD),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_SWORD
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_SWORD), conditionsFromItem(CustomItems.INFORMEJTYCY_SWORD))
						.offerTo(exporter, "reinforced_informejtycy_sword");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_PICKAXE),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_PICKAXE), conditionsFromItem(CustomItems.INFORMEJTYCY_PICKAXE))
						.offerTo(exporter, "reinforced_informejtycy_pickaxe");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_SHOVEL),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_SHOVEL), conditionsFromItem(CustomItems.INFORMEJTYCY_SHOVEL))
						.offerTo(exporter, "reinforced_informejtycy_shovel");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_AXE),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_AXE
						)
						.criterion("has_informejtycy_axe", conditionsFromItem(CustomItems.INFORMEJTYCY_AXE))
						.offerTo(exporter, "reinforced_informejtycy_axe");
				SmithingTransformRecipeJsonBuilder.create(
								Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.ofItems(CustomItems.INFORMEJTYCY_HOE),
								Ingredient.ofItems(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_HOE
						)
						.criterion(hasItem(CustomItems.INFORMEJTYCY_HOE), conditionsFromItem(CustomItems.INFORMEJTYCY_HOE))
						.offerTo(exporter, "reinforced_informejtycy_hoe");
			}
		};
	}

	@Override
	public String getName() {
		return "Recipe (zmysio hot)";
	}
}
