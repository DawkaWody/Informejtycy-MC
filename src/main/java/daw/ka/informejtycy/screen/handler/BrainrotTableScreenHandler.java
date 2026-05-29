package daw.ka.informejtycy.screen.handler;

import daw.ka.informejtycy.block.entity.custom.BrainrotTableBlockEntity;
import daw.ka.informejtycy.screen.InformejtycyScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class BrainrotTableScreenHandler extends ScreenHandler {
	public final BrainrotTableBlockEntity blockEntity;
	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;

	public BrainrotTableScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
		this(syncId, inventory, inventory.player.getEntityWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
	}

	public BrainrotTableScreenHandler(int syncId, PlayerInventory playerInventory,
									BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
		super(InformejtycyScreenHandlers.BRAINROT_TABLE_SCREEN_HANDLER, syncId);
		this.inventory = (Inventory) blockEntity;
		this.blockEntity = (BrainrotTableBlockEntity) blockEntity;
		this.propertyDelegate = propertyDelegate;

		this.addSlot(new Slot(inventory, 0, 53, 35));
		this.addSlot(new Slot(inventory, 1, 108, 35));

		addPlayerInventorySlots(playerInventory);

		addProperties(propertyDelegate);
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
	public ItemStack quickMove(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();

			if (invSlot < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	private void addPlayerInventorySlots(PlayerInventory playerInventory) {
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
