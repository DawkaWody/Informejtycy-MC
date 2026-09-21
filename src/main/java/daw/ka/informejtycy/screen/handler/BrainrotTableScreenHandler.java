package daw.ka.informejtycy.screen.handler;

import daw.ka.informejtycy.block.entity.custom.BrainrotTableBlockEntity;
import daw.ka.informejtycy.screen.InformejtycyScreenHandlers;
import daw.ka.informejtycy.util.FilteredSlot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.core.BlockPos;

public class BrainrotTableScreenHandler extends AbstractContainerMenu {
	public final BrainrotTableBlockEntity blockEntity;
	private final Container inventory;
	private final ContainerData propertyDelegate;

	public BrainrotTableScreenHandler(int syncId, Inventory inventory, BlockPos pos) {
		this(syncId, inventory, inventory.player.level().getBlockEntity(pos), new SimpleContainerData(2));
	}

	public BrainrotTableScreenHandler(int syncId, Inventory playerInventory,
									BlockEntity blockEntity, ContainerData propertyDelegate) {
		super(InformejtycyScreenHandlers.BRAINROT_TABLE_SCREEN_HANDLER, syncId);
		this.inventory = (Container) blockEntity;
		this.blockEntity = (BrainrotTableBlockEntity) blockEntity;
		this.propertyDelegate = propertyDelegate;

		this.addSlot(new FilteredSlot(inventory, 0, 53, 35));
		this.addSlot(new FilteredSlot(inventory, 1, 108, 35));

		addPlayerInventorySlots(playerInventory);

		addDataSlots(propertyDelegate);
	}

	public boolean isCrafting() {
		return this.propertyDelegate.get(0) > 0;
	}

	public int getScaledArrowProgress() {
		int progress = this.propertyDelegate.get(0);
		int maxProgress = this.propertyDelegate.get(1);
		int arrowPixelSize = 21;

		return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot.hasItem()) {
			ItemStack originalStack = slot.getItem();
			newStack = originalStack.copy();

			if (invSlot < this.inventory.getContainerSize()) {
				if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(originalStack, 0, BrainrotTableBlockEntity.OUTPUT_SLOT, false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return newStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.inventory.stillValid(player);
	}

	private void addPlayerInventorySlots(Inventory playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				int x = 8 + j * 18 - (j >= 5 ? 1 : 0);
				int y = 84 + i * 18;
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, x, y));
			}
		}
		for (int i = 0; i < 9; i++) {
			int x = 8 + i * 18 - (i >= 5 ? 1 : 0);
			this.addSlot(new Slot(playerInventory, i, x, 142));
		}
	}
}
