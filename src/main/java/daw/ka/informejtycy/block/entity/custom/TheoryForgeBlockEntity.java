package daw.ka.informejtycy.block.entity.custom;

import daw.ka.informejtycy.block.custom.TheoryForgeBlock;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.recipe.CustomRecipes;
import daw.ka.informejtycy.recipe.custom.TheoryForgeRecipe;
import daw.ka.informejtycy.recipe.custom.TheoryForgeRecipeInput;
import daw.ka.informejtycy.screen.handler.TheoryForgeScreenHandler;
import daw.ka.informejtycy.util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
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
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TheoryForgeBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
	private static final int FUEL_SLOT = 0;
	private static final int INPUT_SLOT_1 = 1;
	private static final int INPUT_SLOT_2 = 2;
	private static final int OUTPUT_SLOT = 3;

	private static final int FUEL_PER_CRAFT = 2;
	private static final int XP_DROP = 5;

	protected final PropertyDelegate propertyDelegate;
	private int progress;
	private int maxProgress = 140;
	private int fuelLevel;
	private int maxFuelLevel = 8;

	public TheoryForgeBlockEntity(BlockPos pos, BlockState state) {
		super(CustomBlockEntities.THEORY_FORGE_BLOCK_ENTITY_TYPE, pos, state);
		this.propertyDelegate = new PropertyDelegate() {
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
			public int size() {
				return 4;
			}
		};
	}

	public void tick(World world, BlockPos pos, BlockState state, TheoryForgeBlockEntity blockEntity) {
		boolean hasFuel = blockEntity.fuelLevel > 0;
		boolean isCrafting = blockEntity.progress > 0;

		if (hasRecipe() && hasEnoughFuel()) {
			increaseCraftingProgress();
			markDirty(world, pos, state);
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
		if (!world.isClient()) {
			if (state.get(TheoryForgeBlock.LIT) != hasFuel) {
				world.setBlockState(pos, state.with(TheoryForgeBlock.LIT, hasFuel), 3);
			}
		}
		if (!world.isClient() && world.getTime() % 20 == 0) {
			if (isCrafting) {
				world.playSound(null, pos, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			else if (hasFuel && world.getTime() % 60 == 0) {
				world.playSound(null, pos, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.25F, 1.0F);
			}
		}
	}

	private void resetProgress() {
		this.progress = 0;
		this.maxProgress = 140;
	}

	private void craftItem() {
		Optional<RecipeEntry<TheoryForgeRecipe>> recipe = getCurrentRecipe();

		ItemStack output = recipe.get().value().output();
		this.removeStack(INPUT_SLOT_1, 1);
		this.removeStack(INPUT_SLOT_2, 1);
		this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
				this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
		consumeFuel();

		if (world != null && !world.isClient()) {
			world.spawnEntity(new ExperienceOrbEntity(world, pos.getX() + 0.5, pos.getY() + 0.5,
					pos.getZ() + 0.5, XP_DROP));
		}
	}

	private void addFuel() {
		if (this.fuelLevel >= maxFuelLevel) return;
		this.fuelLevel += 1;
		this.setStack(FUEL_SLOT, new ItemStack(Items.BUCKET, this.getStack(FUEL_SLOT).getCount()));
		if (world != null && !world.isClient())
			world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
		Optional<RecipeEntry<TheoryForgeRecipe>> recipe = getCurrentRecipe();
		if (recipe.isEmpty()) return false;

		ItemStack output = recipe.get().value().output();
		return canInsertOutput(output, output.getCount());
	}

	private Optional<RecipeEntry<TheoryForgeRecipe>> getCurrentRecipe() {
		ServerRecipeManager.MatchGetter<TheoryForgeRecipeInput, TheoryForgeRecipe> getter =
				ServerRecipeManager.createCachedMatchGetter(CustomRecipes.THEORY_FORGE_RECIPE_TYPE);

		return getter.getFirstMatch(new TheoryForgeRecipeInput(inventory.get(INPUT_SLOT_1), inventory.get(INPUT_SLOT_2)), (ServerWorld) this.getWorld());
	}

	private boolean hasEnoughFuel() {
		return this.fuelLevel >= FUEL_PER_CRAFT;
	}

	private boolean hasLavaBucket() {
		return this.getStack(FUEL_SLOT).getItem() == Items.LAVA_BUCKET;
	}

	private boolean canInsertOutput(ItemStack item, int count) {
		int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
		return (this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == item.getItem()) &&
				this.getStack(OUTPUT_SLOT).getCount() + count <= maxCount;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
		return switch (slot) {
			case FUEL_SLOT -> stack.getItem() == Items.LAVA_BUCKET;
			case INPUT_SLOT_1, INPUT_SLOT_2 -> true;
			default -> false;
		};
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
		return this.pos;
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("block.informejtycy.theory_forge");
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new TheoryForgeScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		Inventories.writeData(view, inventory);
		view.putInt("theory_forge.progress", progress);
		view.putInt("theory_forge.max_progress", maxProgress);
		view.putInt("theory_forge.fuel_level", fuelLevel);
		view.putInt("theory_forge.max_fuel_level", maxFuelLevel);
	}

	@Override
	protected void readData(ReadView view) {
		Inventories.readData(view, inventory);
		progress = view.getInt("theory_forge.progress", 0);
		maxProgress = view.getInt("theory_forge.max_progress", 140);
		fuelLevel = view.getInt("theory_forge.fuel_level", 0);
		maxFuelLevel = view.getInt("theory_forge.max_fuel_level", 8);
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