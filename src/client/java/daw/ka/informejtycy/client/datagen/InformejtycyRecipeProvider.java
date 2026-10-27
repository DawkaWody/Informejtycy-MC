package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.potion.CustomPotions;
import daw.ka.informejtycy.tag.CustomTags;
import daw.ka.informejtycy.util.RecipeHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBrewingProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class InformejtycyRecipeProvider extends FabricRecipeProvider {
	public InformejtycyRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider wrapperLookup,
														  @NonNull BootstrapContext<Recipe<?>> recipeOutput,
														  @NonNull BootstrapContext<Advancement> advancementOutput) {
		return new RecipeProvider(recipeOutput, advancementOutput) {
			@Override
			public void buildRecipes() {
				shaped(RecipeCategory.MISC, CustomItems.PITCH_CONTEST_TROPHY, 1)
						.pattern("BGB")
						.pattern("GBG")
						.pattern("BGB")
						.define('B', CustomItems.BALLS_UNDER_MAGNIFIER)
						.define('G', Items.GOLD_BLOCK)
						.unlockedBy(getHasName(CustomItems.BALLS_UNDER_MAGNIFIER), has(CustomItems.BALLS_UNDER_MAGNIFIER))
						.save(this.output);

				shaped(RecipeCategory.BUILDING_BLOCKS, CustomBlocks.DARK_GLOWSTONE, 4)
						.pattern("SGS")
						.pattern("GNG")
						.pattern("SGS")
						.define('S', Items.SCULK)
						.define('G', Items.GLOWSTONE)
						.define('N', Items.NETHERITE_INGOT)
						.unlockedBy(getHasName(Items.GLOWSTONE), has(Items.GLOWSTONE))
						.save(this.output, "dark_glowstone");

				shaped(RecipeCategory.BUILDING_BLOCKS, CustomBlocks.DARK_GLOWSTONE, 1)
						.pattern("##")
						.pattern("##")
						.define('#', CustomItems.DARK_GLOWSTONE_DUST)
						.unlockedBy(getHasName(CustomItems.DARK_GLOWSTONE_DUST), has(CustomItems.DARK_GLOWSTONE_DUST))
						.save(this.output, "dark_glowstone_from_dust");

				shaped(RecipeCategory.MISC, CustomBlocks.THEORY_FORGE_BLOCK, 1)
						.pattern("GCG")
						.pattern("CSC")
						.pattern("BBB")
						.define('G', CustomBlocks.DARK_GLOWSTONE)
						.define('C', Items.CONCRETE.pick(DyeColor.MAGENTA))
						.define('S', Items.BLAST_FURNACE)
						.define('B', Items.BLACKSTONE)
						.unlockedBy(getHasName(CustomBlocks.DARK_GLOWSTONE), has(CustomBlocks.DARK_GLOWSTONE))
						.save(this.output);

				shaped(RecipeCategory.MISC, CustomBlocks.BRAINROT_TABLE_BLOCK, 1)
						.pattern("GG")
						.pattern("PP")
						.pattern("PP")
						.define('G', CustomBlocks.DARK_GLOWSTONE)
						.define('P', ItemTags.PLANKS)
						.unlockedBy(getHasName(CustomBlocks.DARK_GLOWSTONE), has(CustomBlocks.DARK_GLOWSTONE))
						.save(this.output);

				shaped(RecipeCategory.MISC, CustomItems.NO_MORE_TEARS_MUSIC_DISC, 1)
						.pattern(" G ")
						.pattern("GDE")
						.pattern(" E ")
						.define('G', CustomItems.DARK_GLOWSTONE_DUST)
						.define('D', Items.MUSIC_DISC_TEARS)
						.define('E', Items.ECHO_SHARD)
						.unlockedBy(getHasName(Items.MUSIC_DISC_TEARS), has(Items.MUSIC_DISC_TEARS))
						.save(this.output);

				shaped(RecipeCategory.TOOLS, CustomItems.TALISMAN_OF_SHRIEK, 1)
						.pattern(" G ")
						.pattern("GAG")
						.pattern(" S ")
						.define('G', CustomBlocks.DARK_GLOWSTONE)
						.define('A', Items.AMETHYST_SHARD)
						.define('S', Items.SCULK_SHRIEKER)
						.unlockedBy(getHasName(CustomBlocks.DARK_GLOWSTONE), has(CustomBlocks.DARK_GLOWSTONE))
						.save(this.output);

				shaped(RecipeCategory.MISC, CustomBlocks.SILVER_WOLF_BLOCK, 1)
						.pattern("SSS")
						.pattern("SSS")
						.pattern("SSS")
						.define('S', CustomItems.SILVER_WOLF)
						.unlockedBy(getHasName(CustomItems.SILVER_WOLF), has(CustomItems.SILVER_WOLF))
						.save(this.output, "silver_wolf_block");

				shaped(RecipeCategory.MISC, CustomBlocks.GOLDEN_WOLF_BLOCK, 1)
						.pattern("GGG")
						.pattern("GGG")
						.pattern("GGG")
						.define('G', CustomItems.GOLDEN_WOLF)
						.unlockedBy(getHasName(CustomItems.GOLDEN_WOLF), has(CustomItems.GOLDEN_WOLF))
						.save(this.output, "golden_wolf_block");

                shaped(RecipeCategory.BUILDING_BLOCKS, CustomBlocks.GLINIANKA_BLOCK, 1)
                        .pattern("CS")
                        .pattern("SC")
                        .define('C', Items.CLAY)
                        .define('S', CustomBlocks.SILVER_WOLF_BLOCK)
                        .unlockedBy(getHasName(CustomBlocks.SILVER_WOLF_BLOCK), has(CustomBlocks.SILVER_WOLF_BLOCK))
                        .save(this.output, "glinianka_block");


                shaped(RecipeCategory.MISC, CustomBlocks.TRASH_CAN, 1)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("DDD")
                        .define('I', Items.IRON_INGOT)
                        .define('D', Items.COARSE_DIRT)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(this.output, "trash_can");

                shaped(RecipeCategory.COMBAT, CustomItems.ZMYSIO_SWORD, 1)
                        .pattern(" P ")
                        .pattern("MSM")
                        .pattern(" T ")
                        .define('T', Items.GHAST_TEAR)
                        .define('M', CustomItems.ZMYSIO_MILK_BUCKET)
                        .define('S', CustomItems.REINFORCED_INFORMEJTYCY_SWORD)
                        .define('P', CustomItems.PLUS)
                        .unlockedBy(getHasName(CustomItems.PLUS), has(CustomItems.PLUS))
                        .save(this.output, "zmysio_sword");

                shaped(RecipeCategory.TRANSPORTATION, CustomItems.ZMYSIO_ELYTRA, 1)
                        .pattern("S S")
                        .pattern("PEP")
                        .pattern(" P ")
                        .define('S', Items.ARMADILLO_SCUTE)
                        .define('E', Items.ELYTRA)
                        .define('P', CustomItems.PLUS)
                        .unlockedBy(getHasName(Items.ELYTRA), has(Items.ELYTRA))
                        .save(this.output, "zmysio_elytra");

				shaped(RecipeCategory.COMBAT, CustomItems.PRESIDENT_HELMET, 1)
						.pattern("GCG")
						.pattern("GHG")
						.define('G', CustomItems.CONCENTRATED_ZARZYK_GEL)
						.define('C', Items.COOKIE)
						.define('H', CustomItems.REINFORCED_INFORMEJTYCY_HELMET)
						.unlockedBy(getHasName(CustomItems.CONCENTRATED_ZARZYK_GEL), has(CustomItems.CONCENTRATED_ZARZYK_GEL))
						.save(this.output, "president_helmet");

				shapeless(RecipeCategory.MISC, CustomItems.SILVER_WOLF, 9)
						.requires(CustomBlocks.SILVER_WOLF_BLOCK)
						.unlockedBy(getHasName(CustomBlocks.SILVER_WOLF_BLOCK), has(CustomBlocks.SILVER_WOLF_BLOCK))
						.save(this.output, "silver_wolf_from_block");

				shapeless(RecipeCategory.MISC, CustomItems.GOLDEN_WOLF, 9)
						.requires(CustomBlocks.GOLDEN_WOLF_BLOCK)
						.unlockedBy(getHasName(CustomBlocks.GOLDEN_WOLF_BLOCK), has(CustomBlocks.GOLDEN_WOLF_BLOCK))
						.save(this.output, "golden_wolf_from_block");

				buildWoodSetRecipes(CustomBlocks.ISTANBUL_WOOD, CustomBlocks.ISTANBUL_LOG, CustomBlocks.STRIPPED_ISTANBUL_WOOD,
						CustomBlocks.STRIPPED_ISTANBUL_LOG, CustomBlocks.ISTANBUL_PLANKS, CustomBlocks.ISTANBUL_STAIRS,
						CustomBlocks.ISTANBUL_SLAB, CustomBlocks.ISTANBUL_FENCE, CustomBlocks.ISTANBUL_FENCE_GATE,
						CustomBlocks.ISTANBUL_PRESSURE_PLATE, CustomBlocks.ISTANBUL_BUTTON,
						CustomBlocks.ISTANBUL_TRAPDOOR, CustomBlocks.ISTANBUL_DOOR, CustomTags.Items.ISTANBUL_LOGS);
				buildWoodSetRecipes(CustomBlocks.ZARZYK_WOOD, CustomBlocks.ZARZYK_LOG, CustomBlocks.STRIPPED_ZARZYK_WOOD,
						CustomBlocks.STRIPPED_ZARZYK_LOG, CustomBlocks.ZARZYK_PLANKS, CustomBlocks.ZARZYK_STAIRS,
						CustomBlocks.ZARZYK_SLAB, CustomBlocks.ZARZYK_FENCE, CustomBlocks.ZARZYK_FENCE_GATE,
						CustomBlocks.ZARZYK_PRESSURE_PLATE, CustomBlocks.ZARZYK_BUTTON,
						CustomBlocks.ZARZYK_TRAPDOOR, CustomBlocks.ZARZYK_DOOR, CustomTags.Items.ZARZYK_LOGS);

				buildArmorRecipes(CustomItems.SILVER_WOLF, CustomItems.INFORMEJTYCY_HELMET,
						CustomItems.INFORMEJTYCY_CHESTPLATE, CustomItems.INFORMEJTYCY_LEGGINGS,
						CustomItems.INFORMEJTYCY_BOOTS);
				buildToolsetRecipes(CustomItems.SILVER_WOLF, CustomItems.INFORMEJTYCY_SWORD,
						CustomItems.INFORMEJTYCY_PICKAXE, CustomItems.INFORMEJTYCY_SHOVEL,
						CustomItems.INFORMEJTYCY_AXE, CustomItems.INFORMEJTYCY_HOE);

				SmithingTransformRecipeBuilder.smithing(
						Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
						Ingredient.of(CustomItems.INFORMEJTYCY_HELMET),
						Ingredient.of(CustomItems.GOLDEN_WOLF),
						RecipeCategory.COMBAT,
						CustomItems.REINFORCED_INFORMEJTYCY_HELMET
				)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_HELMET), has(CustomItems.INFORMEJTYCY_HELMET))
						.save(this.output, "reinforced_informejtycy_helmet");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_CHESTPLATE),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_CHESTPLATE
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_CHESTPLATE), has(CustomItems.INFORMEJTYCY_CHESTPLATE))
						.save(this.output, "reinforced_informejtycy_chestplate");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_LEGGINGS),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_LEGGINGS
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_LEGGINGS), has(CustomItems.INFORMEJTYCY_LEGGINGS))
						.save(this.output, "reinforced_informejtycy_leggings");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_BOOTS),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_BOOTS
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_BOOTS), has(CustomItems.INFORMEJTYCY_BOOTS))
						.save(this.output, "reinforced_informejtycy_boots");

				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_SWORD),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.COMBAT,
								CustomItems.REINFORCED_INFORMEJTYCY_SWORD
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_SWORD), has(CustomItems.INFORMEJTYCY_SWORD))
						.save(this.output, "reinforced_informejtycy_sword");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_PICKAXE),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_PICKAXE), has(CustomItems.INFORMEJTYCY_PICKAXE))
						.save(this.output, "reinforced_informejtycy_pickaxe");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_SHOVEL),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_SHOVEL
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_SHOVEL), has(CustomItems.INFORMEJTYCY_SHOVEL))
						.save(this.output, "reinforced_informejtycy_shovel");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_AXE),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_AXE
						)
						.unlocks("has_informejtycy_axe", has(CustomItems.INFORMEJTYCY_AXE))
						.save(this.output, "reinforced_informejtycy_axe");
				SmithingTransformRecipeBuilder.smithing(
								Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
								Ingredient.of(CustomItems.INFORMEJTYCY_HOE),
								Ingredient.of(CustomItems.GOLDEN_WOLF),
								RecipeCategory.TOOLS,
								CustomItems.REINFORCED_INFORMEJTYCY_HOE
						)
						.unlocks(getHasName(CustomItems.INFORMEJTYCY_HOE), has(CustomItems.INFORMEJTYCY_HOE))
						.save(this.output, "reinforced_informejtycy_hoe");

				new FabricBrewingProvider(this.output) {
					@Override
					protected void buildMixes() {
						buildStartMix(CustomBlocks.SILVER_WOLF_ORE.asItem(), CustomPotions.AURA_POTION);
					}

					@Override
					protected void buildTransformations() {
						save(BrewingRecipeBuilder.brewingContainerTransform(
								Items.POTION, CustomPotions.AURA_POTION, Items.GUNPOWDER, Items.SPLASH_POTION));
						save(BrewingRecipeBuilder.brewingContainerTransform(
								Items.SPLASH_POTION, CustomPotions.AURA_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION));
					}
				}.buildRecipes();
			}

			private void buildArmorRecipes(Item material, Item helmet, Item chestplate, Item leggings, Item boots) {
				RecipeHelper.createHelmetRecipe(shaped(RecipeCategory.COMBAT, helmet, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createChestplateRecipe(shaped(RecipeCategory.COMBAT, chestplate, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createLeggingsRecipe(shaped(RecipeCategory.COMBAT, leggings, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createBootsRecipe(shaped(RecipeCategory.COMBAT, boots, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
			}

			private void buildToolsetRecipes(Item material, Item sword, Item pickaxe, Item shovel, Item axe, Item hoe) {
				RecipeHelper.createSwordRecipe(shaped(RecipeCategory.COMBAT, sword, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createPickaxeRecipe(shaped(RecipeCategory.TOOLS, pickaxe, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createShovelRecipe(shaped(RecipeCategory.TOOLS, shovel, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createAxeRecipe(shaped(RecipeCategory.TOOLS, axe, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
				RecipeHelper.createHoeRecipe(shaped(RecipeCategory.TOOLS, hoe, 1), material)
						.unlockedBy(getHasName(material), has(material))
						.save(this.output);
			}

			private void buildWoodSetRecipes(Block wood, Block log, Block strippedWood, Block strippedLog, Block planks,
			                                 Block stairs, Block slab, Block fence, Block fenceGate, Block pressurePlate,
			                                 Block button, Block trapdoor, Block door, TagKey<Item> logTag) {
				woodFromLogs(wood, log);
				woodFromLogs(strippedWood, strippedLog);
				planksFromLogs(planks, logTag, 4);
				stairBuilder(stairs, Ingredient.of(planks))
						.unlockedBy(getHasName(planks), has(planks))
						.save(this.output);
				slab(RecipeCategory.BUILDING_BLOCKS, slab, planks);
				fenceBuilder(fence, Ingredient.of(planks))
						.unlockedBy(getHasName(planks), has(planks))
						.save(this.output);
				fenceGateBuilder(fenceGate, Ingredient.of(planks))
						.unlockedBy(getHasName(planks), has(planks))
						.save(this.output);
				pressurePlate(pressurePlate, planks);
				buttonBuilder(button, Ingredient.of(planks))
						.unlockedBy(getHasName(planks), has(planks))
						.save(this.output);
				trapdoorBuilder(trapdoor, Ingredient.of(planks))
						.unlockedBy(getHasName(planks), has(planks))
						.save(this.output);
				doorBuilder(door, Ingredient.of(planks))
						.unlockedBy(getHasName(planks), has(planks))
						.save(this.output);
			}
		};
	}

	@Override
	public @NonNull String getName() {
		return "Recipe (zmysio hot)";
	}
}
