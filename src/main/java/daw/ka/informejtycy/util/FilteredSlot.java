package daw.ka.informejtycy.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FilteredSlot extends Slot {
	public FilteredSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return this.inventory.isValid(this.getIndex(), stack);
	}
}
