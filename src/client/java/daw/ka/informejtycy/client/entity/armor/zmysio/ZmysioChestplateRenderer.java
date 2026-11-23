package daw.ka.informejtycy.client.entity.armor.zmysio;

import com.mojang.blaze3d.vertex.PoseStack;
import daw.ka.informejtycy.InformejtycyRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ZmysioChestplateRenderer implements ArmorRenderer {
    private static final Identifier TEXTURE = InformejtycyRegistry.id("textures/entity/zmysio/zmysio.png");
    private final ZmysioChestplateModel model;

    public ZmysioChestplateRenderer(EntityRendererProvider.Context context) {
        this.model = new ZmysioChestplateModel(context.bakeLayer(ZmysioChestplateModel.ZMYSIO_CHESTPLATE));
    }

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState state,
                       EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        if (slot != EquipmentSlot.CHEST) return;

        RenderType renderType = stack.hasFoil() ? RenderTypes.armorCutoutNoCullGlint(TEXTURE) : RenderTypes.armorCutoutNoCull(TEXTURE);
        ArmorRenderer.submitTransformCopyingModel(contextModel, state, model, state, false, submitNodeCollector,
                poseStack, renderType, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
    }
}
