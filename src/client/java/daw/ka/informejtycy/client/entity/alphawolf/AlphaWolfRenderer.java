package daw.ka.informejtycy.client.entity.alphawolf;

import com.mojang.blaze3d.vertex.PoseStack;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class AlphaWolfRenderer extends MobRenderer<AlphaWolfEntity, AlphaWolfRenderState, AlphaWolfModel> {
	private static final int AWAKEN_FRAMES = 6;
	private static final String TEXTURES = "textures/entity/alpha_wolf/";

	private static final Map<AlphaWolfAction, AnimationDefinition> FIGHT_ANIMATIONS = Map.of(
			AlphaWolfAction.WALK, AlphaWolfAnim.WALK,
			AlphaWolfAction.SLASH, AlphaWolfAnim.SLASH,
			AlphaWolfAction.KICK, AlphaWolfAnim.KICK,
			AlphaWolfAction.GRAB_THROW, AlphaWolfAnim.GRAB_THROW,
			AlphaWolfAction.GROUND_PUNCH, AlphaWolfAnim.GROUND_PUNCH,
			AlphaWolfAction.ROAR, AlphaWolfAnim.ROAR,
			AlphaWolfAction.THROW_ROCK, AlphaWolfAnim.THROW_ROCK,
			AlphaWolfAction.THROW_SHURIKEN, AlphaWolfAnim.THROW_SHURIKEN,
			AlphaWolfAction.DEATH, AlphaWolfAnim.DEATH);

	private final AlphaWolfModel sitting;
	private final AlphaWolfModel transition1;
	private final AlphaWolfModel transition2;
	private final AlphaWolfModel standing;
	private final AlphaWolfModel maskOff;
	private final AlphaWolfModel unmasked;

	public AlphaWolfRenderer(EntityRendererProvider.Context context) {
		this(context, new AlphaWolfModel(AlphaWolfMesh.load("sitting", 1.17f, -1.37f), Map.of()));
	}

	private AlphaWolfRenderer(EntityRendererProvider.Context context, AlphaWolfModel sitting) {
		super(context, sitting, 0.6f);
		this.sitting = sitting;
		this.transition1 = new AlphaWolfModel(AlphaWolfMesh.load("transition1", 0f, 0.78f), Map.of());
		this.transition2 = new AlphaWolfModel(AlphaWolfMesh.load("transition2"), Map.of());
		this.standing = new AlphaWolfModel(AlphaWolfMesh.load("standing"), FIGHT_ANIMATIONS);
		this.maskOff = new AlphaWolfModel(AlphaWolfMesh.load("mask_off"), Map.of(AlphaWolfAction.MASK_OFF, AlphaWolfAnim.MASK_OFF));
		this.unmasked = new AlphaWolfModel(AlphaWolfMesh.load("unmasked"), FIGHT_ANIMATIONS);
		addLayer(new AlphaWolfHeldProjectileLayer(this));
	}

	private AlphaWolfModel modelFor(AlphaWolfPhase phase) {
		return switch (phase) {
			case STATUE, AWAKENING, SITTING -> sitting;
			case RISING_1 -> transition1;
			case RISING_2 -> transition2;
			case STANDING -> standing;
			case MASK_OFF -> maskOff;
			case UNMASKED, DEAD -> unmasked;
		};
	}

	@Override
	public void submit(@NonNull AlphaWolfRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector collector,
					   @NonNull CameraRenderState camera) {
		this.model = modelFor(state.phase);
		super.submit(state, poseStack, collector, camera);
	}

	@Override
	public @NonNull Identifier getTextureLocation(@NonNull AlphaWolfRenderState state) {
		String texture = switch (state.phase) {
			case STATUE -> "stone";
			case AWAKENING -> "awaken_%02d".formatted(
					Math.min(state.phaseTicks / AlphaWolfEntity.AWAKEN_TICKS_PER_FRAME + 1, AWAKEN_FRAMES));
			case SITTING -> "sitting";
			case RISING_1 -> "transition1";
			case RISING_2 -> "transition2";
			case STANDING -> "standing";
			case MASK_OFF -> "mask_off";
			case UNMASKED, DEAD -> "unmasked";
		};
		return InformejtycyRegistry.id(TEXTURES + texture + ".png");
	}

	@Override
	public @NonNull AlphaWolfRenderState createRenderState() {
		return new AlphaWolfRenderState();
	}

	@Override
	public void extractRenderState(@NonNull AlphaWolfEntity entity, @NonNull AlphaWolfRenderState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.phase = entity.getPhase();
		state.phaseTicks = entity.getPhaseTicks();
		state.animationStates.forEach((action, animationState) -> animationState.copyFrom(entity.animationState(action)));
		state.deathTime = 0;
	}
}
