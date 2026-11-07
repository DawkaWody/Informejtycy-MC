package daw.ka.informejtycy.client.entity.projectile.meteor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.client.entity.alphawolf.AlphaWolfMesh;
import daw.ka.informejtycy.entity.custom.projectile.MeteorProjectileEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import org.jspecify.annotations.NonNull;

public class MeteorProjectileRenderer extends EntityRenderer<MeteorProjectileEntity, EntityRenderState> {
    private static final Identifier TEXTURE = InformejtycyRegistry.id("textures/entity/alpha_wolf/meteor.png");
    private static final float SPIN_DEGREES_PER_TICK = 12.0f;
    private static final int FULL_BRIGHT = 0xF000F0;

    private final Model.Simple model = new Model.Simple(AlphaWolfMesh.load("rock"), RenderTypes::entityCutout);

    public MeteorProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.6f;
    }

    @Override
    public void submit(@NonNull EntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector collector,
                       @NonNull CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.0f, state.boundingBoxHeight / 2, 0.0f);
        poseStack.rotateDegrees(Axis.XP, state.ageInTicks * SPIN_DEGREES_PER_TICK);
        poseStack.rotateDegrees(Axis.YP, state.ageInTicks * SPIN_DEGREES_PER_TICK * 0.7f);
        collector.submitModel(this.model, Unit.INSTANCE, poseStack, this.model.renderType(TEXTURE),
                FULL_BRIGHT, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    @Override
    public @NonNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
