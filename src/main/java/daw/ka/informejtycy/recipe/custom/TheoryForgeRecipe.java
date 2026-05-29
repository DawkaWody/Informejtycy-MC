package daw.ka.informejtycy.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.recipe.CustomRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public record TheoryForgeRecipe(Ingredient input1, Ingredient input2, ItemStack output) implements Recipe<TheoryForgeRecipeInput> {
	@Override
	public boolean matches(TheoryForgeRecipeInput input, World world) {
		if (world.isClient()) return false;
		return input1.test(input.getStackInSlot(0)) && input2.test(input.getStackInSlot(1));
	}

	@Override
	public ItemStack craft(TheoryForgeRecipeInput input, RegistryWrapper.WrapperLookup registries) {
		return output.copy();
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
	public IngredientPlacement getIngredientPlacement() {
		return IngredientPlacement.forMultipleSlots(List.of(Optional.of(this.input1),
				Optional.of(this.input2)));
	}

	@Override
	public RecipeBookCategory getRecipeBookCategory() {
		return Registry.register(
				Registries.RECIPE_BOOK_CATEGORY,
				InformejtycyRegistry.id("recipes"),
				new RecipeBookCategory()
		);
	}

	public static class Serializer implements RecipeSerializer<TheoryForgeRecipe> {
		public static final MapCodec<TheoryForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(
				inst -> inst.group(
						Ingredient.CODEC.fieldOf("ingredient1").forGetter(TheoryForgeRecipe::input1),
						Ingredient.CODEC.fieldOf("ingredient2").forGetter(TheoryForgeRecipe::input2),
						ItemStack.CODEC.fieldOf("result").forGetter(TheoryForgeRecipe::output)
				).apply(inst, TheoryForgeRecipe::new)
		);

		public static final PacketCodec<RegistryByteBuf, TheoryForgeRecipe> STREAM_CODEC = PacketCodec.tuple(
				Ingredient.PACKET_CODEC, TheoryForgeRecipe::input1,
				Ingredient.PACKET_CODEC, TheoryForgeRecipe::input2,
				ItemStack.PACKET_CODEC, TheoryForgeRecipe::output,
				TheoryForgeRecipe::new
		);

		@Override
		public MapCodec<TheoryForgeRecipe> codec() {
			return CODEC;
		}

		@Override
		public PacketCodec<RegistryByteBuf, TheoryForgeRecipe> packetCodec() {
			return STREAM_CODEC;
		}
	}
}
