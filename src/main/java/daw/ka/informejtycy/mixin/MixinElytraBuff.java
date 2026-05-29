package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.item.CustomItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinElytraBuff {
    private final float liftCoefficient = 0.85f; // 0.75f
    private final float liftMultiplier = 0.055f; // 0.04f

    @Inject(method = "calcGlidingVelocity", at = @At("HEAD"), cancellable = true)
    private void buffElytraFlight(Vec3d oldVelocity, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        Item wornItem = self.getEquippedStack(EquipmentSlot.CHEST).getItem();
        if (wornItem == CustomItems.ZMYSIO_ELYTRA) {
            Vec3d vec3d = self.getRotationVector();
            float f = self.getPitch() * (float) (Math.PI / 180.0);
            double d = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
            double e = oldVelocity.horizontalLength();
            double g = this.getEffectiveGravityAccessible();
            double h = MathHelper.square(Math.cos(f));
            oldVelocity = oldVelocity.add(0.0, g * (-1.0 + h * liftCoefficient), 0.0);
            if (oldVelocity.y < 0.0 && d > 0.0) {
                double i = oldVelocity.y * -0.1 * h;
                oldVelocity = oldVelocity.add(vec3d.x * i / d, i, vec3d.z * i / d);
            }

            if (f < 0.0F && d > 0.0) {
                double i = e * -MathHelper.sin(f) * liftMultiplier;
                oldVelocity = oldVelocity.add(-vec3d.x * i / d, i * 3.2, -vec3d.z * i / d);
            }

            if (d > 0.0) {
                oldVelocity = oldVelocity.add((vec3d.x / d * e - oldVelocity.x) * 0.1, 0.0, (vec3d.z / d * e - oldVelocity.z) * 0.1);
            }

            Vec3d result = oldVelocity.multiply(0.99F, 0.98F, 0.99F);
            cir.setReturnValue(result);
        }
    }

    @Unique
    private double getEffectiveGravityAccessible() {
        LivingEntity self = (LivingEntity) (Object) this;
        boolean bl = self.getVelocity().y <= 0.0;
        return bl && self.hasStatusEffect(StatusEffects.SLOW_FALLING) ? Math.min(self.getFinalGravity(), 0.01) : self.getFinalGravity();
    }
}
