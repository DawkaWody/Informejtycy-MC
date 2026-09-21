package daw.ka.informejtycy.util;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class RecipeHelper {
	public static ShapedRecipeBuilder createHelmetRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("TTT")
				.pattern("T T")
				.define('T', material);
	}

	public static ShapedRecipeBuilder createChestplateRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("T T")
				.pattern("TTT")
				.pattern("TTT")
				.define('T', material);
	}

	public static ShapedRecipeBuilder createLeggingsRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("TTT")
				.pattern("T T")
				.pattern("T T")
				.define('T', material);
	}

	public static ShapedRecipeBuilder createBootsRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("T T")
				.pattern("T T")
				.define('T', material);
	}

	public static ShapedRecipeBuilder createSwordRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern(" T ")
				.pattern(" T ")
				.pattern(" S ")
				.define('T', material)
				.define('S', Items.STICK);
	}

	public static ShapedRecipeBuilder createPickaxeRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("TTT")
				.pattern(" S ")
				.pattern(" S ")
				.define('T', material)
				.define('S', Items.STICK);
	}

	public static ShapedRecipeBuilder createShovelRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern(" T ")
				.pattern(" S ")
				.pattern(" S ")
				.define('T', material)
				.define('S', Items.STICK);
	}

	public static ShapedRecipeBuilder createAxeRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("TT ")
				.pattern("TS ")
				.pattern(" S ")
				.define('T', material)
				.define('S', Items.STICK);
	}

	public static ShapedRecipeBuilder createHoeRecipe(ShapedRecipeBuilder builder, Item material) {
		return builder
				.pattern("TT ")
				.pattern(" S ")
				.pattern(" S ")
				.define('T', material)
				.define('S', Items.STICK);
	}
}
