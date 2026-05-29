package daw.ka.informejtycy.event.custom;

import daw.ka.informejtycy.enchantment.CustomEnchantments;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.VillagerProfession;

public class MusicDiscDropEvent {
	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity.getType() == EntityType.ZOMBIE && usedThunderstruckEnchantedWeapon(damageSource)) {
				dropMusicDisc(((LivingEntity) damageSource.getAttacker()), entity.getEntityPos(), CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC, 0.5f);
			}
			else if (entity.getType() == EntityType.VILLAGER && killedByPlayer(damageSource)) {
				if (isCleric(((VillagerEntity) entity))) {
					dropMusicDisc(((LivingEntity) damageSource.getAttacker()), entity.getEntityPos(), CustomItems.HOLY_WARS_MUSIC_DISC, 0.1f);
				}
			}
			else if (entity.getType() == EntityType.ZOMBIE && killedByBlaze(damageSource)) {
				dropMusicDisc(((LivingEntity) damageSource.getAttacker()), entity.getEntityPos(), CustomItems.YOU_MUST_BURN_MUSIC_DISC, 1f);
			}
		});
	}

	private static void dropMusicDisc(LivingEntity player, Vec3d pos, Item disc, float chance) {
		ServerWorld serverWorld = (ServerWorld) player.getEntityWorld();
		if (Math.random() < chance) {
			serverWorld.spawnEntity(new ItemEntity(
					serverWorld,
					Math.round(pos.x),
					Math.round(pos.y),
					Math.round(pos.z),
					new ItemStack(disc)
			));
		}
	}

	private static boolean usedThunderstruckEnchantedWeapon(DamageSource damageSource) {
		if (!(damageSource.getAttacker() instanceof PlayerEntity player)) return false;
		ItemStack weapon = player.getMainHandStack();

		ServerWorld world = (ServerWorld) player.getEntityWorld();
		Registry<Enchantment> enchantmentRegistry = world.getRegistryManager()
				.getOrThrow(RegistryKeys.ENCHANTMENT);
		RegistryEntry<Enchantment> enchantmentEntry = enchantmentRegistry
				.getEntry(enchantmentRegistry.get(CustomEnchantments.THUNDERSTRUCK));

		if (enchantmentEntry == null) return false;
		return EnchantmentHelper.getLevel(enchantmentEntry, weapon) > 0;
	}

	private static boolean isCleric(VillagerEntity entity) {
		ServerWorld world = (ServerWorld) entity.getEntityWorld();
		Registry<VillagerProfession> professionRegistry = world.getRegistryManager()
				.getOrThrow(RegistryKeys.VILLAGER_PROFESSION);
		VillagerProfession villagerProfession = entity.getVillagerData().profession().value();
		return villagerProfession == professionRegistry.get(VillagerProfession.CLERIC);
	}

	private static boolean killedByPlayer(DamageSource damageSource) {
		return damageSource.getAttacker() instanceof PlayerEntity;
	}

	private static boolean killedByBlaze(DamageSource damageSource) {
		if (damageSource.getAttacker() == null) return false;
		return damageSource.getAttacker().getType() == EntityType.BLAZE;
	}
}
