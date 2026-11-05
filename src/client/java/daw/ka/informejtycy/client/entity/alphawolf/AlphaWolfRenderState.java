package daw.ka.informejtycy.client.entity.alphawolf;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

import java.util.EnumMap;
import java.util.Map;

public class AlphaWolfRenderState extends LivingEntityRenderState {
	public final Map<AlphaWolfAction, AnimationState> animationStates = new EnumMap<>(AlphaWolfAction.class);
	public AlphaWolfPhase phase = AlphaWolfPhase.STATUE;
	public int phaseTicks;

	public AlphaWolfRenderState() {
		for (AlphaWolfAction action : AlphaWolfAction.values()) animationStates.put(action, new AnimationState());
	}
}
