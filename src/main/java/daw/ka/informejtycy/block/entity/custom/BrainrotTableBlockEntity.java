package daw.ka.informejtycy.block.entity.custom;

import net.minecraft.world.item.DyeColor;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.recipe.CustomRecipes;
import daw.ka.informejtycy.recipe.custom.BrainrotTableRecipe;
import daw.ka.informejtycy.recipe.custom.BrainrotTableRecipeInput;
import daw.ka.informejtycy.screen.handler.BrainrotTableScreenHandler;
import daw.ka.informejtycy.util.ImplementedInventory;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class BrainrotTableBlockEntity extends BlockEntity implements ExtendedMenuProvider<BlockPos>, ImplementedInventory {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
	private static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;

	private static final int[] INPUT_SLOTS = {INPUT_SLOT};
	private static final int[] BOTTOM_SLOTS = {OUTPUT_SLOT};

	private static final int XP_DROP = 10;

	protected final ContainerData propertyDelegate;
	private int progress;
	private int maxProgress = 280;
	private Item craftingItem;

	public BrainrotTableBlockEntity(BlockPos pos, BlockState state) {
		super(CustomBlockEntities.BRAINROT_TABLE_BLOCK_ENTITY_TYPE, pos, state);
		this.propertyDelegate = new ContainerData() {
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
			public int getCount() {
				return 2;
			}
		};
	}

	public void tick(Level world, BlockPos pos, BlockState state, BrainrotTableBlockEntity blockEntity) {
		boolean isCrafting = blockEntity.progress > 0;

		Item inputItem = this.getItem(INPUT_SLOT).getItem();
		if (inputItem != craftingItem) {
			craftingItem = inputItem;
			resetProgress();
		}

		if (hasRecipe()) {
			increaseCraftingProgress();
			setChanged(world, pos, state);
			if (hasCraftingFinished()) {
				craftItem();
				resetProgress();
			}
		}
		else {
			resetProgress();
		}

		if (isCrafting && !world.isClientSide() && world.getGameTime() % 20 == 0) {
			world.playSound(null, pos, SoundEvents.GOAT_SCREAMING_MILK, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	private void resetProgress() {
		this.progress = 0;
		this.maxProgress = 280;
	}

	private void craftItem() {
		Optional<RecipeHolder<BrainrotTableRecipe>> recipe = getCurrentRecipe();

		ItemStack output = recipe.get().value().output().create();
		if (output.is(Items.PAINTING)) {
			ItemStack painting = new ItemStack(Items.PAINTING);
			if (this.getItem(INPUT_SLOT).is(Items.NETHERITE_INGOT)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("zmysio_kulturysta"));
				painting.set(DataComponents.RARITY, Rarity.UNCOMMON);
			}
			else if (this.getItem(INPUT_SLOT).is(Items.DYE.pick(DyeColor.GRAY))) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("sigma"));
			}
			else if (this.getItem(INPUT_SLOT).is(CustomItems.LIGHT_FOOD)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("jacob"));
			}
			else if (this.getItem(INPUT_SLOT).is(CustomItems.HOLY_WARS_MUSIC_DISC)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("surprise"));
			}
			else if (this.getItem(INPUT_SLOT).is(Items.COPPER_INGOT)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("bialoleka"));
			}
			else if (this.getItem(INPUT_SLOT).is(CustomItems.DARK_GLOWSTONE_DUST)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("hitla"));
			}
			else if (this.getItem(INPUT_SLOT).is(Items.COOKIE)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("dzulian"));
			}
			else if (this.getItem(INPUT_SLOT).is(Items.REINFORCED_DEEPSLATE)) {
				painting.set(DataComponents.PAINTING_VARIANT, paintingVariant("wuzini"));
			}
			this.setItem(OUTPUT_SLOT, painting);
		} else if (this.getItem(OUTPUT_SLOT).isEmpty()) {
			this.setItem(OUTPUT_SLOT, output.copy());
		} else {
			this.getItem(OUTPUT_SLOT).grow(output.getCount());
		}
		this.removeItem(INPUT_SLOT, 1);

		if (level != null && !level.isClientSide()) {
			level.addFreshEntity(new ExperienceOrb(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5,
					worldPosition.getZ() + 0.5, XP_DROP));
		}
	}

	Holder.Reference<PaintingVariant> paintingVariant(String name) {
		return this.getLevel() != null ? this.getLevel().registryAccess().lookupOrThrow(Registries.PAINTING_VARIANT)
				.getOrThrow(ResourceKey.create(Registries.PAINTING_VARIANT,
						InformejtycyRegistry.id(name))) : null;
	}

	private boolean hasCraftingFinished() {
		return this.progress >= maxProgress;
	}

	private void increaseCraftingProgress() {
		this.progress++;
	}

	private boolean hasRecipe() {
		Optional<RecipeHolder<BrainrotTableRecipe>> recipe = getCurrentRecipe();
		if (recipe.isEmpty()) return false;

		ItemStack output = recipe.get().value().output().create();
		return canInsertOutput(output, output.getCount());
	}

	private Optional<RecipeHolder<BrainrotTableRecipe>> getCurrentRecipe() {
		RecipeManager.CachedCheck<BrainrotTableRecipeInput, BrainrotTableRecipe> getter =
				RecipeManager.createCheck(CustomRecipes.BRAINROT_TABLE_RECIPE_TYPE);

		return getter.getRecipeFor(new BrainrotTableRecipeInput(inventory.get(INPUT_SLOT)), (ServerLevel) this.getLevel());
	}

	private boolean canInsertOutput(ItemStack item, int count) {
		if (item.is(Items.PAINTING)) return this.getItem(OUTPUT_SLOT).isEmpty();
		int maxCount = this.getItem(OUTPUT_SLOT).isEmpty() ? 64 : this.getItem(OUTPUT_SLOT).getMaxStackSize();
		return (this.getItem(OUTPUT_SLOT).isEmpty() || ItemStack.isSameItemSameComponents(this.getItem(OUTPUT_SLOT), item)) &&
				this.getItem(OUTPUT_SLOT).getCount() + count <= maxCount;
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return slot == INPUT_SLOT;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.DOWN ? BOTTOM_SLOTS : INPUT_SLOTS;
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
		return slot == OUTPUT_SLOT;
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public @NonNull BlockPos getScreenOpeningData(@NonNull ServerPlayer serverPlayerEntity) {
		return this.worldPosition;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.informejtycy.brainrot_table");
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
		return new BrainrotTableScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	protected void saveAdditional(ValueOutput view) {
		super.saveAdditional(view);
		ContainerHelper.saveAllItems(view, inventory);
		view.putInt("brainrot_table.progress", progress);
		view.putInt("brainrot_table.max_progress", maxProgress);
	}

	@Override
	protected void loadAdditional(ValueInput view) {
		ContainerHelper.loadAllItems(view, inventory);
		progress = view.getIntOr("brainrot_table.progress", 0);
		maxProgress = view.getIntOr("brainrot_table.max_progress", 280);
		craftingItem = inventory.get(INPUT_SLOT).getItem();
		super.loadAdditional(view);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}
}
