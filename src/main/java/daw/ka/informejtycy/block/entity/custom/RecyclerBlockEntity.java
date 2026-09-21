package daw.ka.informejtycy.block.entity.custom;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.screen.handler.RecyclerScreenHandler;
import daw.ka.informejtycy.util.ImplementedInventory;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.core.HolderLookup;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class RecyclerBlockEntity extends BlockEntity implements ExtendedMenuProvider<BlockPos>, ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
    private static final int[] INPUT_SLOTS = {INPUT_SLOT};
    private static final Item INPUT_ITEM = CustomItems.RECYCLABLE_BOTTLE;
    private static final List<Item> OUTPUT_POOL = List.of(
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.EMERALD,
            Items.LAPIS_LAZULI,
            Items.COAL,
            Items.REDSTONE
    );
    private static final List<Item> OUTPUT_POOL_UNCOMMON = List.of(
            Items.GOLDEN_APPLE,
            Items.DIAMOND
    );
    private static final List<Item> OUTPUT_POOL_RARE = List.of(
            Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
            CustomItems.MEGAMIKSKLASA2_MUSIC_DISC
    );

    protected final ContainerData propertyDelegate;
    private int progress;
    private int maxProgress = 100;
    private @Nullable UUID lastOperator;

    public RecyclerBlockEntity(BlockPos pos, BlockState state) {
        super(CustomBlockEntities.RECYCLER_BLOCK_ENTITY_TYPE, pos, state);
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

    public void tick(Level world, BlockPos pos, BlockState state, RecyclerBlockEntity blockEntity) {
        if (inventory.get(INPUT_SLOT).is(INPUT_ITEM)) {
            this.progress++;
            setChanged(world, pos, state);
            if (this.progress >= maxProgress) {
                craftItem();
                resetProgress();
            }
        }
        else {
            resetProgress();
        }
    }

    void craftItem() {
        if (level == null || level.isClientSide()) return;
        if (this.inventory.get(INPUT_SLOT).getCount() == 1) {
            this.inventory.set(INPUT_SLOT, ItemStack.EMPTY);
        } else {
            this.inventory.get(INPUT_SLOT).shrink(1);
        }
        boolean isRare = level.getRandom().nextFloat() < 0.01f;
        boolean isUncommon = !isRare && level.getRandom().nextFloat() < 0.15f;
        Item outputItem = isRare ? OUTPUT_POOL_RARE.get(level.getRandom().nextInt(OUTPUT_POOL_RARE.size())) :
                isUncommon ? OUTPUT_POOL_UNCOMMON.get(level.getRandom().nextInt(OUTPUT_POOL_UNCOMMON.size())) :
                        OUTPUT_POOL.get(level.getRandom().nextInt(OUTPUT_POOL.size()));
        level.addFreshEntity(new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 1.5, worldPosition.getZ() + 0.5,
                new ItemStack(outputItem, 1)));

        grantRecycleAdvancement();
    }

    public void setLastOperator(@Nullable UUID lastOperator) {
        this.lastOperator = lastOperator;
        setChanged();
    }

    private void grantRecycleAdvancement() {
        if (level == null || level.getServer() == null || lastOperator == null) return;

        ServerPlayer player = level.getServer().getPlayerList().getPlayer(lastOperator);
        if (player == null || player.level() != level) return;

        AdvancementHolder advancement = level.getServer().getAdvancements()
                .get(InformejtycyRegistry.id("recycle_a_bottle"));
        if (advancement == null) return;

        AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(advancement);
        for (String criterion : advancementProgress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, criterion);
        }
    }

    void resetProgress() {
        this.progress = 0;
        this.maxProgress = 100;
    }

    @Override
    public boolean canPlaceItem(int slot, @NonNull ItemStack stack) {
        return slot == INPUT_SLOT && stack.is(INPUT_ITEM);
    }

    @Override
    public int @NonNull [] getSlotsForFace(Direction side) {
        return INPUT_SLOTS;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public @NonNull BlockPos getScreenOpeningData(@NonNull ServerPlayer player) {
        return this.getBlockPos();
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.translatable("block.informejtycy.recycler");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, @NonNull Inventory playerInventory, @NonNull Player player) {
        return new RecyclerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, inventory);
        view.putInt("recycler.progress", progress);
        view.putInt("recycler.max_progress", maxProgress);
        if (lastOperator != null) {
            view.putString("recycler.last_operator", lastOperator.toString());
        }
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput view) {
        ContainerHelper.loadAllItems(view, inventory);
        progress = view.getIntOr("recycler.progress", 0);
        maxProgress = view.getIntOr("recycler.max_progress", 100);
        view.getString("recycler.last_operator").ifPresentOrElse(value -> {
            try {
                lastOperator = UUID.fromString(value);
            } catch (IllegalArgumentException ignored) {
                lastOperator = null;
            }
        }, () -> lastOperator = null);
        super.loadAdditional(view);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
