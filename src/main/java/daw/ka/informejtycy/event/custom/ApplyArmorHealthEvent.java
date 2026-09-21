package daw.ka.informejtycy.event.custom;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.custom.HealthBonusArmorItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;

public class ApplyArmorHealthEvent {
    public static void register() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register(((livingEntity, equipmentSlot, previousStack, currentStack) -> {
            updateArmorBonuses(livingEntity);
        }));
    }

    private static void updateArmorBonuses(LivingEntity entity) {
        if (entity.level().isClientSide()) return;

        float totalBonus = 0;
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : slots)
            if (entity.getItemBySlot(slot).getItem() instanceof HealthBonusArmorItem)
                totalBonus += HealthBonusArmorItem.HEALTH_BONUS;

        AttributeInstance healthAttribute = Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH));
        healthAttribute.removeModifier(InformejtycyRegistry.id("armor_health_bonus"));
        healthAttribute.addPermanentModifier(
                new AttributeModifier(InformejtycyRegistry.id("armor_health_bonus"),
                        totalBonus, AttributeModifier.Operation.ADD_VALUE));
    }
}
