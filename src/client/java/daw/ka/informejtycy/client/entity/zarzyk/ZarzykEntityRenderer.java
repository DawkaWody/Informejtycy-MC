package daw.ka.informejtycy.client.entity.zarzyk;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.mob.ZarzykEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ZarzykEntityRenderer extends MobEntityRenderer<ZarzykEntity, ZarzykEntityRenderState, ZarzykEntityModel> {
    public ZarzykEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ZarzykEntityModel(context.getPart(ZarzykEntityModel.ZARZYK)), 1f);
    }

    @Override
    public Identifier getTexture(ZarzykEntityRenderState state) {
        return InformejtycyRegistry.id("textures/entity/zarzyk/zarzyk.png");
    }

    @Override
    public ZarzykEntityRenderState createRenderState() {
        return new ZarzykEntityRenderState();
    }

    @Override
    public void updateRenderState(ZarzykEntity entity, ZarzykEntityRenderState renderState, float f) {
        super.updateRenderState(entity, renderState, f);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.attackAnimationState.copyFrom(entity.attackAnimationState);
        renderState.walkAnimationState.copyFrom(entity.walkAnimationState);
    }
}
