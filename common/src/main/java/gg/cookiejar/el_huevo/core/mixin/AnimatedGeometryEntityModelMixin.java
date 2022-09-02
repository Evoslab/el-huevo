package gg.cookiejar.el_huevo.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedGeometryEntityModel;
import gg.moonflower.pollen.pinwheel.api.client.geometry.GeometryModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AnimatedGeometryEntityModel.class)
public abstract class AnimatedGeometryEntityModelMixin implements ArmedModel {
    @Shadow public abstract GeometryModel getModel();

    private ModelPart getArm(HumanoidArm arg) {
        return arg == HumanoidArm.LEFT ? this.getModel().getModelPart("leftarm").get() : this.getModel().getModelPart("righttarm").get();
    }

    @Override
    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
    }
}