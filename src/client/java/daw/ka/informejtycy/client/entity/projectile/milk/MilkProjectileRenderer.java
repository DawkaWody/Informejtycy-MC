package daw.ka.informejtycy.client.entity.projectile.milk;

import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MilkProjectileRenderer extends EntityRenderer<MilkProjectileEntity, MilkProjectileRenderState> {

    public MilkProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public MilkProjectileRenderState createRenderState() {
        return new MilkProjectileRenderState();
    }
}

