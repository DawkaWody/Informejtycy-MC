package daw.ka.informejtycy.recipe;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.recipe.custom.BrainrotTableRecipe;
import daw.ka.informejtycy.recipe.custom.TheoryForgeRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

public class CustomRecipes {
	public static RecipeSerializer<TheoryForgeRecipe> THEORY_FORGE_RECIPE_SERIALIZER;
	public static RecipeSerializer<BrainrotTableRecipe> BRAINROT_TABLE_RECIPE_SERIALIZER;

	public static RecipeType<TheoryForgeRecipe> THEORY_FORGE_RECIPE_TYPE;
	public static RecipeType<BrainrotTableRecipe> BRAINROT_TABLE_RECIPE_TYPE;

	public static RecipeBookCategory RECIPE_BOOK_CATEGORY;

	public static void registerAll() {
		THEORY_FORGE_RECIPE_SERIALIZER = Registry.register(
				BuiltInRegistries.RECIPE_SERIALIZER, InformejtycyRegistry.id("theory_forge"), new RecipeSerializer<>(TheoryForgeRecipe.CODEC, TheoryForgeRecipe.STREAM_CODEC));
		BRAINROT_TABLE_RECIPE_SERIALIZER = Registry.register(
				BuiltInRegistries.RECIPE_SERIALIZER, InformejtycyRegistry.id("brainrot_table"), new RecipeSerializer<>(BrainrotTableRecipe.CODEC, BrainrotTableRecipe.STREAM_CODEC));

		THEORY_FORGE_RECIPE_TYPE = Registry.register(
				BuiltInRegistries.RECIPE_TYPE, InformejtycyRegistry.id("theory_forge"), new RecipeType<>() {
					@Override
					public String toString() {
						return Informejtycy.MOD_ID + ":theory_forge";
					}
				}
		);
		BRAINROT_TABLE_RECIPE_TYPE = Registry.register(
				BuiltInRegistries.RECIPE_TYPE, InformejtycyRegistry.id("brainrot_table"), new RecipeType<>() {
					@Override
					public String toString() {
						return Informejtycy.MOD_ID + ":brainrot_table";
					}
				}
		);

		RECIPE_BOOK_CATEGORY = Registry.register(
				BuiltInRegistries.RECIPE_BOOK_CATEGORY, InformejtycyRegistry.id("recipes"), new RecipeBookCategory());
	}
}
