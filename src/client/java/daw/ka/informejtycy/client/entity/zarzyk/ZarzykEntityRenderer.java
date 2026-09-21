package daw.ka.informejtycy.client.entity.zarzyk;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.mob.ZarzykEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class ZarzykEntityRenderer extends MobRenderer<ZarzykEntity, ZarzykEntityRenderState, ZarzykEntityModel> {
    public ZarzykEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ZarzykEntityModel(context.bakeLayer(ZarzykEntityModel.ZARZYK)), 1f);
    }

    @Override
    public Identifier getTextureLocation(ZarzykEntityRenderState state) {
        return InformejtycyRegistry.id("textures/entity/zarzyk/zarzyk.png");
    }

    @Override
    public ZarzykEntityRenderState createRenderState() {
        return new ZarzykEntityRenderState();
    }

    @Override
    public void extractRenderState(ZarzykEntity entity, ZarzykEntityRenderState renderState, float f) {
        super.extractRenderState(entity, renderState, f);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.attackAnimationState.copyFrom(entity.attackAnimationState);
        renderState.walkAnimationState.copyFrom(entity.walkAnimationState);
    }
}
