package daw.ka.informejtycy.block.entity.custom;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.recipe.CustomRecipes;
import daw.ka.informejtycy.recipe.custom.BrainrotTableRecipe;
import daw.ka.informejtycy.recipe.custom.BrainrotTableRecipeInput;
import daw.ka.informejtycy.screen.handler.BrainrotTableScreenHandler;
import daw.ka.informejtycy.util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class BrainrotTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;

	private static final int XP_DROP = 10;

	protected final PropertyDelegate propertyDelegate;
	private int progress;
	private int maxProgress = 280;

	public BrainrotTableBlockEntity(BlockPos pos, BlockState state) {
		super(CustomBlockEntities.BRAINROT_TABLE_BLOCK_ENTITY_TYPE, pos, state);
		this.propertyDelegate = new PropertyDelegate() {
			@Override
			public int get(int index) {
				return switch (index) {
					case 0 -> progress;
					case 1 -> maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0 -> progress = value;
					case 1 -> maxProgress = value;
				}
			}

			@Override
			public int size() {
				return 2;
			}
		};
	}

	public void tick(World world, BlockPos pos, BlockState state, BrainrotTableBlockEntity blockEntity) {
		boolean isCrafting = blockEntity.progress > 0;

		if (hasRecipe()) {
			increaseCraftingProgress();
			markDirty(world, pos, state);
			if (hasCraftingFinished()) {
				craftItem();
				resetProgress();
			}
		}
		else {
			resetProgress();
		}

		if (isCrafting && !world.isClient() && world.getTime() % 20 == 0) {
			world.playSound(null, pos, SoundEvents.ENTITY_GOAT_SCREAMING_MILK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

	private void resetProgress() {
		this.progress = 0;
		this.maxProgress = 280;
	}

	private void craftItem() {
		Optional<RecipeEntry<BrainrotTableRecipe>> recipe = getCurrentRecipe();

		ItemStack output = recipe.get().value().output();
		if (output.isOf(Items.PAINTING)) {
			ItemStack painting = new ItemStack(Items.PAINTING);
			if (this.getStack(INPUT_SLOT).isOf(Items.NETHERITE_INGOT)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("zmysio_kulturysta"));
				painting.set(DataComponentTypes.RARITY, Rarity.UNCOMMON);
			}
			else if (this.getStack(INPUT_SLOT).isOf(Items.GRAY_DYE)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("sigma"));
			}
			else if (this.getStack(INPUT_SLOT).isOf(CustomItems.LIGHT_FOOD)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("jacob"));
			}
			else if (this.getStack(INPUT_SLOT).isOf(CustomItems.HOLY_WARS_MUSIC_DISC)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("surprise"));
			}
			else if (this.getStack(INPUT_SLOT).isOf(Items.COPPER_INGOT)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("bialoleka"));
			}
			else if (this.getStack(INPUT_SLOT).isOf(CustomItems.DARK_GLOWSTONE_DUST)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("hitla"));
			}
			else if (this.getStack(INPUT_SLOT).isOf(Items.COOKIE)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("dzulian"));
			}
			else if (this.getStack(INPUT_SLOT).isOf(Items.REINFORCED_DEEPSLATE)) {
				painting.set(DataComponentTypes.PAINTING_VARIANT, paintingVariant("wuzini"));
			}
			this.setStack(OUTPUT_SLOT, painting);
		} else {
			this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
					this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
		}
		this.removeStack(INPUT_SLOT, 1);

		if (world != null && !world.isClient()) {
			world.spawnEntity(new ExperienceOrbEntity(world, pos.getX() + 0.5, pos.getY() + 0.5,
					pos.getZ() + 0.5, XP_DROP));
		}
	}

	RegistryEntry.Reference<PaintingVariant> paintingVariant(String name) {
		return this.getWorld() != null ? this.getWorld().getRegistryManager().getOrThrow(RegistryKeys.PAINTING_VARIANT)
				.getOrThrow(RegistryKey.of(RegistryKeys.PAINTING_VARIANT,
						InformejtycyRegistry.id(name))) : null;
	}

	private boolean hasCraftingFinished() {
		return this.progress >= maxProgress;
	}

	private void increaseCraftingProgress() {
		this.progress++;
	}

	private boolean hasRecipe() {
		Optional<RecipeEntry<BrainrotTableRecipe>> recipe = getCurrentRecipe();
		if (recipe.isEmpty()) return false;

		ItemStack output = recipe.get().value().output();
		return canInsertOutput(output, output.getCount());
	}

	private Optional<RecipeEntry<BrainrotTableRecipe>> getCurrentRecipe() {
		ServerRecipeManager.MatchGetter<BrainrotTableRecipeInput, BrainrotTableRecipe> getter =
				ServerRecipeManager.createCachedMatchGetter(CustomRecipes.BRAINROT_TABLE_RECIPE_TYPE);

		return getter.getFirstMatch(new BrainrotTableRecipeInput(inventory.get(INPUT_SLOT)), (ServerWorld) this.getWorld());
	}

	private boolean canInsertOutput(ItemStack item, int count) {
		if (item.isOf(Items.PAINTING)) return this.getStack(OUTPUT_SLOT).isEmpty();
		int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
		return (this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == item.getItem()) &&
				this.getStack(OUTPUT_SLOT).getCount() + count <= maxCount;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public @NonNull BlockPos getScreenOpeningData(@NonNull ServerPlayerEntity serverPlayerEntity) {
		return this.pos;
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("block.informejtycy.brainrot_table");
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new BrainrotTableScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		Inventories.writeData(view, inventory);
		view.putInt("brainrot_table.progress", progress);
		view.putInt("brainrot_table.max_progress", maxProgress);
	}

	@Override
	protected void readData(ReadView view) {
		Inventories.readData(view, inventory);
		progress = view.getInt("brainrot_table.progress", 0);
		maxProgress = view.getInt("brainrot_table.max_progress", 280);
		super.readData(view);
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
		return createNbt(registries);
	}
}
