package daw.ka.informejtycy.item.custom;

import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;

public class ZmysioSwordItem extends AlwaysGlintItem {
    private static final float ARMOR_IGNORE_PERCENTAGE = 0.3f;

    public ZmysioSwordItem(Properties settings) {
        super(settings);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, attacker.getUsedItemHand());
        if (attacker.level() instanceof ServerLevel world && attacker instanceof Player player) {
            float armor = target.getArmorValue();
            float toughness = (float) target.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            float baseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float armoredDamage = CombatRules.getDamageAfterAbsorb(target, baseDamage, attacker.damageSources().playerAttack(player), armor, toughness);
            float blockedDamage = baseDamage - armoredDamage;
            if (blockedDamage <= 0) return;
            int bonusDamage = Math.round(blockedDamage * ARMOR_IGNORE_PERCENTAGE);
            int regen = target.getInvulnerableTime();
            target.setInvulnerableTime(0);
            target.hurtServer(world, attacker.damageSources().playerAttack(player), bonusDamage);
            target.setInvulnerableTime(regen);
        }
    }
}
