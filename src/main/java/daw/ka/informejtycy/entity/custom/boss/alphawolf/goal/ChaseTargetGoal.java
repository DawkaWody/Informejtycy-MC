package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jspecify.annotations.NonNull;

public class ChaseTargetGoal extends MeleeAttackGoal {
	public ChaseTargetGoal(PathfinderMob mob, double speedModifier) {
		super(mob, speedModifier, true);
	}

	@Override
	protected void checkAndPerformAttack(@NonNull LivingEntity target) {

	}
}
