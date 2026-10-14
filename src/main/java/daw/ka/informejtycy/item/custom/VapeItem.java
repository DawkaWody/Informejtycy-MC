package daw.ka.informejtycy.item.custom;

import daw.ka.informejtycy.item.component.CustomDataComponents;
import daw.ka.informejtycy.item.component.custom.VapeContents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class VapeItem extends Item {
    private static final int COOLDOWN = 100;
    private static final int USE_DURATION = 32;
    private static final int BAR_COLOR = 0xA0A0FF;

    public VapeItem(Properties properties) {
        super(properties);
    }

    public static VapeContents getContents(ItemStack stack) {
        return stack.getOrDefault(CustomDataComponents.VAPE_CONTENTS, VapeContents.EMPTY);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level world, Player user, @NonNull InteractionHand hand) {
        if (getContents(user.getItemInHand(hand)).isEmpty()) return InteractionResult.FAIL;

        user.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public @NonNull ItemStack finishUsingItem(@NonNull ItemStack stack, @NonNull Level world, @NonNull LivingEntity user) {
        VapeContents contents = getContents(stack);
        if (contents.isEmpty()) return stack;

        if (world instanceof ServerLevel serverWorld) {
            PotionContents potion = contents.getNextPotion().get(DataComponents.POTION_CONTENTS);
            if (potion != null) potion.applyToLivingEntity(user, 0.8F);
            stack.set(CustomDataComponents.VAPE_CONTENTS, contents.removeOne());

            Vec3 mouth = user.getEyePosition().add(user.getLookAngle().scale(0.4));
            serverWorld.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, mouth.x, mouth.y - 0.1, mouth.z, 6, 0.1, 0.05, 0.1, 0.01);
        }
        if (user instanceof Player player) {
            player.getCooldowns().addCooldown(stack, COOLDOWN);
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.FIRE_EXTINGUISH, user.getSoundSource(), 0.5F, 1.6F);

        return stack;
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity user) {
        return USE_DURATION;
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.TOOT_HORN;
    }

    @Override
    public boolean overrideStackedOnOther(@NonNull ItemStack stack, Slot slot, @NonNull ClickAction clickAction, @NonNull Player player) {
        VapeContents contents = getContents(stack);
        ItemStack slotStack = slot.getItem();

        if (clickAction == ClickAction.PRIMARY && !slotStack.isEmpty()) {
            if (contents.canInsert(slotStack)) {
                stack.set(CustomDataComponents.VAPE_CONTENTS, contents.insert(slot.safeTake(1, 1, player)));
                playInsertSound(player);
            } else {
                playInsertFailSound(player);
            }
            broadcastChanges(player);
            return true;
        }
        if (clickAction == ClickAction.SECONDARY && slotStack.isEmpty()) {
            if (!contents.isEmpty()) {
                ItemStack remainder = slot.safeInsert(contents.getNextPotion());
                if (remainder.isEmpty()) {
                    stack.set(CustomDataComponents.VAPE_CONTENTS, contents.removeOne());
                    playRemoveOneSound(player);
                }
            }
            broadcastChanges(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(@NonNull ItemStack stack, @NonNull ItemStack otherStack, @NonNull Slot slot,
                                            @NonNull ClickAction clickAction, @NonNull Player player, @NonNull SlotAccess carriedItem) {
        VapeContents contents = getContents(stack);

        if (clickAction == ClickAction.PRIMARY && !otherStack.isEmpty()) {
            if (slot.allowModification(player) && contents.canInsert(otherStack)) {
                stack.set(CustomDataComponents.VAPE_CONTENTS, contents.insert(otherStack.split(1)));
                playInsertSound(player);
            } else {
                playInsertFailSound(player);
            }
            broadcastChanges(player);
            return true;
        }
        if (clickAction == ClickAction.SECONDARY && otherStack.isEmpty()) {
            if (slot.allowModification(player) && !contents.isEmpty()) {
                carriedItem.set(contents.getNextPotion());
                stack.set(CustomDataComponents.VAPE_CONTENTS, contents.removeOne());
                playRemoveOneSound(player);
            }
            broadcastChanges(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean isBarVisible(@NonNull ItemStack stack) {
        return !getContents(stack).isEmpty();
    }

    @Override
    public int getBarWidth(@NonNull ItemStack stack) {
        return Math.min(1 + Mth.floor(12.0F * getContents(stack).size() / VapeContents.MAX_POTIONS), 13);
    }

    @Override
    public int getBarColor(@NonNull ItemStack stack) {
        PotionContents potion = getContents(stack).getNextPotion().get(DataComponents.POTION_CONTENTS);
        return potion != null ? potion.getColor() & 0xFFFFFF : BAR_COLOR;
    }

    @Override
    public @NonNull Optional<TooltipComponent> getTooltipImage(@NonNull ItemStack stack) {
        return Optional.of(getContents(stack));
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        VapeContents contents = getContents(itemEntity.getItem());
        if (contents.isEmpty()) return;

        itemEntity.getItem().set(CustomDataComponents.VAPE_CONTENTS, VapeContents.EMPTY);
        ItemUtils.onContainerDestroyed(itemEntity, contents.potions().stream().map(ItemStackTemplate::create));
    }

    private static void broadcastChanges(Player player) {
        player.containerMenu.slotsChanged(player.getInventory());
    }

    private static void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertFailSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT_FAIL, 1.0F, 1.0F);
    }
}
