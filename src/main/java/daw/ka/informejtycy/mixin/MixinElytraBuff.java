package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.item.CustomItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinElytraBuff {
    @Unique private static final float liftCoefficient = 0.85f;
    @Unique private static final float liftMultiplier = 0.055f;
    @Unique private static final float gravityMultiplier = 0.98f;

    @Inject(method = "updateFallFlyingMovement", at = @At("HEAD"), cancellable = true)
    private void buffElytraFlight(Vec3 movement, CallbackInfoReturnable<Vec3> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        Item wornItem = self.getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (wornItem != CustomItems.ZMYSIO_ELYTRA) return;

        Vec3 vec3d = self.getLookAngle();
        float f = self.getXRot() * (float) (Math.PI / 180.0);
        double d = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
        double e = movement.horizontalDistance();
        double g = this.getEffectiveGravityAccessible();
        double h = Mth.square(Math.cos(f));
        movement = movement.add(0.0, g * (-1.0 + h * liftCoefficient), 0.0);
        if (movement.y < 0.0 && d > 0.0) {
            double i = movement.y * -0.1 * h;
            movement = movement.add(vec3d.x * i / d, i, vec3d.z * i / d);
        }

        if (f < 0.0F && d > 0.0) {
            double i = e * -Mth.sin(f) * liftMultiplier;
            movement = movement.add(-vec3d.x * i / d, i * 3.2, -vec3d.z * i / d);
        }

        if (d > 0.0) {
            movement = movement.add((vec3d.x / d * e - movement.x) * 0.1, 0.0, (vec3d.z / d * e - movement.z) * 0.1);
        }

        Vec3 result = movement.multiply(0.99F, gravityMultiplier, 0.99F);
        cir.setReturnValue(result);
    }

    @Unique
    private double getEffectiveGravityAccessible() {
        LivingEntity self = (LivingEntity) (Object) this;
        boolean bl = self.getDeltaMovement().y <= 0.0;
        return bl && self.hasEffect(MobEffects.SLOW_FALLING) ? Math.min(self.getGravity(), 0.01) : self.getGravity();
    }
}
