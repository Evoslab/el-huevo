package gg.cookiejar.el_huevo.client.render.entity;

import gg.cookiejar.el_huevo.client.render.entity.layers.HuevoClothingLayer;
import gg.cookiejar.el_huevo.common.entity.Huevo;
import gg.cookiejar.el_huevo.core.ElHuevo;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
    public static final ResourceLocation HUEVO_LOCATION = new ResourceLocation(ElHuevo.MOD_ID, "huevo");

    private boolean isMoving = true;

    public HuevoRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(ElHuevo.MOD_ID, "huevo"), 0.4F);
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
    protected void setupRotations(Huevo entity, PoseStack matrixStack, float ticksExisted, float rotY, float partialTicks) {
        ResourceLocation location = switch (entity.getClothingColor()) {
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
            case WHITE -> new ResourceLocation(ElHuevo.MOD_ID, "huevo_white");
        };

        if (entity.isTame() && !entity.isInvisible()) {
            this.model.setTexture(location);
        }
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