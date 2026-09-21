package daw.ka.informejtycy.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import org.jspecify.annotations.Nullable;

public class PresidentHelmetItem extends HealthBonusArmorItem {
    private static final int EFFECT_DURATION = 280;
    private static final int REFRESH_BELOW = 200;
    private static final int AMPLIFIER = 1;

    public PresidentHelmetItem(Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if (slot != EquipmentSlot.HEAD) return;
        if (!(entity instanceof LivingEntity living)) return;

        MobEffectInstance current = living.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
        if (current == null || current.getAmplifier() < AMPLIFIER || current.endsWithin(REFRESH_BELOW)) {
            living.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, EFFECT_DURATION, AMPLIFIER, false, false, true));
        }
    }
}
