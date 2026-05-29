package daw.ka.informejtycy.sound;

import net.minecraft.entity.Entity;

import java.util.UUID;
import java.util.WeakHashMap;

public class SoundCooldown {
	private static final WeakHashMap<UUID, Long> cooldowns = new WeakHashMap<>();
	private static final long COOLDOWN_TICKS = 65;

	public static boolean canPlaySound(Entity player, long currentTick) {
		UUID uuid = player.getUuid();
		Long lastPlayedTick = cooldowns.get(uuid);
		if (lastPlayedTick == null || (currentTick - lastPlayedTick) >= COOLDOWN_TICKS) {
			cooldowns.put(uuid, currentTick);
			return true;
		}
		return false;
	}
}
