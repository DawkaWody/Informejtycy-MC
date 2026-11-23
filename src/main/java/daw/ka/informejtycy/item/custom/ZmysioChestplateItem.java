package daw.ka.informejtycy.item.custom;

import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class ZmysioChestplateItem extends AlwaysGlintItem {
    private static final float MILK_SHOOT_CHANCE = 0.15f;
    private static final float MILK_PROJECTILE_SPEED = 1.5f;
    private static final double SUT_SIDE_OFFSET = 2.75 / 16.0;
    private static final double SUT_FORWARD_OFFSET = 4.5 / 16.0;
    private static final double SUT_BELOW_EYES = 0.31;

    public ZmysioChestplateItem(Properties settings) {
        super(settings);
    }

    public static void tryShootMilk(Player player, LivingEntity target) {
        if (player.getRandom().nextFloat() >= MILK_SHOOT_CHANCE) return;

        shootMilkProjectileAt(player, true, target);
        shootMilkProjectileAt(player, false, target);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.LLAMA_SPIT, player.getSoundSource(), 1.0F, 1.0F);
    }

    private static void shootMilkProjectileAt(Player player, boolean side, LivingEntity target) {
        Vec3 start = getSutCoords(player, side);
        Vec3 direction = target.getBoundingBox().getCenter().subtract(start);
        Vec3 velocity = direction.normalize().scale(MILK_PROJECTILE_SPEED);

        MilkProjectileEntity projectile = new MilkProjectileEntity(player.level(), player, start);
        projectile.setDeltaMovement(velocity);
        projectile.setIgnoresInvulnerability(true);
        player.level().addFreshEntity(projectile);
    }

    private static Vec3 getSutCoords(Player player, boolean side) {
        Vec3 local = new Vec3(side ? -SUT_SIDE_OFFSET : SUT_SIDE_OFFSET, -SUT_BELOW_EYES, SUT_FORWARD_OFFSET);
        return player.getEyePosition().add(local.yRot(-player.getYRot() * Mth.DEG_TO_RAD));
    }
}
