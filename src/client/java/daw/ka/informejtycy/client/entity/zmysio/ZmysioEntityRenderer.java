package daw.ka.informejtycy.client.entity.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.boss.ZmysioEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ZmysioEntityRenderer extends MobEntityRenderer<ZmysioEntity, ZmysioEntityRenderState, ZmysioEntityModel> {
    public ZmysioEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ZmysioEntityModel(context.getPart(ZmysioEntityModel.ZMYSIO)), 1.5f);
        this.addFeature(new ZmysioArmorFeatureRenderer(this, context.getPart(ZmysioEntityModel.ZMYSIO)));
    }

    @Override
    public Identifier getTexture(ZmysioEntityRenderState state) {
        return InformejtycyRegistry.id("textures/entity/zmysio/zmysio.png");
    }

    @Override
    public ZmysioEntityRenderState createRenderState() {
        return new ZmysioEntityRenderState();
    }

    @Override
    public void updateRenderState(ZmysioEntity entity, ZmysioEntityRenderState renderState, float f) {
        super.updateRenderState(entity, renderState, f);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.shootRightAnimationState.copyFrom(entity.shootRightAnimationState);
        renderState.shootLeftAnimationState.copyFrom(entity.shootLeftAnimationState);
        renderState.spinAnimationState.copyFrom(entity.spinAnimationState);
        renderState.gasAnimationState.copyFrom(entity.gasAnimationState);
        renderState.deathAnimationState.copyFrom(entity.deathAnimationState);
        renderState.armored = entity.isArmored();
    }
}
