package daw.ka.informejtycy.item.component.custom;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public record VapeContents(List<ItemStackTemplate> potions) implements TooltipComponent {
    public static final int MAX_POTIONS = 4;
    public static final VapeContents EMPTY = new VapeContents(List.of());
    public static final Codec<VapeContents> CODEC = ItemStackTemplate.CODEC.sizeLimitedListOf(MAX_POTIONS)
            .xmap(VapeContents::new, VapeContents::potions);
    public static final StreamCodec<RegistryFriendlyByteBuf, VapeContents> STREAM_CODEC = ItemStackTemplate.STREAM_CODEC
            .apply(ByteBufCodecs.list(MAX_POTIONS))
            .map(VapeContents::new, VapeContents::potions);

    public VapeContents {
        potions = List.copyOf(potions);
    }

    public int size() {
        return potions.size();
    }

    public boolean isEmpty() {
        return potions.isEmpty();
    }

    public boolean isFull() {
        return potions.size() >= MAX_POTIONS;
    }

    public ItemStack getPotion(int index) {
        return potions.get(index).create();
    }

    public ItemStack getNextPotion() {
        return isEmpty() ? ItemStack.EMPTY : getPotion(potions.size() - 1);
    }

    public boolean canInsert(ItemStack stack) {
        if (stack.isEmpty() || !stack.is(Items.POTION) || isFull()) return false;
        return isEmpty() || ItemStack.isSameItemSameComponents(getPotion(0), stack);
    }

    public VapeContents insert(ItemStack stack) {
        List<ItemStackTemplate> newPotions = new ArrayList<>(potions);
        newPotions.add(ItemStackTemplate.fromNonEmptyStack(stack, 1));
        return new VapeContents(newPotions);
    }

    public VapeContents removeOne() {
        return isEmpty() ? this : new VapeContents(potions.subList(0, potions.size() - 1));
    }
}
