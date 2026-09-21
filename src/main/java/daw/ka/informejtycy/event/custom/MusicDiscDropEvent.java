package daw.ka.informejtycy.event.custom;

import net.minecraft.world.entity.EntityTypes;
import daw.ka.informejtycy.enchantment.CustomEnchantments;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class MusicDiscDropEvent {
	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity.getType() == EntityTypes.ZOMBIE && usedThunderstruckEnchantedWeapon(damageSource)) {
				dropMusicDisc(((LivingEntity) damageSource.getEntity()), entity.position(), CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC, 0.5f);
			}
			else if (entity.getType() == EntityTypes.VILLAGER && killedByPlayer(damageSource)) {
				if (isCleric(((Villager) entity))) {
					dropMusicDisc(((LivingEntity) damageSource.getEntity()), entity.position(), CustomItems.HOLY_WARS_MUSIC_DISC, 0.1f);
				}
			}
			else if (entity.getType() == EntityTypes.ZOMBIE && killedByBlaze(damageSource)) {
				dropMusicDisc(((LivingEntity) damageSource.getEntity()), entity.position(), CustomItems.YOU_MUST_BURN_MUSIC_DISC, 1f);
			}
		});
	}

	private static void dropMusicDisc(LivingEntity player, Vec3 pos, Item disc, float chance) {
		ServerLevel serverWorld = (ServerLevel) player.level();
		if (Math.random() < chance) {
			serverWorld.addFreshEntity(new ItemEntity(
					serverWorld,
					Math.round(pos.x),
					Math.round(pos.y),
					Math.round(pos.z),
					new ItemStack(disc)
			));
		}
	}

	private static boolean usedThunderstruckEnchantedWeapon(DamageSource damageSource) {
		if (!(damageSource.getEntity() instanceof Player player)) return false;
		ItemStack weapon = player.getMainHandItem();

		ServerLevel world = (ServerLevel) player.level();
				Holder<Enchantment> enchantmentEntry = world.registryAccess()
				.lookupOrThrow(Registries.ENCHANTMENT)
				.get(CustomEnchantments.THUNDERSTRUCK)
				.orElse(null);

		if (enchantmentEntry == null) return false;
		return EnchantmentHelper.getItemEnchantmentLevel(enchantmentEntry, weapon) > 0;
	}

	private static boolean isCleric(Villager entity) {
		ServerLevel world = (ServerLevel) entity.level();
		VillagerProfession villagerProfession = entity.getVillagerData().profession().value();
		return villagerProfession == world.registryAccess()
				.lookupOrThrow(Registries.VILLAGER_PROFESSION)
				.getValue(VillagerProfession.CLERIC);
	}

	private static boolean killedByPlayer(DamageSource damageSource) {
		return damageSource.getEntity() instanceof Player;
	}

	private static boolean killedByBlaze(DamageSource damageSource) {
		if (damageSource.getEntity() == null) return false;
		return damageSource.getEntity().getType() == EntityTypes.BLAZE;
	}
}
