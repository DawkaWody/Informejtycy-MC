package daw.ka.informejtycy.enchantment.custom;

import net.minecraft.world.entity.EntityTypes;
import com.mojang.serialization.MapCodec;
import daw.ka.informejtycy.sound.CustomSounds;
import daw.ka.informejtycy.sound.SoundCooldown;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public record ThunderstruckEnchantmentEffect() implements EnchantmentEntityEffect {
	public static final MapCodec<ThunderstruckEnchantmentEffect> CODEC = MapCodec.unit(ThunderstruckEnchantmentEffect::new);

	@Override
	public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity target, Vec3 pos) {
		for (int i = 0; i < level; i++) {
			EntityTypes.LIGHTNING_BOLT.spawn(world, target.blockPosition(), EntitySpawnReason.TRIGGERED);
		}

		Entity player = context.owner();
		if (player != null && SoundCooldown.canPlaySound(player, world.getGameTime())) {
			world.playSound(null, target.blockPosition(), CustomSounds.THUNDERSTRUCK, SoundSource.PLAYERS, 1.0F, 1.0F);
		}
	}

	@Override
	public MapCodec<? extends EnchantmentEntityEffect> codec() {
		return CODEC;
	}
}
