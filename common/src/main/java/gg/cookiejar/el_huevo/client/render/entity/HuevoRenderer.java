package gg.cookiejar.el_huevo.client.render.entity;

import gg.cookiejar.el_huevo.client.render.entity.layers.HuevoClothingLayer;
import gg.cookiejar.el_huevo.common.entity.Huevo;
import gg.cookiejar.el_huevo.core.ElHuevo;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * @author Steven
 */
@Environment(EnvType.CLIENT)
public class HuevoRenderer extends AnimatedEntityRenderer<Huevo> {
    private static final ResourceLocation[] IDLE_ANIMATION = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.idle")};
    private static final ResourceLocation[] WALK_ANIMATION = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.walk")};
    private static final ResourceLocation[] DANCE_ANIMATION = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.dance")};
    private static final ResourceLocation HUEVO_LOCATION = new ResourceLocation(ElHuevo.MOD_ID, "huevo");

    private boolean isMoving = true;

    public HuevoRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(ElHuevo.MOD_ID, "huevo"), 0.4F);
        this.addLayer(new HuevoClothingLayer(this));
    }

    @Override
    public ResourceLocation[] getAnimations(Huevo entity) {
        if (entity.isInSittingPose())
            //TODO add sitting animation like 1.0.0
            return IDLE_ANIMATION;
        else if (entity.isHuevoDancing() && entity.isInSittingPose())
            return DANCE_ANIMATION;
        else if (isMoving)
            //todo: make this easier check out isInSittingPose parrot
            return WALK_ANIMATION;
        else if (entity.isNoAnimationPlaying())
            return IDLE_ANIMATION;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(Huevo entity) {
        return HUEVO_LOCATION;
    }

    @Override
    public void render(Huevo entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.shouldRiderSit());

        float limbSwingAmount = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            limbSwingAmount = Mth.lerp(partialTicks, entity.animationSpeedOld, entity.animationSpeed);

            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }
        this.isMoving = !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
}