package daw.ka.informejtycy.event.custom;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.custom.HealthBonusArmorItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.Objects;

public class ApplyArmorHealthEvent {
    public static void register() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register(((livingEntity, equipmentSlot, previousStack, currentStack) -> {
            updateArmorBonuses(livingEntity);
        }));
    }

    private static void updateArmorBonuses(LivingEntity entity) {
        if (entity.getEntityWorld().isClient()) return;

        float totalBonus = 0;
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : slots)
            if (entity.getEquippedStack(slot).getItem() instanceof HealthBonusArmorItem)
                totalBonus += HealthBonusArmorItem.HEALTH_BONUS;

        EntityAttributeInstance healthAttribute = Objects.requireNonNull(entity.getAttributeInstance(EntityAttributes.MAX_HEALTH));
        healthAttribute.removeModifier(InformejtycyRegistry.id("armor_health_bonus"));
        healthAttribute.addPersistentModifier(
                new EntityAttributeModifier(InformejtycyRegistry.id("armor_health_bonus"),
                        totalBonus, EntityAttributeModifier.Operation.ADD_VALUE));
    }
}
