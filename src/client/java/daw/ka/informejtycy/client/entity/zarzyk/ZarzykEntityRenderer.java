package daw.ka.informejtycy.client.entity.zarzyk;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.entity.custom.mob.ZarzykEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class ZarzykEntityRenderer extends AgeableMobRenderer<ZarzykEntity, ZarzykEntityRenderState, ZarzykEntityModel> {
    public ZarzykEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ZarzykEntityModel(context.bakeLayer(ZarzykEntityModel.ZARZYK)),
                new ZarzykEntityModel(context.bakeLayer(ZarzykEntityModel.ZARZYK_BABY)), 1f);
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull ZarzykEntityRenderState state) {
        return InformejtycyRegistry.id("textures/entity/zarzyk/zarzyk.png");
    }

    @Override
    public @NonNull ZarzykEntityRenderState createRenderState() {
        return new ZarzykEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull ZarzykEntity entity, @NonNull ZarzykEntityRenderState renderState, float f) {
        super.extractRenderState(entity, renderState, f);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.attackAnimationState.copyFrom(entity.attackAnimationState);
        renderState.walkAnimationState.copyFrom(entity.walkAnimationState);
    }
}
