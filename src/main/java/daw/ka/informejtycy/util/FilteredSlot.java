package daw.ka.informejtycy.util;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;

public class FilteredSlot extends Slot {
	public FilteredSlot(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return this.container.canPlaceItem(this.getContainerSlot(), stack);
	}
}
