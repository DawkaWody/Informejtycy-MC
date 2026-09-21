package daw.ka.informejtycy.client.entity.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.util.Mth;

public class ZmysioEntityModel extends EntityModel<ZmysioEntityRenderState> {
    public static final ModelLayerLocation ZMYSIO = new ModelLayerLocation(InformejtycyRegistry.id("zmysio"), "main");

    private final ModelPart zmysio;
    private final ModelPart head;

    private final KeyframeAnimation spawnAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation shootRightAnimation;
    private final KeyframeAnimation shootLeftAnimation;
    private final KeyframeAnimation spinAnimation;
    private final KeyframeAnimation gasAnimation;
    private final KeyframeAnimation deathAnimation;
    
    public ZmysioEntityModel(ModelPart root) {
        super(root);
        this.zmysio = root.getChild("zmysio");
        this.head = this.zmysio.getChild("glowa");

        spawnAnimation = ZmysioEntityAnim.SPAWN.bake(root);
        idleAnimation = ZmysioEntityAnim.IDLE.bake(root);
        shootRightAnimation = ZmysioEntityAnim.SHOOT_RIGHT.bake(root);
        shootLeftAnimation = ZmysioEntityAnim.SHOOT_LEFT.bake(root);
        spinAnimation = ZmysioEntityAnim.SPIN.bake(root);
        gasAnimation = ZmysioEntityAnim.GAS.bake(root);
        deathAnimation = ZmysioEntityAnim.DIE.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition geometry = modelData.getRoot();
        PartDefinition zmysio = geometry.addOrReplaceChild("zmysio", CubeListBuilder.create().texOffs(-29, -10).addBox(-13.0F, -14.0F, -6.0F, 26.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-16.0F, -28.0F, -8.0F, 32.0F, 14.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -52.0F, -6.0F, 12.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition boczekR_r1 = zmysio.addOrReplaceChild("boczekR_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -29.0F, 0.0F, 11.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, -5.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition boczekL_r1 = zmysio.addOrReplaceChild("boczekL_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -29.0F, 0.0F, 11.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.0F, -5.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition klata = zmysio.addOrReplaceChild("klata", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -46.0F, -11.0F, 40.0F, 18.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sutR = klata.addOrReplaceChild("sutR", CubeListBuilder.create().texOffs(0, 232).addBox(7.0F, -3.0F, -11.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(-22.0F, -31.0F, -3.0F));

        PartDefinition cycR = klata.addOrReplaceChild("cycR", CubeListBuilder.create().texOffs(174, 0).addBox(5.0F, -13.0F, -11.0F, 18.0F, 15.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, -31.0F, -2.0F));

        PartDefinition cycL = klata.addOrReplaceChild("cycL", CubeListBuilder.create().texOffs(174, 43).addBox(1.0F, -13.0F, -11.0F, 18.0F, 15.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -31.0F, -2.0F));

        PartDefinition sutL = klata.addOrReplaceChild("sutL", CubeListBuilder.create().texOffs(0, 232).addBox(7.0F, -3.0F, -11.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -31.0F, -3.0F));

        PartDefinition rekaL = zmysio.addOrReplaceChild("rekaL", CubeListBuilder.create().texOffs(0, 0).addBox(132.0F, -9.0F, -4.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-77.0F, -31.0F, 0.0F));

        PartDefinition _2L = rekaL.addOrReplaceChild("2L", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -14.0F, -8.0F, 22.0F, 16.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.0F, -12.0F, -6.0F, 30.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(117.0F, 0.0F, 0.0F));

        PartDefinition _1L = rekaL.addOrReplaceChild("_1L", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -12.0F, -7.0F, 22.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.0F, -11.0F, -6.0F, 30.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(158.0F, 0.0F, 0.0F));

        PartDefinition glowa = zmysio.addOrReplaceChild("glowa", CubeListBuilder.create().texOffs(184, 218).addBox(-9.0F, -14.0F, -9.0F, 18.0F, 20.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -58.0F, -1.0F));

        PartDefinition rekaR = zmysio.addOrReplaceChild("rekaR", CubeListBuilder.create().texOffs(0, 0).addBox(-62.0F, -40.0F, -4.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition _3R = rekaR.addOrReplaceChild("3R", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.0F, -12.0F, -6.0F, 30.0F, 12.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-13.0F, -14.0F, -8.0F, 22.0F, 16.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-36.0F, -31.0F, 0.0F));

        PartDefinition _1R = rekaR.addOrReplaceChild("_1R", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -12.0F, -7.0F, 22.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.0F, -11.0F, -6.0F, 30.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-20.0F, -10.0F, -5.0F, 36.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-77.0F, -31.0F, 0.0F));

        PartDefinition szesciopak = zmysio.addOrReplaceChild("szesciopak", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-10.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition szesciopak_r1 = szesciopak.addOrReplaceChild("szesciopak_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -14.0F, -6.0F, 26.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition miesien_DR_r1 = szesciopak.addOrReplaceChild("miesien_DR_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -1.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(5.0F, -7.0F, -1.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -3.0F, -8.0F, 0.0436F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);

    }

    @Override
    public void setupAnim(ZmysioEntityRenderState state) {
        super.setupAnim(state);
        this.setHeadAngles(state.yRot, state.xRot);

        this.spawnAnimation.apply(state.spawnAnimationState, state.ageInTicks, 1f);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
        this.shootRightAnimation.apply(state.shootRightAnimationState, state.ageInTicks, 1f);
        this.shootLeftAnimation.apply(state.shootLeftAnimationState, state.ageInTicks, 1f);
        this.spinAnimation.apply(state.spinAnimationState, state.ageInTicks, 1f);
        this.gasAnimation.apply(state.gasAnimationState, state.ageInTicks, 1f);
        this.deathAnimation.apply(state.deathAnimationState, state.ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0f, 30.0f);
        headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }
}
