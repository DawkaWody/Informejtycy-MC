package daw.ka.informejtycy.client.entity.projectile.rock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.client.entity.alphawolf.AlphaWolfMesh;
import daw.ka.informejtycy.entity.custom.projectile.RockProjectileEntity;
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

public class RockProjectileRenderer extends EntityRenderer<RockProjectileEntity, EntityRenderState> {
    public static final Identifier TEXTURE = InformejtycyRegistry.id("textures/entity/alpha_wolf/rock.png");
    private static final float SPIN_DEGREES_PER_TICK = 18.0f;

    private final Model.Simple model = new Model.Simple(AlphaWolfMesh.load("rock"), RenderTypes::entityCutout);

    public RockProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.6f;
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.0f, state.boundingBoxHeight / 2, 0.0f);
        poseStack.rotateDegrees(Axis.XP, state.ageInTicks * SPIN_DEGREES_PER_TICK);
        poseStack.rotateDegrees(Axis.ZP, state.ageInTicks * SPIN_DEGREES_PER_TICK * 0.6f);
        collector.submitModel(this.model, Unit.INSTANCE, poseStack, this.model.renderType(TEXTURE),
                state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
