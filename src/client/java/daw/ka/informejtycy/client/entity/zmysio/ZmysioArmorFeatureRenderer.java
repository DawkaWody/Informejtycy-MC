package daw.ka.informejtycy.client.entity.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class ZmysioArmorFeatureRenderer extends EnergySwirlLayer<ZmysioEntityRenderState, ZmysioEntityModel> {
    private static final Identifier SKIN = InformejtycyRegistry.id("textures/entity/zmysio/zmysio_armor.png");
    private final ZmysioEntityModel model;

    public ZmysioArmorFeatureRenderer(RenderLayerParent<ZmysioEntityRenderState, ZmysioEntityModel> context, ModelPart root) {
        super(context);
        this.model = new ZmysioEntityModel(root);
    }

    @Override
    protected boolean isPowered(ZmysioEntityRenderState state) {
        return state.armored;
    }

    @Override
    protected float xOffset(float partialAge) {
        return Mth.cos(partialAge * 0.02F) * 3.0F;
    }

    @Override
    protected Identifier getTextureLocation() {
        return SKIN;
    }

    @Override
    protected ZmysioEntityModel model() {
        return model;
    }
}
