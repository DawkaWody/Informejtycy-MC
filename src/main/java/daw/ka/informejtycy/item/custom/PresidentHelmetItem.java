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
    private static final int EFFECT_DURATION = 280;
    private static final int REFRESH_BELOW = 200;
    private static final int AMPLIFIER = 1;

    public PresidentHelmetItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (slot != EquipmentSlot.HEAD) return;
        if (!(entity instanceof LivingEntity living)) return;

        StatusEffectInstance current = living.getStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
        if (current == null || current.getAmplifier() < AMPLIFIER || current.isDurationBelow(REFRESH_BELOW)) {
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, EFFECT_DURATION, AMPLIFIER, false, false, true));
        }
    }
}
