package daw.ka.informejtycy.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.recipe.CustomRecipes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.decoration.painting.PaintingVariants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.*;
import net.minecraft.world.World;

public record BrainrotTableRecipe(Ingredient input, ItemStack output) implements Recipe<BrainrotTableRecipeInput> {
	@Override
	public boolean matches(BrainrotTableRecipeInput input, World world) {
		if (world.isClient()) return false;
		return this.input.test(input.getStackInSlot(0));
	}

	@Override
	public ItemStack craft(BrainrotTableRecipeInput input, RegistryWrapper.WrapperLookup registries) {
		return output.copy();
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
	public IngredientPlacement getIngredientPlacement() {
		return IngredientPlacement.forSingleSlot(input);
	}

	@Override
	public RecipeBookCategory getRecipeBookCategory() {
		return Registry.register(
				Registries.RECIPE_BOOK_CATEGORY,
				InformejtycyRegistry.id("recipes"),
				new RecipeBookCategory()
		);
	}

	public static class Serializer implements RecipeSerializer<BrainrotTableRecipe> {
		public static final MapCodec<BrainrotTableRecipe> CODEC = RecordCodecBuilder.mapCodec(
				inst -> inst.group(
						Ingredient.CODEC.fieldOf("ingredient").forGetter(BrainrotTableRecipe::input),
						ItemStack.CODEC.fieldOf("result").forGetter(BrainrotTableRecipe::output)
				).apply(inst, BrainrotTableRecipe::new)
		);

		public static final PacketCodec<RegistryByteBuf, BrainrotTableRecipe> STREAM_CODEC = PacketCodec.tuple(
				Ingredient.PACKET_CODEC, BrainrotTableRecipe::input,
				ItemStack.PACKET_CODEC, BrainrotTableRecipe::output,
				BrainrotTableRecipe::new
		);

		@Override
		public MapCodec<BrainrotTableRecipe> codec() {
			return CODEC;
		}

		@Override
		public PacketCodec<RegistryByteBuf, BrainrotTableRecipe> packetCodec() {
			return STREAM_CODEC;
		}
	}
}
