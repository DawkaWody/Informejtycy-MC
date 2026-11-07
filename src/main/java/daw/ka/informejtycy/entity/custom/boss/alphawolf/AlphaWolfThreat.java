package daw.ka.informejtycy.entity.custom.boss.alphawolf;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AlphaWolfThreat {
	private static final int DECAY_INTERVAL = 20;
	private static final float DECAY = 0.8f;
	private static final float FORGET_BELOW = 0.5f;
	private static final float AFTER_ATTACK = 0.5f;

	private final Map<UUID, Float> threat = new HashMap<>();

	public void add(Player player, float damage) {
		threat.merge(player.getUUID(), damage, Float::sum);
	}

	public void attacked(LivingEntity target) {
		threat.computeIfPresent(target.getUUID(), (uuid, value) -> value * AFTER_ATTACK);
	}

	public void tick(int tickCount) {
		if (tickCount % DECAY_INTERVAL != 0) return;
		threat.replaceAll((uuid, value) -> value * DECAY);
		threat.values().removeIf(value -> value < FORGET_BELOW);
	}

	public @Nullable Player pick(List<Player> candidates, LivingEntity wolf) {
		return candidates.stream().max(Comparator
				.comparingDouble((Player player) -> threat.getOrDefault(player.getUUID(), 0f))
				.thenComparingDouble(player -> -wolf.distanceToSqr(player))).orElse(null);
	}
}
