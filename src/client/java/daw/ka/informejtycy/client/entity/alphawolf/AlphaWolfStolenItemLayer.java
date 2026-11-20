package daw.ka.informejtycy.client.entity.alphawolf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jspecify.annotations.NonNull;

/**
 * Renders the item the wolf snatched from a player in its right hand until it is put into an item frame.
 */
public class AlphaWolfStolenItemLayer extends RenderLayer<AlphaWolfRenderState, AlphaWolfModel> {
	private static final String[] ARM = {"wolf_root", "pelvis", "chest", "arm_upper_r", "arm_lower_r"};
	private static final float HAND_X = -0.2f / 16f;
	private static final float HAND_Y = 7.5f / 16f;
	private static final float HAND_Z = -1.2f / 16f;
	private static final float ITEM_SCALE = 2.0f;

	public AlphaWolfStolenItemLayer(RenderLayerParent<AlphaWolfRenderState, AlphaWolfModel> parent) {
		super(parent);
	}

	@Override
	public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector collector, int light,
					   @NonNull AlphaWolfRenderState state, float yRot, float xRot) {
		if (state.stolenItem.isEmpty()) return;

		poseStack.pushPose();
		ModelPart part = getParentModel().root();
		for (String name : ARM) {
			part = part.getChild(name);
			part.translateAndRotate(poseStack);
		}
		poseStack.translate(HAND_X, HAND_Y, HAND_Z);
		float scale = ITEM_SCALE / state.scale;
		poseStack.scale(scale, scale, scale);
		poseStack.rotateDegrees(Axis.XP, -90f);
		poseStack.rotateDegrees(Axis.YP, 180f);
		state.stolenItem.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
		poseStack.popPose();
	}
}
