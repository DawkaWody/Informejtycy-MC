package daw.ka.informejtycy.client.entity.alphawolf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import daw.ka.informejtycy.client.entity.projectile.cigarette.CigaretteProjectileRenderer;
import daw.ka.informejtycy.client.entity.projectile.rock.RockProjectileRenderer;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.AnimationState;
import org.jspecify.annotations.NonNull;

public class AlphaWolfHeldProjectileLayer extends RenderLayer<AlphaWolfRenderState, AlphaWolfModel> {
	private static final String[] ARM = {"wolf_root", "pelvis", "chest", "arm_upper_r", "arm_lower_r"};
	private static final float HAND_X = -0.2f / 16f;
	private static final float HAND_Y = 7.5f / 16f;
	private static final float HAND_Z = -1.2f / 16f;
	private static final float GROW_TICKS = 8f;

	private final Model.Simple rock = new Model.Simple(AlphaWolfMesh.load("rock"), RenderTypes::entityCutout);
	private final Model.Simple cigarette = new Model.Simple(AlphaWolfMesh.load("cigarette"), RenderTypes::entityCutout);

	public AlphaWolfHeldProjectileLayer(RenderLayerParent<AlphaWolfRenderState, AlphaWolfModel> parent) {
		super(parent);
	}

	@Override
	public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector collector, int light,
					   @NonNull AlphaWolfRenderState state, float yRot, float xRot) {
		submitHeld(poseStack, collector, light, state, AlphaWolfAction.THROW_ROCK, rock, RockProjectileRenderer.TEXTURE, 0f);
		submitHeld(poseStack, collector, light, state, AlphaWolfAction.THROW_SHURIKEN, cigarette, CigaretteProjectileRenderer.TEXTURE, 90f);
	}

	private void submitHeld(PoseStack poseStack, SubmitNodeCollector collector, int light, AlphaWolfRenderState state,
							AlphaWolfAction action, Model.Simple model, Identifier texture, float yRotation) {
		AnimationState throwing = state.animationStates.get(action);
		float ticks = throwing.getTimeInMillis(state.ageInTicks) / 50f;
		if (!throwing.isStarted() || ticks >= action.hitTick) return;

		poseStack.pushPose();
		ModelPart part = getParentModel().root();
		for (String name : ARM) {
			part = part.getChild(name);
			part.translateAndRotate(poseStack);
		}
		poseStack.translate(HAND_X, HAND_Y, HAND_Z);
		poseStack.rotateDegrees(Axis.YP, yRotation);
		float scale = Math.min(ticks / Math.min(GROW_TICKS, action.hitTick), 1f) / state.scale;
		poseStack.scale(scale, scale, scale);
		collector.submitModel(model, Unit.INSTANCE, poseStack, model.renderType(texture),
				light, OverlayTexture.NO_OVERLAY, state.outlineColor);
		poseStack.popPose();
	}
}
