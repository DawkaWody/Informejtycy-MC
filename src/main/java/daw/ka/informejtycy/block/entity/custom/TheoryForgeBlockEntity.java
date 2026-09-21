package daw.ka.informejtycy.block.entity.custom;

import daw.ka.informejtycy.block.custom.TheoryForgeBlock;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.recipe.CustomRecipes;
import daw.ka.informejtycy.recipe.custom.TheoryForgeRecipe;
import daw.ka.informejtycy.recipe.custom.TheoryForgeRecipeInput;
import daw.ka.informejtycy.screen.handler.TheoryForgeScreenHandler;
import daw.ka.informejtycy.util.ImplementedInventory;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.ExperienceOrb;
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
import net.minecraft.core.HolderLookup;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TheoryForgeBlockEntity extends BlockEntity implements ExtendedMenuProvider<BlockPos>, ImplementedInventory {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
	private static final int FUEL_SLOT = 0;
	private static final int INPUT_SLOT_1 = 1;
	private static final int INPUT_SLOT_2 = 2;
	public static final int OUTPUT_SLOT = 3;

	private static final int[] TOP_SLOTS = {INPUT_SLOT_1, INPUT_SLOT_2};
	private static final int[] SIDE_SLOTS = {FUEL_SLOT};
	private static final int[] BOTTOM_SLOTS = {OUTPUT_SLOT, FUEL_SLOT};

	private static final int FUEL_PER_CRAFT = 2;
	private static final int XP_DROP = 5;

	protected final ContainerData propertyDelegate;
	private int progress;
	private int maxProgress = 140;
	private int fuelLevel;
	private int maxFuelLevel = 8;
	private Item craftingItem1;
	private Item craftingItem2;

	public TheoryForgeBlockEntity(BlockPos pos, BlockState state) {
		super(CustomBlockEntities.THEORY_FORGE_BLOCK_ENTITY_TYPE, pos, state);
		this.propertyDelegate = new ContainerData() {
			@Override
			public int get(int index) {
				return switch (index) {
					case 0 -> progress;
					case 1 -> maxProgress;
					case 2 -> fuelLevel;
					case 3 -> maxFuelLevel;
					default -> 0;
				};
			}
			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0 -> progress = value;
					case 1 -> maxProgress = value;
					case 2 -> fuelLevel = value;
					case 3 -> maxFuelLevel = value;
				}
			}
			@Override
			public int getCount() {
				return 4;
			}
		};
	}

	public void tick(Level world, BlockPos pos, BlockState state, TheoryForgeBlockEntity blockEntity) {
		boolean hasFuel = blockEntity.fuelLevel > 0;
		boolean isCrafting = blockEntity.progress > 0;

		Item inputItem1 = this.getItem(INPUT_SLOT_1).getItem();
		Item inputItem2 = this.getItem(INPUT_SLOT_2).getItem();
		if (inputItem1 != craftingItem1 || inputItem2 != craftingItem2) {
			craftingItem1 = inputItem1;
			craftingItem2 = inputItem2;
			resetProgress();
		}

		if (hasRecipe() && hasEnoughFuel()) {
			increaseCraftingProgress();
			setChanged(world, pos, state);
			if (hasCraftingFinished()) {
				craftItem();
				resetProgress();
			}
		} else {
			resetProgress();
		}
		if (hasLavaBucket()) {
			addFuel();
		}
		// Update block state based on fuel available.
		if (!world.isClientSide()) {
			if (state.getValue(TheoryForgeBlock.LIT) != hasFuel) {
				world.setBlock(pos, state.setValue(TheoryForgeBlock.LIT, hasFuel), 3);
			}
		}
		if (!world.isClientSide() && world.getGameTime() % 20 == 0) {
			if (isCrafting) {
				world.playSound(null, pos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
			else if (hasFuel && world.getGameTime() % 60 == 0) {
				world.playSound(null, pos, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.25F, 1.0F);
			}
		}
	}

	private void resetProgress() {
		this.progress = 0;
		this.maxProgress = 140;
	}

	private void craftItem() {
		Optional<RecipeHolder<TheoryForgeRecipe>> recipe = getCurrentRecipe();

		ItemStack output = recipe.get().value().output().create();
		this.removeItem(INPUT_SLOT_1, 1);
		this.removeItem(INPUT_SLOT_2, 1);
		if (this.getItem(OUTPUT_SLOT).isEmpty()) {
			this.setItem(OUTPUT_SLOT, output.copy());
		} else {
			this.getItem(OUTPUT_SLOT).grow(output.getCount());
		}
		consumeFuel();

		if (level != null && !level.isClientSide()) {
			level.addFreshEntity(new ExperienceOrb(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5,
					worldPosition.getZ() + 0.5, XP_DROP));
		}
	}

	private void addFuel() {
		if (this.fuelLevel >= maxFuelLevel) return;
		this.fuelLevel += 1;
		this.setItem(FUEL_SLOT, new ItemStack(Items.BUCKET, this.getItem(FUEL_SLOT).getCount()));
		if (level != null && !level.isClientSide())
			level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	private void consumeFuel() {
		fuelLevel -= FUEL_PER_CRAFT;
	}

	private boolean hasCraftingFinished() {
		return this.progress >= maxProgress;
	}

	private void increaseCraftingProgress() {
		this.progress++;
	}

	private boolean hasRecipe() {
		Optional<RecipeHolder<TheoryForgeRecipe>> recipe = getCurrentRecipe();
		if (recipe.isEmpty()) return false;

		ItemStack output = recipe.get().value().output().create();
		return canInsertOutput(output, output.getCount());
	}

	private Optional<RecipeHolder<TheoryForgeRecipe>> getCurrentRecipe() {
		RecipeManager.CachedCheck<TheoryForgeRecipeInput, TheoryForgeRecipe> getter =
				RecipeManager.createCheck(CustomRecipes.THEORY_FORGE_RECIPE_TYPE);

		return getter.getRecipeFor(new TheoryForgeRecipeInput(inventory.get(INPUT_SLOT_1), inventory.get(INPUT_SLOT_2)), (ServerLevel) this.getLevel());
	}

	private boolean hasEnoughFuel() {
		return this.fuelLevel >= FUEL_PER_CRAFT;
	}

	private boolean hasLavaBucket() {
		return this.getItem(FUEL_SLOT).getItem() == Items.LAVA_BUCKET;
	}

	private boolean canInsertOutput(ItemStack item, int count) {
		int maxCount = this.getItem(OUTPUT_SLOT).isEmpty() ? 64 : this.getItem(OUTPUT_SLOT).getMaxStackSize();
		return (this.getItem(OUTPUT_SLOT).isEmpty() || ItemStack.isSameItemSameComponents(this.getItem(OUTPUT_SLOT), item)) &&
				this.getItem(OUTPUT_SLOT).getCount() + count <= maxCount;
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return switch (slot) {
			case FUEL_SLOT -> stack.getItem() == Items.LAVA_BUCKET;
			case INPUT_SLOT_1, INPUT_SLOT_2 -> stack.getItem() != Items.LAVA_BUCKET;
			default -> false;
		};
	}

	@Override
	public int[] getSlotsForFace(@Nullable Direction side) {
		if (side == null) return SIDE_SLOTS;

		return switch (side) {
			case UP -> TOP_SLOTS;
			case DOWN -> BOTTOM_SLOTS;
			default -> SIDE_SLOTS;
		};
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
		return slot == OUTPUT_SLOT || (slot == FUEL_SLOT && stack.getItem() == Items.BUCKET);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public BlockPos getScreenOpeningData(ServerPlayer serverPlayerEntity) {
		return this.worldPosition;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.informejtycy.theory_forge");
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
		return new TheoryForgeScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	protected void saveAdditional(ValueOutput view) {
		super.saveAdditional(view);
		ContainerHelper.saveAllItems(view, inventory);
		view.putInt("theory_forge.progress", progress);
		view.putInt("theory_forge.max_progress", maxProgress);
		view.putInt("theory_forge.fuel_level", fuelLevel);
		view.putInt("theory_forge.max_fuel_level", maxFuelLevel);
	}

	@Override
	protected void loadAdditional(ValueInput view) {
		ContainerHelper.loadAllItems(view, inventory);
		progress = view.getIntOr("theory_forge.progress", 0);
		maxProgress = view.getIntOr("theory_forge.max_progress", 140);
		fuelLevel = view.getIntOr("theory_forge.fuel_level", 0);
		maxFuelLevel = view.getIntOr("theory_forge.max_fuel_level", 8);
		craftingItem1 = inventory.get(INPUT_SLOT_1).getItem();
		craftingItem2 = inventory.get(INPUT_SLOT_2).getItem();
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