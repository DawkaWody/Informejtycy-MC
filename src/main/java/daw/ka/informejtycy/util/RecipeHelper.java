package daw.ka.informejtycy.util;

import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class RecipeHelper {
	public static ShapedRecipeJsonBuilder createHelmetRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("TTT")
				.pattern("T T")
				.input('T', material);
	}

	public static ShapedRecipeJsonBuilder createChestplateRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("T T")
				.pattern("TTT")
				.pattern("TTT")
				.input('T', material);
	}

	public static ShapedRecipeJsonBuilder createLeggingsRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("TTT")
				.pattern("T T")
				.pattern("T T")
				.input('T', material);
	}

	public static ShapedRecipeJsonBuilder createBootsRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("T T")
				.pattern("T T")
				.input('T', material);
	}

	public static ShapedRecipeJsonBuilder createSwordRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern(" T ")
				.pattern(" T ")
				.pattern(" S ")
				.input('T', material)
				.input('S', Items.STICK);
	}

	public static ShapedRecipeJsonBuilder createPickaxeRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("TTT")
				.pattern(" S ")
				.pattern(" S ")
				.input('T', material)
				.input('S', Items.STICK);
	}

	public static ShapedRecipeJsonBuilder createShovelRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern(" T ")
				.pattern(" S ")
				.pattern(" S ")
				.input('T', material)
				.input('S', Items.STICK);
	}

	public static ShapedRecipeJsonBuilder createAxeRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("TT ")
				.pattern("TS ")
				.pattern(" S ")
				.input('T', material)
				.input('S', Items.STICK);
	}

	public static ShapedRecipeJsonBuilder createHoeRecipe(ShapedRecipeJsonBuilder builder, Item material) {
		return builder
				.pattern("TT ")
				.pattern(" S ")
				.pattern(" S ")
				.input('T', material)
				.input('S', Items.STICK);
	}
}
