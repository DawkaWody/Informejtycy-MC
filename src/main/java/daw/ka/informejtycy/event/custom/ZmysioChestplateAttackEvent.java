package daw.ka.informejtycy.event.custom;

import daw.ka.informejtycy.item.custom.ZmysioChestplateItem;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class ZmysioChestplateAttackEvent {
    public static void register() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClientSide() || player.isSpectator()) return InteractionResult.PASS;
            if (!(entity instanceof LivingEntity target) || !target.isAlive()) return InteractionResult.PASS;

            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ZmysioChestplateItem)
                ZmysioChestplateItem.tryShootMilk(player, target);
            return InteractionResult.PASS;
        });
    }
}
