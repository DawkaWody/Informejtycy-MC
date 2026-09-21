package daw.ka.informejtycy.client.entity.zarzyk;

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

public class ZarzykEntityModel extends EntityModel<ZarzykEntityRenderState> {
	public static final ModelLayerLocation ZARZYK = new ModelLayerLocation(InformejtycyRegistry.id("zarzyk"), "main");
	
	private final ModelPart zarzyk;
	private final ModelPart head;
	
	private final KeyframeAnimation idleAnimation;
	private final KeyframeAnimation attackAnimation;
	private final KeyframeAnimation walkAnimation;

	public ZarzykEntityModel(ModelPart root) {
        super(root);
        this.zarzyk = root.getChild("zarzyk");
		this.head = this.zarzyk.getChild("glowa");
		
		idleAnimation = ZarzykEntityAnim.IDLE.bake(root);
		attackAnimation = ZarzykEntityAnim.ATTACK.bake(root);
		walkAnimation = ZarzykEntityAnim.WALK.bake(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition geometry = modelData.getRoot();

		PartDefinition zarzyk = geometry.addOrReplaceChild("zarzyk", CubeListBuilder.create().texOffs(86, 94).addBox(-13.0F, -66.0F, -6.0F, 26.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 46).addBox(-6.0F, -104.0F, -6.0F, 12.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition brzuchetc = zarzyk.addOrReplaceChild("brzuchetc", CubeListBuilder.create().texOffs(125, 21).addBox(-11.0F, -26.0F, -3.0F, 28.0F, 14.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -54.0F, -5.0F));

		PartDefinition boczekR_r1 = brzuchetc.addOrReplaceChild("boczekR_r1", CubeListBuilder.create().texOffs(56, 0).addBox(-9.0F, -29.0F, 0.0F, 7.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition boczekL_r1 = brzuchetc.addOrReplaceChild("boczekL_r1", CubeListBuilder.create().texOffs(56, 0).addBox(-9.0F, -29.0F, 0.0F, 7.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition rekaL = zarzyk.addOrReplaceChild("rekaL", CubeListBuilder.create().texOffs(0, 150).addBox(97.0F, -8.0F, -5.0F, 30.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 144).addBox(100.0F, -10.0F, -6.0F, 24.0F, 9.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(77, 135).addBox(104.0F, -12.0F, -8.0F, 16.0F, 13.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 127).addBox(128.0F, -8.0F, -5.0F, 30.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 114).addBox(127.0F, -7.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 146).addBox(131.0F, -9.0F, -6.0F, 24.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 140).addBox(135.0F, -10.0F, -7.0F, 16.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-74.0F, -124.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition nogaL = zarzyk.addOrReplaceChild("nogaL", CubeListBuilder.create().texOffs(186, 100).addBox(97.0F, -8.0F, -5.0F, 25.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(100.0F, -10.0F, -6.0F, 19.0F, 9.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(104.0F, -12.0F, -8.0F, 11.0F, 13.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(123.0F, -8.0F, -5.0F, 25.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(122.0F, -7.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(126.0F, -9.0F, -6.0F, 19.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(130.0F, -11.0F, -7.0F, 11.0F, 11.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(148.0F, -13.0F, -22.0F, 1.0F, 14.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -149.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition glowa = zarzyk.addOrReplaceChild("glowa", CubeListBuilder.create().texOffs(184, 218).addBox(-9.0F, -14.0F, -9.0F, 18.0F, 20.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -110.0F, -1.0F));

		PartDefinition rekaR = zarzyk.addOrReplaceChild("rekaR", CubeListBuilder.create().texOffs(0, 150).addBox(-50.0F, -39.0F, -5.0F, 30.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 144).addBox(-47.0F, -41.0F, -6.0F, 24.0F, 9.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(77, 135).addBox(-43.0F, -43.0F, -8.0F, 16.0F, 13.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 114).addBox(-51.0F, -38.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 140).addBox(-74.0F, -41.0F, -7.0F, 16.0F, 11.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(0, 146).addBox(-78.0F, -40.0F, -6.0F, 24.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 140).addBox(-81.0F, -39.0F, -5.0F, 30.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0F, -75.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition nogaR = zarzyk.addOrReplaceChild("nogaR", CubeListBuilder.create().texOffs(186, 100).addBox(-56.0F, -39.0F, -5.0F, 25.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-53.0F, -41.0F, -6.0F, 19.0F, 9.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-49.0F, -43.0F, -8.0F, 11.0F, 13.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-57.0F, -38.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-75.0F, -42.0F, -7.0F, 11.0F, 11.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-83.0F, -44.0F, -22.0F, 1.0F, 14.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-79.0F, -40.0F, -6.0F, 19.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(186, 100).addBox(-82.0F, -39.0F, -5.0F, 25.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(29.0F, -83.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition szesciopak = zarzyk.addOrReplaceChild("szesciopak", CubeListBuilder.create().texOffs(132, 210).addBox(-9.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(132, 210).addBox(1.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(132, 210).addBox(-10.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(132, 210).addBox(1.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -52.0F, 0.0F));

		PartDefinition szesciopak_r1 = szesciopak.addOrReplaceChild("szesciopak?_r1", CubeListBuilder.create().texOffs(134, 210).addBox(-11.0F, -14.0F, -6.0F, 22.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition miesienDR_r1 = szesciopak.addOrReplaceChild("miesien-DR_r1", CubeListBuilder.create().texOffs(133, 210).addBox(-3.0F, -7.0F, -1.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(132, 210).addBox(5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -3.0F, -8.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition klata = zarzyk.addOrReplaceChild("klata", CubeListBuilder.create().texOffs(103, 16).addBox(-18.0F, -46.0F, -11.0F, 36.0F, 18.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(176, 43).addBox(1.0F, -44.0F, -13.0F, 15.0F, 15.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(175, 0).addBox(-16.0F, -44.0F, -13.0F, 15.0F, 15.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 232).addBox(-12.0F, -34.0F, -14.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 232).addBox(10.0F, -34.0F, -14.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -52.0F, 0.0F));

		PartDefinition parowaContainer = zarzyk.addOrReplaceChild("parowaContainer", CubeListBuilder.create(), PartPose.offset(0.0F, -31.0F, -18.0F));

		PartDefinition parowa_r1 = parowaContainer.addOrReplaceChild("parowa_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 3.0F, 1.0036F, 0.0F, 0.0F));

		PartDefinition dorota_r1 = parowaContainer.addOrReplaceChild("dorota_r1", CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, -2.0F, 1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0036F, 0.0F, 0.0F));

		PartDefinition knaga = zarzyk.addOrReplaceChild("knaga", CubeListBuilder.create().texOffs(0, 18).addBox(3.0F, -53.0F, -7.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-5.0F, -53.0F, -7.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(modelData, 256, 256);
	}

	@Override
	public void setupAnim(ZarzykEntityRenderState state) {
		super.setupAnim(state);
		this.setHeadAngles(state.yRot, state.xRot);

		this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
		this.attackAnimation.apply(state.attackAnimationState, state.ageInTicks, 1f);
		this.walkAnimation.apply(state.walkAnimationState, state.ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = Mth.clamp(headYaw, -30.0f, 30.0f);
		headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);

		this.head.yRot = headYaw * 0.017453292F;
		this.head.xRot = headPitch * 0.017453292F;
	}
}