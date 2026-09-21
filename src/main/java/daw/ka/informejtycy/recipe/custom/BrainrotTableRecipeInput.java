package daw.ka.informejtycy.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record BrainrotTableRecipeInput(ItemStack input) implements RecipeInput {
	@Override
	public ItemStack getItem(int slot) {
		return input;
	}

	@Override
	public int size() {
		return 1;
	}
}
