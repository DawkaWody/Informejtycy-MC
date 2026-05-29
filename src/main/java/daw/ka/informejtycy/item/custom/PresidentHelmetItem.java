package daw.ka.informejtycy.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jspecify.annotations.Nullable;

public class PresidentHelmetItem extends HealthBonusArmorItem {
    public PresidentHelmetItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (world.isClient()) return;
        if (!(entity instanceof LivingEntity living)) return;
        ItemStack headSlot = living.getEquippedStack(EquipmentSlot.HEAD);
        if (headSlot.getItem() != stack.getItem()) return;
        living.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 280, 1, false, false, true));
    }
}
