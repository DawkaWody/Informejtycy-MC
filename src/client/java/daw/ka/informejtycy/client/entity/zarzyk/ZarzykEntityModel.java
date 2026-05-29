package daw.ka.informejtycy.client.entity.zarzyk;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.math.MathHelper;

public class ZarzykEntityModel extends EntityModel<ZarzykEntityRenderState> {
	public static final EntityModelLayer ZARZYK = new EntityModelLayer(InformejtycyRegistry.id("zarzyk"), "main");
	
	private final ModelPart zarzyk;
	private final ModelPart head;
	
	private final Animation idleAnimation;
	private final Animation attackAnimation;
	private final Animation walkAnimation;

	public ZarzykEntityModel(ModelPart root) {
        super(root);
        this.zarzyk = root.getChild("zarzyk");
		this.head = this.zarzyk.getChild("glowa");
		
		idleAnimation = ZarzykEntityAnim.IDLE.createAnimation(root);
		attackAnimation = ZarzykEntityAnim.ATTACK.createAnimation(root);
		walkAnimation = ZarzykEntityAnim.WALK.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData geometry = modelData.getRoot();

		ModelPartData zarzyk = geometry.addChild("zarzyk", ModelPartBuilder.create().uv(86, 94).cuboid(-13.0F, -66.0F, -6.0F, 26.0F, 14.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 46).cuboid(-6.0F, -104.0F, -6.0F, 12.0F, 13.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData brzuchetc = zarzyk.addChild("brzuchetc", ModelPartBuilder.create().uv(125, 21).cuboid(-11.0F, -26.0F, -3.0F, 28.0F, 14.0F, 17.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, -54.0F, -5.0F));

		ModelPartData boczekR_r1 = brzuchetc.addChild("boczekR_r1", ModelPartBuilder.create().uv(56, 0).cuboid(-9.0F, -29.0F, 0.0F, 7.0F, 29.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData boczekL_r1 = brzuchetc.addChild("boczekL_r1", ModelPartBuilder.create().uv(56, 0).cuboid(-9.0F, -29.0F, 0.0F, 7.0F, 29.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(17.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		ModelPartData rekaL = zarzyk.addChild("rekaL", ModelPartBuilder.create().uv(0, 150).cuboid(97.0F, -8.0F, -5.0F, 30.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 144).cuboid(100.0F, -10.0F, -6.0F, 24.0F, 9.0F, 13.0F, new Dilation(0.0F))
		.uv(77, 135).cuboid(104.0F, -12.0F, -8.0F, 16.0F, 13.0F, 18.0F, new Dilation(0.0F))
		.uv(0, 127).cuboid(128.0F, -8.0F, -5.0F, 30.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 114).cuboid(127.0F, -7.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 146).cuboid(131.0F, -9.0F, -6.0F, 24.0F, 8.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 140).cuboid(135.0F, -10.0F, -7.0F, 16.0F, 11.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(-74.0F, -124.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		ModelPartData nogaL = zarzyk.addChild("nogaL", ModelPartBuilder.create().uv(186, 100).cuboid(97.0F, -8.0F, -5.0F, 25.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(100.0F, -10.0F, -6.0F, 19.0F, 9.0F, 13.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(104.0F, -12.0F, -8.0F, 11.0F, 13.0F, 18.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(123.0F, -8.0F, -5.0F, 25.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(122.0F, -7.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(126.0F, -9.0F, -6.0F, 19.0F, 8.0F, 12.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(130.0F, -11.0F, -7.0F, 11.0F, 11.0F, 15.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(148.0F, -13.0F, -22.0F, 1.0F, 14.0F, 30.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -149.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData glowa = zarzyk.addChild("glowa", ModelPartBuilder.create().uv(184, 218).cuboid(-9.0F, -14.0F, -9.0F, 18.0F, 20.0F, 18.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -110.0F, -1.0F));

		ModelPartData rekaR = zarzyk.addChild("rekaR", ModelPartBuilder.create().uv(0, 150).cuboid(-50.0F, -39.0F, -5.0F, 30.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 144).cuboid(-47.0F, -41.0F, -6.0F, 24.0F, 9.0F, 13.0F, new Dilation(0.0F))
		.uv(77, 135).cuboid(-43.0F, -43.0F, -8.0F, 16.0F, 13.0F, 18.0F, new Dilation(0.0F))
		.uv(0, 114).cuboid(-51.0F, -38.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 140).cuboid(-74.0F, -41.0F, -7.0F, 16.0F, 11.0F, 15.0F, new Dilation(0.0F))
		.uv(0, 146).cuboid(-78.0F, -40.0F, -6.0F, 24.0F, 8.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 140).cuboid(-81.0F, -39.0F, -5.0F, 30.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(21.0F, -75.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		ModelPartData nogaR = zarzyk.addChild("nogaR", ModelPartBuilder.create().uv(186, 100).cuboid(-56.0F, -39.0F, -5.0F, 25.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-53.0F, -41.0F, -6.0F, 19.0F, 9.0F, 13.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-49.0F, -43.0F, -8.0F, 11.0F, 13.0F, 18.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-57.0F, -38.0F, -4.0F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-75.0F, -42.0F, -7.0F, 11.0F, 11.0F, 15.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-83.0F, -44.0F, -22.0F, 1.0F, 14.0F, 30.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-79.0F, -40.0F, -6.0F, 19.0F, 8.0F, 12.0F, new Dilation(0.0F))
		.uv(186, 100).cuboid(-82.0F, -39.0F, -5.0F, 25.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(29.0F, -83.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData szesciopak = zarzyk.addChild("szesciopak", ModelPartBuilder.create().uv(132, 210).cuboid(-9.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F))
		.uv(132, 210).cuboid(1.0F, -19.0F, -10.0F, 8.0F, 8.0F, 3.0F, new Dilation(0.0F))
		.uv(132, 210).cuboid(-10.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new Dilation(0.0F))
		.uv(132, 210).cuboid(1.0F, -27.0F, -10.0F, 9.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -52.0F, 0.0F));

		ModelPartData szesciopak_r1 = szesciopak.addChild("szesciopak?_r1", ModelPartBuilder.create().uv(134, 210).cuboid(-11.0F, -14.0F, -6.0F, 22.0F, 14.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -1.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData miesienDR_r1 = szesciopak.addChild("miesien-DR_r1", ModelPartBuilder.create().uv(133, 210).cuboid(-3.0F, -7.0F, -1.0F, 6.0F, 7.0F, 2.0F, new Dilation(0.0F))
		.uv(132, 210).cuboid(5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -3.0F, -8.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData klata = zarzyk.addChild("klata", ModelPartBuilder.create().uv(103, 16).cuboid(-18.0F, -46.0F, -11.0F, 36.0F, 18.0F, 23.0F, new Dilation(0.0F))
		.uv(176, 43).cuboid(1.0F, -44.0F, -13.0F, 15.0F, 15.0F, 23.0F, new Dilation(0.0F))
		.uv(175, 0).cuboid(-16.0F, -44.0F, -13.0F, 15.0F, 15.0F, 23.0F, new Dilation(0.0F))
		.uv(0, 232).cuboid(-12.0F, -34.0F, -14.0F, 2.0F, 2.0F, 22.0F, new Dilation(0.0F))
		.uv(0, 232).cuboid(10.0F, -34.0F, -14.0F, 2.0F, 2.0F, 22.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -52.0F, 0.0F));

		ModelPartData parowaContainer = zarzyk.addChild("parowaContainer", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -31.0F, -18.0F));

		ModelPartData parowa_r1 = parowaContainer.addChild("parowa_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 3.0F, 1.0036F, 0.0F, 0.0F));

		ModelPartData dorota_r1 = parowaContainer.addChild("dorota_r1", ModelPartBuilder.create().uv(1, 1).cuboid(-1.0F, -2.0F, 1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.0036F, 0.0F, 0.0F));

		ModelPartData knaga = zarzyk.addChild("knaga", ModelPartBuilder.create().uv(0, 18).cuboid(3.0F, -53.0F, -7.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 18).cuboid(-5.0F, -53.0F, -7.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void setAngles(ZarzykEntityRenderState state) {
		super.setAngles(state);
		this.setHeadAngles(state.relativeHeadYaw, state.pitch);

		this.idleAnimation.apply(state.idleAnimationState, state.age, 1f);
		this.attackAnimation.apply(state.attackAnimationState, state.age, 1f);
		this.walkAnimation.apply(state.walkAnimationState, state.age, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
}