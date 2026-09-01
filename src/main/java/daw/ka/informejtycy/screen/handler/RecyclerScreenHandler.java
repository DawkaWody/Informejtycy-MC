package daw.ka.informejtycy.screen.handler;

import daw.ka.informejtycy.block.entity.custom.RecyclerBlockEntity;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.screen.InformejtycyScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class RecyclerScreenHandler extends ScreenHandler {
    public final RecyclerBlockEntity blockEntity;
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public RecyclerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getEntityWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
    }

    public RecyclerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(InformejtycyScreenHandlers.RECYCLER_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory) blockEntity;
        this.blockEntity = (RecyclerBlockEntity) blockEntity;
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new Slot(this.inventory, 0, 79, 15) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == CustomItems.RECYCLABLE_BOTTLE;
            }
        });

        addPlayerInventorySlots(playerInventory);

        addProperties(propertyDelegate);
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
            } else {
                if (originalStack.getItem() != CustomItems.RECYCLABLE_BOTTLE) {
                    return ItemStack.EMPTY;
                }
                if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                    return ItemStack.EMPTY;
                }
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

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);

        ItemStack recyclerInput = this.blockEntity.getStack(0);
        if (recyclerInput.isOf(CustomItems.RECYCLABLE_BOTTLE)) {
            this.blockEntity.setLastOperator(player.getUuid());
        }
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
