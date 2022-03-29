package gg.cookiejar.el_huevo.client.render.entity.layers;

import gg.cookiejar.el_huevo.common.entity.Huevo;
import gg.cookiejar.el_huevo.core.ElHuevo;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedGeometryEntityModel;
import gg.moonflower.pollen.pinwheel.api.client.geometry.GeometryModelRenderer;
import gg.moonflower.pollen.pinwheel.api.client.texture.GeometryTextureManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Steven
 */
@Environment(EnvType.CLIENT)
public class HuevoClothingLayer extends RenderLayer<Huevo, AnimatedGeometryEntityModel<Huevo>> {
    public HuevoClothingLayer(RenderLayerParent<Huevo, AnimatedGeometryEntityModel<Huevo>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, Huevo livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation location = switch (livingEntity.getClothingColor()) {
            case RED -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_red");
            case BLUE -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_blue");
            case CYAN -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_cyan");
            case GRAY -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_gray");
            case LIME -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_lime");
            case PINK -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_pink");
            case BLACK -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_black");
            case BROWN -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_brown");
            case GREEN -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_green");
            case ORANGE -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_orange");
            case PURPLE -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_purple");
            case YELLOW -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_yellow");
            case MAGENTA -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_magenta");
            case LIGHT_BLUE -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_light_blue");
            case LIGHT_GRAY -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_light_gray");
            default -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_white");
        };

        if (livingEntity.isTame() && !livingEntity.isInvisible()) {
            GeometryModelRenderer.render(this.getParentModel().getModel(), location, buffer, matrixStack, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0F);
        }
    }
}