package gg.cookiejar.el_huevo.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import gg.cookiejar.el_huevo.common.entity.Huevo;
import gg.cookiejar.el_huevo.core.ElHuevo;
import gg.cookiejar.el_huevo.core.util.CompactUtil;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedGeometryEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
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
    private static final ResourceLocation[] SIT_ANIMATION = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.sit")};
    public static final ResourceLocation HUEVO_LOCATION = new ResourceLocation(ElHuevo.MOD_ID, "huevo");

    private boolean isMoving = true;

    public HuevoRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(ElHuevo.MOD_ID, "huevo"), 0.4F);
        this.addLayer(new ItemInHandLayer(this));
    }

    @Override
    public ResourceLocation[] getAnimations(Huevo entity) {
        if (entity.isDancing() && entity.isInSittingPose())
            return DANCE_ANIMATION;
        if (!entity.isDancing() & entity.isInSittingPose())
            return SIT_ANIMATION;
        else if (isMoving)
            return WALK_ANIMATION;
        else if (entity.isNoAnimationPlaying())
            return IDLE_ANIMATION;
//        else if (CompactUtil.getKnife() != null && entity.isHolding(CompactUtil.getKnife()))
//            return IDLE_ANIMATION;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(Huevo entity) {
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
            return location;
        }
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

    @Override
    public void setupRotations(Huevo huevo, PoseStack poseStack, float f, float g, float h) {
        super.setupRotations(huevo, poseStack, f, g, h);
        if (huevo.rollCounter > 0) {
            int i = huevo.rollCounter;
            int j = i + 1;
            float l = huevo.isBaby() ? 0.3F : 0.8F;
            float o;
            float m;
            float n;
            if (i < 8) {
                m = (float)(90 * i) / 7.0F;
                n = (float)(90 * j) / 7.0F;
                o = this.getAngle(m, n, j, h, 8.0F);
                poseStack.translate(0.0D, (l + 0.2F) * (o / 90.0F), 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(-o));
            } else {
                float p;
                if (i < 16) {
                    m = ((float)i - 8.0F) / 7.0F;
                    n = 90.0F + 90.0F * m;
                    p = 90.0F + 90.0F * ((float)j - 8.0F) / 7.0F;
                    o = this.getAngle(n, p, j, h, 16.0F);
                    poseStack.translate(0.0D, l + 0.2F + (l - 0.2F) * (o - 90.0F) / 90.0F, 0.0D);
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(-o));
                } else if ((float)i < 24.0F) {
                    m = ((float)i - 16.0F) / 7.0F;
                    n = 180.0F + 90.0F * m;
                    p = 180.0F + 90.0F * ((float)j - 16.0F) / 7.0F;
                    o = this.getAngle(n, p, j, h, 24.0F);
                    poseStack.translate(0.0D, l + l * (270.0F - o) / 90.0F, 0.0D);
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(-o));
                } else if (i < 32) {
                    m = ((float)i - 24.0F) / 7.0F;
                    n = 270.0F + 90.0F * m;
                    p = 270.0F + 90.0F * ((float)j - 24.0F) / 7.0F;
                    o = this.getAngle(n, p, j, h, 32.0F);
                    poseStack.translate(0.0D, l * ((360.0F - o) / 90.0F), 0.0D);
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(-o));
                }
            }
        }
    }

    private float getAngle(float f, float g, int i, float h, float j) {
        return (float)i < j ? Mth.lerp(h, f, g) : f;
    }


}