package daw.ka.informejtycy.item.custom;

import daw.ka.informejtycy.particle.CustomParticles;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ZmysioElytraItem extends AlwaysGlintItem {
    public ZmysioElytraItem(Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if (world.isClientSide()) return;
        if (!(entity instanceof LivingEntity living)) return;

        ItemStack chestStack = living.getItemBySlot(EquipmentSlot.CHEST);
        if (chestStack != stack) return;
        if (stack.getComponents().get(DataComponents.GLIDER) != Unit.INSTANCE) return;
        if (!living.isFallFlying()) return;

        Vec3 pos = living.position();
        Vec3 vel = living.getDeltaMovement();
        Vec3 back = vel.normalize().scale(-0.6);
        double px = pos.x + back.x;
        double py = pos.y + 0.2;
        double pz = pos.z + back.z;
        world.sendParticles(
                CustomParticles.STINK_PARTICLE,
                px, py, pz,
                6,
                0.05, 0.05, 0.05,
                0.0
        );
    }
}
