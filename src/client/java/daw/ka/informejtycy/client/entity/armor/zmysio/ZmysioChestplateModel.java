package daw.ka.informejtycy.client.entity.armor.zmysio;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class ZmysioChestplateModel extends HumanoidModel<HumanoidRenderState> {
    public static final ModelLayerLocation ZMYSIO_CHESTPLATE = new ModelLayerLocation(InformejtycyRegistry.id("zmysio_chestplate"), "main");

    private static final CubeDeformation ARMOR = new CubeDeformation(1.0F);
    private static final int SKIN_U = 40;
    private static final int SKIN_V = 80;
    private static final int SUT_U = 0;
    private static final int SUT_V = 232;

    public ZmysioChestplateModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition geometry = modelData.getRoot();

        PartDefinition head = geometry.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        geometry.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        geometry.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition body = geometry.addOrReplaceChild("body", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, ARMOR), PartPose.ZERO);

        // Broader upper chest sticking out past the torso
        body.addOrReplaceChild("klata", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(1.5F, 1.0F, 1.5F)), PartPose.ZERO);

        body.addOrReplaceChild("cycR", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-5.0F, 0.25F, -4.75F, 4.75F, 4.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.ZERO);
        body.addOrReplaceChild("cycL", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(0.25F, 0.25F, -4.75F, 4.75F, 4.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.ZERO);
        body.addOrReplaceChild("sutR", CubeListBuilder.create().texOffs(SUT_U, SUT_V).addBox(-3.125F, 2.25F, -5.0F, 1.0F, 1.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.ZERO);
        body.addOrReplaceChild("sutL", CubeListBuilder.create().texOffs(SUT_U, SUT_V).addBox(2.125F, 2.25F, -5.0F, 1.0F, 1.0F, 0.25F, new CubeDeformation(0.0F)), PartPose.ZERO);

        body.addOrReplaceChild("szesciopak", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-2.25F, 5.25F, -3.5F, 2.0F, 1.75F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(SKIN_U, SKIN_V).addBox(0.25F, 5.25F, -3.5F, 2.0F, 1.75F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(SKIN_U, SKIN_V).addBox(-2.25F, 7.25F, -3.5F, 2.0F, 1.75F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(SKIN_U, SKIN_V).addBox(0.25F, 7.25F, -3.5F, 2.0F, 1.75F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(SKIN_U, SKIN_V).addBox(-2.25F, 9.25F, -3.5F, 2.0F, 1.75F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(SKIN_U, SKIN_V).addBox(0.25F, 9.25F, -3.5F, 2.0F, 1.75F, 0.5F, new CubeDeformation(0.0F)), PartPose.ZERO);

        PartDefinition rekaR = geometry.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, ARMOR), PartPose.offset(-5.0F, 2.0F, 0.0F));
        rekaR.addOrReplaceChild("barkR", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-4.5F, -3.5F, -3.25F, 5.5F, 4.0F, 6.5F, new CubeDeformation(0.0F)), PartPose.ZERO);
        rekaR.addOrReplaceChild("bicekR", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-3.0F, 1.5F, -3.75F, 3.5F, 3.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        PartDefinition rekaL = geometry.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, ARMOR), PartPose.offset(5.0F, 2.0F, 0.0F));
        rekaL.addOrReplaceChild("barkL", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-1.0F, -3.5F, -3.25F, 5.5F, 4.0F, 6.5F, new CubeDeformation(0.0F)), PartPose.ZERO);
        rekaL.addOrReplaceChild("bicekL", CubeListBuilder.create().texOffs(SKIN_U, SKIN_V).addBox(-0.5F, 1.5F, -3.75F, 3.5F, 3.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(modelData, 256, 256);
    }
}
