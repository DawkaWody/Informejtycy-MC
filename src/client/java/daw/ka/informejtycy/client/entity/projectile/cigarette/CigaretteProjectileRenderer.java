package daw.ka.informejtycy.client.entity.projectile.cigarette;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.client.entity.alphawolf.AlphaWolfMesh;
import daw.ka.informejtycy.entity.custom.projectile.CigaretteProjectileEntity;
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

public class CigaretteProjectileRenderer extends EntityRenderer<CigaretteProjectileEntity, EntityRenderState> {
    public static final Identifier TEXTURE = InformejtycyRegistry.id("textures/entity/alpha_wolf/cigarette.png");
    private static final float SPIN_DEGREES_PER_TICK = 40.0f;

    private final Model.Simple model = new Model.Simple(AlphaWolfMesh.load("cigarette"), RenderTypes::entityCutout);

    public CigaretteProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.2f;
    }

    @Override
    public void submit(@NonNull EntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector collector,
                       @NonNull CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.0f, state.boundingBoxHeight / 2, 0.0f);
        poseStack.rotateDegrees(Axis.YP, state.ageInTicks * SPIN_DEGREES_PER_TICK);
        collector.submitModel(this.model, Unit.INSTANCE, poseStack, this.model.renderType(TEXTURE),
                state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    @Override
    public @NonNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
