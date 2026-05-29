package daw.ka.informejtycy.client.entity.projectile.milk;

import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class MilkProjectileRenderer extends EntityRenderer<MilkProjectileEntity, MilkProjectileRenderState> {

    public MilkProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public MilkProjectileRenderState createRenderState() {
        return new MilkProjectileRenderState();
    }
}

