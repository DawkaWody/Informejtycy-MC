package daw.ka.informejtycy.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daw.ka.informejtycy.recipe.CustomRecipes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record TheoryForgeRecipe(Ingredient input1, Ingredient input2, ItemStackTemplate output) implements Recipe<TheoryForgeRecipeInput> {
	@Override
	public boolean matches(TheoryForgeRecipeInput input, Level world) {
		if (world.isClientSide()) return false;
		return input1.test(input.getItem(0)) && input2.test(input.getItem(1));
	}

	@Override
	public ItemStack assemble(TheoryForgeRecipeInput input) {
		return output.create();
	}

	@Override
	public RecipeSerializer<? extends Recipe<TheoryForgeRecipeInput>> getSerializer() {
		return CustomRecipes.THEORY_FORGE_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<? extends Recipe<TheoryForgeRecipeInput>> getType() {
		return CustomRecipes.THEORY_FORGE_RECIPE_TYPE;
	}

	@Override
	public String group() {
		return "";
	}

	@Override
	public boolean showNotification() {
		return true;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.createFromOptionals(List.of(Optional.of(this.input1),
				Optional.of(this.input2)));
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return CustomRecipes.RECIPE_BOOK_CATEGORY;
	}

	public static final MapCodec<TheoryForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(
			inst -> inst.group(
					Ingredient.CODEC.fieldOf("ingredient1").forGetter(TheoryForgeRecipe::input1),
					Ingredient.CODEC.fieldOf("ingredient2").forGetter(TheoryForgeRecipe::input2),
					ItemStackTemplate.CODEC.fieldOf("result").forGetter(TheoryForgeRecipe::output)
			).apply(inst, TheoryForgeRecipe::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, TheoryForgeRecipe> STREAM_CODEC = StreamCodec.composite(
			Ingredient.CONTENTS_STREAM_CODEC, TheoryForgeRecipe::input1,
			Ingredient.CONTENTS_STREAM_CODEC, TheoryForgeRecipe::input2,
			ItemStackTemplate.STREAM_CODEC, TheoryForgeRecipe::output,
			TheoryForgeRecipe::new
	);
}
