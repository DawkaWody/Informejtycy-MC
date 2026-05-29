package daw.ka.informejtycy.item.custom;

import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class ZmysioSwordItem extends AlwaysGlintItem {
    private static final float ARMOR_IGNORE_PERCENTAGE = 0.3f;

    public ZmysioSwordItem(Settings settings) {
        super(settings);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, attacker.getActiveHand());
        if (attacker.getEntityWorld() instanceof ServerWorld world && attacker instanceof PlayerEntity player) {
            float armor = target.getArmor();
            float toughness = (float) target.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS);
            float baseDamage = (float) player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
            float armoredDamage = DamageUtil.getDamageLeft(target, baseDamage, attacker.getDamageSources().playerAttack(player), armor, toughness);
            float blockedDamage = baseDamage - armoredDamage;
            if (blockedDamage <= 0) return;
            int bonusDamage = Math.round(blockedDamage * ARMOR_IGNORE_PERCENTAGE);
            int regen = target.timeUntilRegen;
            target.timeUntilRegen = 0;
            target.damage(world, attacker.getDamageSources().playerAttack(player), bonusDamage);
            target.timeUntilRegen = regen;
        }
    }
}
