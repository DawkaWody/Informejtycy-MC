package daw.ka.informejtycy.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.recipe.CustomRecipes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.entity.decoration.painting.PaintingVariants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.level.Level;

public record BrainrotTableRecipe(Ingredient input, ItemStackTemplate output) implements Recipe<BrainrotTableRecipeInput> {
	@Override
	public boolean matches(BrainrotTableRecipeInput input, Level world) {
		if (world.isClientSide()) return false;
		return this.input.test(input.getItem(0));
	}

	@Override
	public ItemStack assemble(BrainrotTableRecipeInput input) {
		return output.create();
	}

	@Override
	public RecipeSerializer<? extends Recipe<BrainrotTableRecipeInput>> getSerializer() {
		return CustomRecipes.BRAINROT_TABLE_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<? extends Recipe<BrainrotTableRecipeInput>> getType() {
		return CustomRecipes.BRAINROT_TABLE_RECIPE_TYPE;
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
		return PlacementInfo.create(input);
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return CustomRecipes.RECIPE_BOOK_CATEGORY;
	}

	public static final MapCodec<BrainrotTableRecipe> CODEC = RecordCodecBuilder.mapCodec(
			inst -> inst.group(
					Ingredient.CODEC.fieldOf("ingredient").forGetter(BrainrotTableRecipe::input),
					ItemStackTemplate.CODEC.fieldOf("result").forGetter(BrainrotTableRecipe::output)
			).apply(inst, BrainrotTableRecipe::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, BrainrotTableRecipe> STREAM_CODEC = StreamCodec.composite(
			Ingredient.CONTENTS_STREAM_CODEC, BrainrotTableRecipe::input,
			ItemStackTemplate.STREAM_CODEC, BrainrotTableRecipe::output,
			BrainrotTableRecipe::new
	);
}
