package daw.ka.informejtycy.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record TheoryForgeRecipeInput(ItemStack input1, ItemStack input2) implements RecipeInput {
	@Override
	public ItemStack getItem(int slot) {
		return slot == 0 ? input1 : input2;
	}

	@Override
	public int size() {
		return 2;
	}
}
