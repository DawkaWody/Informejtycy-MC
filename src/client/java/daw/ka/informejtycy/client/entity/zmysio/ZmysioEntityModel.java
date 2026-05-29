package daw.ka.informejtycy.client.entity.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.math.MathHelper;

public class ZmysioEntityModel extends EntityModel<ZmysioEntityRenderState> {
    public static final EntityModelLayer ZMYSIO = new EntityModelLayer(InformejtycyRegistry.id("zmysio"), "main");

    private final ModelPart zmysio;
    private final ModelPart head;

    private final Animation spawnAnimation;
    private final Animation idleAnimation;
    private final Animation shootRightAnimation;
    private final Animation shootLeftAnimation;
    private final Animation spinAnimation;
    private final Animation gasAnimation;
    private final Animation deathAnimation;
    
    public ZmysioEntityModel(ModelPart root) {
        super(root);
        this.zmysio = root.getChild("zmysio");
        this.head = this.zmysio.getChild("glowa");

        spawnAnimation = ZmysioEntityAnim.SPAWN.createAnimation(root);
        idleAnimation = ZmysioEntityAnim.IDLE.createAnimation(root);
        shootRightAnimation = ZmysioEntityAnim.SHOOT_RIGHT.createAnimation(root);
        shootLeftAnimation = ZmysioEntityAnim.SHOOT_LEFT.createAnimation(root);
        spinAnimation = ZmysioEntityAnim.SPIN.createAnimation(root);
        gasAnimation = ZmysioEntityAnim.GAS.createAnimation(root);
        deathAnimation = ZmysioEntityAnim.DIE.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData geometry = modelData.getRoot();
        ModelPartData zmysio = geometry.addChild("zmysio", ModelPartBuilder.create().uv(-29, -10).cuboid(-13.0F, -14.0F, -6.0F, 26.0F, 14.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-16.0F, -28.0F, -8.0F, 32.0F, 14.0F, 17.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-6.0F, -52.0F, -6.0F, 12.0F, 13.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData boczekR_r1 = zmysio.addChild("boczekR_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-11.0F, -29.0F, 0.0F, 11.0F, 29.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -2.0F, -5.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData boczekL_r1 = zmysio.addChild("boczekL_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-11.0F, -29.0F, 0.0F, 11.0F, 29.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, 0.0F, -5.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData klata = zmysio.addChild("klata", ModelPartBuilder.create().uv(0, 0).cuboid(-20.0F, -46.0F, -11.0F, 40.0F, 18.0F, 23.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData sutR = klata.addChild("sutR", ModelPartBuilder.create().uv(0, 232).cuboid(7.0F, -3.0F, -11.0F, 2.0F, 2.0F, 22.0F, new Dilation(0.0F)), ModelTransform.origin(-22.0F, -31.0F, -3.0F));

        ModelPartData cycR = klata.addChild("cycR", ModelPartBuilder.create().uv(174, 0).cuboid(5.0F, -13.0F, -11.0F, 18.0F, 15.0F, 23.0F, new Dilation(0.0F)), ModelTransform.origin(-24.0F, -31.0F, -2.0F));

        ModelPartData cycL = klata.addChild("cycL", ModelPartBuilder.create().uv(174, 43).cuboid(1.0F, -13.0F, -11.0F, 18.0F, 15.0F, 23.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -31.0F, -2.0F));

        ModelPartData sutL = klata.addChild("sutL", ModelPartBuilder.create().uv(0, 232).cuboid(7.0F, -3.0F, -11.0F, 2.0F, 2.0F, 22.0F, new Dilation(0.0F)), ModelTransform.origin(6.0F, -31.0F, -3.0F));

        ModelPartData rekaL = zmysio.addChild("rekaL", ModelPartBuilder.create().uv(0, 0).cuboid(132.0F, -9.0F, -4.0F, 7.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(-77.0F, -31.0F, 0.0F));

        ModelPartData _2L = rekaL.addChild("2L", ModelPartBuilder.create().uv(0, 0).cuboid(-13.0F, -14.0F, -8.0F, 22.0F, 16.0F, 18.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-17.0F, -12.0F, -6.0F, 30.0F, 12.0F, 13.0F, new Dilation(0.0F)), ModelTransform.origin(117.0F, 0.0F, 0.0F));

        ModelPartData _1L = rekaL.addChild("_1L", ModelPartBuilder.create().uv(0, 0).cuboid(-13.0F, -12.0F, -7.0F, 22.0F, 14.0F, 15.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-17.0F, -11.0F, -6.0F, 30.0F, 11.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(158.0F, 0.0F, 0.0F));

        ModelPartData glowa = zmysio.addChild("glowa", ModelPartBuilder.create().uv(184, 218).cuboid(-9.0F, -14.0F, -9.0F, 18.0F, 20.0F, 18.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -58.0F, -1.0F));

        ModelPartData rekaR = zmysio.addChild("rekaR", ModelPartBuilder.create().uv(0, 0).cuboid(-62.0F, -40.0F, -4.0F, 7.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData _3R = rekaR.addChild("3R", ModelPartBuilder.create().uv(0, 0).cuboid(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-17.0F, -12.0F, -6.0F, 30.0F, 12.0F, 13.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-13.0F, -14.0F, -8.0F, 22.0F, 16.0F, 18.0F, new Dilation(0.0F)), ModelTransform.origin(-36.0F, -31.0F, 0.0F));

        ModelPartData _1R = rekaR.addChild("_1R", ModelPartBuilder.create().uv(0, 0).cuboid(-13.0F, -12.0F, -7.0F, 22.0F, 14.0F, 15.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-17.0F, -11.0F, -6.0F, 30.0F, 11.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(-77.0F, -31.0F, 0.0F));

        ModelPartData szesciopak = zmysio.addChild("szesciopak", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-10.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData szesciopak_r1 = szesciopak.addChild("szesciopak_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-13.0F, -14.0F, -6.0F, 26.0F, 14.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -1.0F, 0.0873F, 0.0F, 0.0F));

        ModelPartData miesien_DR_r1 = szesciopak.addChild("miesien_DR_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -7.0F, -1.0F, 7.0F, 7.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.0F, -7.0F, -1.0F, 7.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -3.0F, -8.0F, 0.0436F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 256, 256);

    }

    @Override
    public void setAngles(ZmysioEntityRenderState state) {
        super.setAngles(state);
        this.setHeadAngles(state.relativeHeadYaw, state.pitch);

        this.idleAnimation.apply(state.idleAnimationState, state.age, 1f);
        this.shootRightAnimation.apply(state.shootRightAnimationState, state.age, 1f);
        this.shootLeftAnimation.apply(state.shootLeftAnimationState, state.age, 1f);
        this.spinAnimation.apply(state.spinAnimationState, state.age, 1f);
        this.gasAnimation.apply(state.gasAnimationState, state.age, 1f);
        this.deathAnimation.apply(state.deathAnimationState, state.age, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}
