package daw.ka.informejtycy.recipe;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.recipe.custom.BrainrotTableRecipe;
import daw.ka.informejtycy.recipe.custom.TheoryForgeRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CustomRecipes {
	public static RecipeSerializer<TheoryForgeRecipe> THEORY_FORGE_RECIPE_SERIALIZER;
	public static RecipeSerializer<BrainrotTableRecipe> BRAINROT_TABLE_RECIPE_SERIALIZER;

	public static RecipeType<TheoryForgeRecipe> THEORY_FORGE_RECIPE_TYPE;
	public static RecipeType<BrainrotTableRecipe> BRAINROT_TABLE_RECIPE_TYPE;

	public static void registerAll() {
		THEORY_FORGE_RECIPE_SERIALIZER = Registry.register(
				Registries.RECIPE_SERIALIZER, Identifier.of(Informejtycy.MOD_ID, "theory_forge"), new TheoryForgeRecipe.Serializer());
		BRAINROT_TABLE_RECIPE_SERIALIZER = Registry.register(
				Registries.RECIPE_SERIALIZER, Identifier.of(Informejtycy.MOD_ID, "brainrot_table"), new BrainrotTableRecipe.Serializer());

		THEORY_FORGE_RECIPE_TYPE = Registry.register(
				Registries.RECIPE_TYPE, Identifier.of(Informejtycy.MOD_ID, "theory_forge"), new RecipeType<>() {
					@Override
					public String toString() {
						return Informejtycy.MOD_ID + ":theory_forge";
					}
				}
		);
		BRAINROT_TABLE_RECIPE_TYPE = Registry.register(
				Registries.RECIPE_TYPE, Identifier.of(Informejtycy.MOD_ID, "brainrot_table"), new RecipeType<>() {
					@Override
					public String toString() {
						return Informejtycy.MOD_ID + ":brainrot_table";
					}
				}
		);
	}
}
