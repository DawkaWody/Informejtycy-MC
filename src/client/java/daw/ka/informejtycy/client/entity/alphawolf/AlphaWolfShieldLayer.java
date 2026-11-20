package daw.ka.informejtycy.client.entity.alphawolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class AlphaWolfShieldLayer extends EnergySwirlLayer<AlphaWolfRenderState, AlphaWolfModel> {
	private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/wither/wither_armor.png");
	private static final float INFLATE = 1.04f;

	public AlphaWolfShieldLayer(RenderLayerParent<AlphaWolfRenderState, AlphaWolfModel> renderer) {
		super(renderer);
	}

	@Override
	public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector collector, int light,
					   @NonNull AlphaWolfRenderState state, float yRot, float xRot) {
		if (!state.shielded) return;
		poseStack.pushPose();
		poseStack.scale(INFLATE, INFLATE, INFLATE);
		super.submit(poseStack, collector, light, state, yRot, xRot);
		poseStack.popPose();
	}

	@Override
	protected boolean isPowered(@NonNull AlphaWolfRenderState state) {
		return state.shielded;
	}

	@Override
	protected float xOffset(float time) {
		return Mth.cos(time * 0.02f) * 3.0f;
	}

	@Override
	protected @NonNull Identifier getTextureLocation() {
		return TEXTURE;
	}

	@Override
	protected @NonNull AlphaWolfModel model() {
		return getParentModel();
	}
}
