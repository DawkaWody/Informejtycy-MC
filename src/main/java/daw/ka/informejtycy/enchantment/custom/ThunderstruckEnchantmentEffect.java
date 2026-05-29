package daw.ka.informejtycy.enchantment.custom;

import com.mojang.serialization.MapCodec;
import daw.ka.informejtycy.sound.CustomSounds;
import daw.ka.informejtycy.sound.SoundCooldown;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public record ThunderstruckEnchantmentEffect() implements EnchantmentEntityEffect {
	public static final MapCodec<ThunderstruckEnchantmentEffect> CODEC = MapCodec.unit(ThunderstruckEnchantmentEffect::new);

	@Override
	public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
		for (int i = 0; i < level; i++) {
			EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
		}

		Entity player = context.owner();
		if (player != null && SoundCooldown.canPlaySound(player, world.getTime())) {
			world.playSound(null, target.getBlockPos(), CustomSounds.THUNDERSTRUCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}

	@Override
	public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
		return CODEC;
	}
}
