package daw.ka.informejtycy.client.entity.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ZmysioArmorFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<ZmysioEntityRenderState, ZmysioEntityModel> {
    private static final Identifier SKIN = InformejtycyRegistry.id("textures/entity/zmysio/zmysio_armor.png");
    private final ZmysioEntityModel model;

    public ZmysioArmorFeatureRenderer(FeatureRendererContext<ZmysioEntityRenderState, ZmysioEntityModel> context, ModelPart root) {
        super(context);
        this.model = new ZmysioEntityModel(root);
    }

    @Override
    protected boolean shouldRender(ZmysioEntityRenderState state) {
        return state.armored;
    }

    @Override
    protected float getEnergySwirlX(float partialAge) {
        return MathHelper.cos(partialAge * 0.02F) * 3.0F;
    }

    @Override
    protected Identifier getEnergySwirlTexture() {
        return SKIN;
    }

    @Override
    protected ZmysioEntityModel getEnergySwirlModel() {
        return model;
    }
}
