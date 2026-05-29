package daw.ka.informejtycy.item.custom;

import daw.ka.informejtycy.particle.CustomParticles;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ZmysioElytraItem extends AlwaysGlintItem {
    public ZmysioElytraItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (world.isClient()) return;
        if (!(entity instanceof LivingEntity living)) return;

        ItemStack chestStack = living.getEquippedStack(EquipmentSlot.CHEST);
        if (chestStack != stack) return;
        if (stack.getComponents().get(DataComponentTypes.GLIDER) != Unit.INSTANCE) return;
        if (!living.isGliding()) return;

        Vec3d pos = living.getEntityPos();
        Vec3d vel = living.getVelocity();
        Vec3d back = vel.normalize().multiply(-0.6);
        double px = pos.x + back.x;
        double py = pos.y + 0.2;
        double pz = pos.z + back.z;
        world.spawnParticles(
                CustomParticles.STINK_PARTICLE,
                px, py, pz,
                6,
                0.05, 0.05, 0.05,
                0.0
        );
    }
}
