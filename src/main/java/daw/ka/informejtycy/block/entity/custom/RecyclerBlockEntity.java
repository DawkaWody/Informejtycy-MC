package daw.ka.informejtycy.block.entity.custom;

import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.screen.handler.RecyclerScreenHandler;
import daw.ka.informejtycy.util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class RecyclerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
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

    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 100;
    private @Nullable UUID lastOperator;

    public RecyclerBlockEntity(BlockPos pos, BlockState state) {
        super(CustomBlockEntities.RECYCLER_BLOCK_ENTITY_TYPE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
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
            public int size() {
                return 2;
            }
        };
    }

    public void tick(World world, BlockPos pos, BlockState state, RecyclerBlockEntity blockEntity) {
        if (inventory.get(INPUT_SLOT).isOf(INPUT_ITEM)) {
            this.progress++;
            markDirty(world, pos, state);
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
        if (world == null || world.isClient()) return;
        if (this.inventory.get(INPUT_SLOT).getCount() == 1) {
            this.inventory.set(INPUT_SLOT, ItemStack.EMPTY);
        } else {
            this.inventory.get(INPUT_SLOT).decrement(1);
        }
        boolean isRare = world.random.nextFloat() < 0.01f;
        boolean isUncommon = !isRare && world.random.nextFloat() < 0.15f;
        Item outputItem = isRare ? OUTPUT_POOL_RARE.get(world.random.nextInt(OUTPUT_POOL_RARE.size())) :
                isUncommon ? OUTPUT_POOL_UNCOMMON.get(world.random.nextInt(OUTPUT_POOL_UNCOMMON.size())) :
                        OUTPUT_POOL.get(world.random.nextInt(OUTPUT_POOL.size()));
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
                new ItemStack(outputItem, 1)));

        grantRecycleAdvancement();
    }

    public void setLastOperator(@Nullable UUID lastOperator) {
        this.lastOperator = lastOperator;
        markDirty();
    }

    private void grantRecycleAdvancement() {
        if (world == null || world.getServer() == null || lastOperator == null) return;

        ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(lastOperator);
        if (player == null || player.getEntityWorld() != world) return;

        AdvancementEntry advancement = world.getServer().getAdvancementLoader()
                .get(Identifier.of("informejtycy", "recycle_a_bottle"));
        if (advancement == null) return;

        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }

    void resetProgress() {
        this.progress = 0;
        this.maxProgress = 100;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public @NonNull BlockPos getScreenOpeningData(@NonNull ServerPlayerEntity player) {
        return this.getPos();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.informejtycy.recycler");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RecyclerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, inventory);
        view.putInt("recycler.progress", progress);
        view.putInt("recycler.max_progress", maxProgress);
        if (lastOperator != null) {
            view.putString("recycler.last_operator", lastOperator.toString());
        }
    }

    @Override
    protected void readData(ReadView view) {
        Inventories.readData(view, inventory);
        progress = view.getInt("recycler.progress", 0);
        maxProgress = view.getInt("recycler.max_progress", 100);
        view.getOptionalString("recycler.last_operator").ifPresentOrElse(value -> {
            try {
                lastOperator = UUID.fromString(value);
            } catch (IllegalArgumentException ignored) {
                lastOperator = null;
            }
        }, () -> lastOperator = null);
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
