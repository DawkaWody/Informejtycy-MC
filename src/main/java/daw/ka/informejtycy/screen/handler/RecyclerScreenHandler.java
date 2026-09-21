package daw.ka.informejtycy.screen.handler;

import daw.ka.informejtycy.block.entity.custom.RecyclerBlockEntity;
import daw.ka.informejtycy.item.CustomItems;
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
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.core.BlockPos;

public class RecyclerScreenHandler extends AbstractContainerMenu {
    public final RecyclerBlockEntity blockEntity;
    private final Container inventory;
    private final ContainerData propertyDelegate;

    public RecyclerScreenHandler(int syncId, Inventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(pos), new SimpleContainerData(2));
    }

    public RecyclerScreenHandler(int syncId, Inventory playerInventory, BlockEntity blockEntity, ContainerData propertyDelegate) {
        super(InformejtycyScreenHandlers.RECYCLER_SCREEN_HANDLER, syncId);
        this.inventory = (Container) blockEntity;
        this.blockEntity = (RecyclerBlockEntity) blockEntity;
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new FilteredSlot(this.inventory, 0, 79, 15));

        addPlayerInventorySlots(playerInventory);

        addDataSlots(propertyDelegate);
    }

    public boolean isCrafting() {
        return this.propertyDelegate.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int arrowPixelSize = 24;

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
            } else {
                if (originalStack.getItem() != CustomItems.RECYCLABLE_BOTTLE) {
                    return ItemStack.EMPTY;
                }
                if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                    return ItemStack.EMPTY;
                }
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

    @Override
    public void clicked(int slotIndex, int button, ContainerInput actionType, Player player) {
        super.clicked(slotIndex, button, actionType, player);

        ItemStack recyclerInput = this.blockEntity.getItem(0);
        if (recyclerInput.is(CustomItems.RECYCLABLE_BOTTLE)) {
            this.blockEntity.setLastOperator(player.getUUID());
        }
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
