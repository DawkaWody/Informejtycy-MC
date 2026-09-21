package daw.ka.informejtycy.client.entity.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.boss.ZmysioEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class ZmysioEntityRenderer extends MobRenderer<ZmysioEntity, ZmysioEntityRenderState, ZmysioEntityModel> {
    public ZmysioEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ZmysioEntityModel(context.bakeLayer(ZmysioEntityModel.ZMYSIO)), 1.5f);
        this.addLayer(new ZmysioArmorFeatureRenderer(this, context.bakeLayer(ZmysioEntityModel.ZMYSIO)));
    }

    @Override
    public Identifier getTextureLocation(ZmysioEntityRenderState state) {
        return InformejtycyRegistry.id("textures/entity/zmysio/zmysio.png");
    }

    @Override
    public ZmysioEntityRenderState createRenderState() {
        return new ZmysioEntityRenderState();
    }

    @Override
    public void extractRenderState(ZmysioEntity entity, ZmysioEntityRenderState renderState, float f) {
        super.extractRenderState(entity, renderState, f);
        renderState.spawnAnimationState.copyFrom(entity.spawnAnimationState);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.shootRightAnimationState.copyFrom(entity.shootRightAnimationState);
        renderState.shootLeftAnimationState.copyFrom(entity.shootLeftAnimationState);
        renderState.spinAnimationState.copyFrom(entity.spinAnimationState);
        renderState.gasAnimationState.copyFrom(entity.gasAnimationState);
        renderState.deathAnimationState.copyFrom(entity.deathAnimationState);
        renderState.armored = entity.isArmored();
    }
}
