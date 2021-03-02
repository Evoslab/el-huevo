// Made with Blockbench 3.7.4
// Exported for Minecraft Fabric version 1.16.4
// Paste this class into your mod and generate all required imports

package com.github.Evoslab.elhuevo.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.MathHelper;

public class ElHuevoEntityModel<E extends TameableEntity> extends EntityModel<ElHuevoEntity> {
    private ModelPart leftleg;
    private ModelPart rightleg;
    private ModelPart bodyhead;
    private ModelPart tail;
    private ModelPart bb_main;
    private ModelPart cube_r2;
    private ModelPart cube_r1;

    public ElHuevoEntityModel() {
        textureWidth = 32;
        textureHeight = 32;
        leftleg = new ModelPart(this);
        leftleg.setPivot(-1.5F, 22.5F, 0.5F);
        leftleg.setTextureOffset(22, 6).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        rightleg = new ModelPart(this);
        rightleg.setPivot(1.5F, 22.5F, 0.5F);
        rightleg.setTextureOffset(22, 9).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        bodyhead = new ModelPart(this);
        bodyhead.setPivot(0.0F, 18.4F, -0.2F);
        bodyhead.setTextureOffset(26, 3).addCuboid(2.0F, -4.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bodyhead.setTextureOffset(26, 0).addCuboid(-4.0F, -4.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bodyhead.setTextureOffset(0, 13).addCuboid(-2.0F, -0.4F, -2.8F, 4.0F, 2.0F, 1.0F, 0.0F, false);
        bodyhead.setTextureOffset(0, 0).addCuboid(-3.0F, -3.4F, -1.8F, 6.0F, 8.0F, 5.0F, 0.0F, false);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, 3.6F, 2.7F);
        bodyhead.addChild(tail);
        tail.setTextureOffset(17, 0).addCuboid(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 18).addCuboid(-3.0F, -9.0F, -2.0F, 6.0F, 8.0F, 5.0F, 0.1F, false);
    }

    @Override
    public void setAngles(ElHuevoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftleg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.rightleg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
    }
    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        leftleg.render(matrixStack, buffer, packedLight, packedOverlay);
        rightleg.render(matrixStack, buffer, packedLight, packedOverlay);
        bodyhead.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    @Override
    public void animateModel(ElHuevoEntity elHuevoEntity, float limbAngle, float limbDistance, float tickDelta) {
            if (elHuevoEntity.isInSittingPose()) {
                this.leftleg = new ModelPart(this);
                this.leftleg.setPivot(-1.5F, 22.5F, 0.5F);

                this.cube_r1 = new ModelPart(this);
                this.cube_r1.setPivot(0.0F, 0.0F, -3.0F);
                this.leftleg.addChild(cube_r1);
                this.setRotationAngle(cube_r1, -1.5708F, 0.0F, 0.0F);
                this.cube_r1.setTextureOffset(22, 6).addCuboid(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);

                this.rightleg = new ModelPart(this);
                this.rightleg.setPivot(1.5F, 22.5F, 0.5F);


                this.cube_r2 = new ModelPart(this);
                this.cube_r2.setPivot(0.0F, 0.0F, -3.0F);
                this.rightleg.addChild(cube_r2);
                this.setRotationAngle(cube_r2, -1.5708F, 0.0F, 0.0F);
                this.cube_r2.setTextureOffset(22, 9).addCuboid(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);

                this.bodyhead = new ModelPart(this);
                this.bodyhead.setPivot(0.0F, 18.4F, -0.2F);
                this.bodyhead.setTextureOffset(26, 3).addCuboid(2.0F, -3.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(26, 0).addCuboid(-4.0F, -3.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(0, 13).addCuboid(-2.0F, 0.6F, -2.8F, 4.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(0, 0).addCuboid(-3.0F, -2.4F, -1.8F, 6.0F, 8.0F, 5.0F, 0.0F, false);

                this.tail = new ModelPart(this);
                this.tail.setPivot(0.0F, 3.6F, 2.7F);
                this.bodyhead.addChild(tail);
                this.tail.setTextureOffset(17, 0).addCuboid(-1.0F, 0.0F, 0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

                this.bb_main = new ModelPart(this);
                this.bb_main.setPivot(0.0F, 24.0F, 0.0F);
                this.bb_main.setTextureOffset(0, 18).addCuboid(-3.0F, -8.0F, -2.0F, 6.0F, 8.0F, 5.0F, 0.1F, false);
                }
            if (elHuevoEntity.getSongPlaying()) {
                this.leftleg = new ModelPart(this);
                this.leftleg.setPivot(-1.5F, 22.5F, 0.5F);
                this.leftleg.setTextureOffset(22, 6).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

                this.rightleg = new ModelPart(this);
                this.rightleg.setPivot(1.5F, 22.5F, 0.5F);
                this.rightleg.setTextureOffset(22, 9).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

                this.bodyhead = new ModelPart(this);
                this.bodyhead.setPivot(0.0F, 18.4F, -0.2F);
                this.bodyhead.setTextureOffset(0, 13).addCuboid(-2.0F, -0.4F, -2.8F, 4.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(0, 0).addCuboid(-3.0F, -3.4F, -1.8F, 6.0F, 8.0F, 5.0F, 0.0F, false);

                this.cube_r1 = new ModelPart(this);
                this.cube_r1.setPivot(-4.0F, -3.4F, 0.2F);
                this.bodyhead.addChild(cube_r1);
                this.setRotationAngle(cube_r1, 0.0F, 0.0F, -0.0873F);
                this.cube_r1.setTextureOffset(26, 0).addCuboid(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

                this.cube_r2 = new ModelPart(this);
                this.cube_r2.setPivot(4.0F, -3.4F, 0.2F);
                this.bodyhead.addChild(cube_r2);
                this.setRotationAngle(cube_r2, 0.0F, 0.0F, 0.0873F);
                this.cube_r2.setTextureOffset(26, 3).addCuboid(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

                this.tail = new ModelPart(this);
                this.tail.setPivot(0.0F, 3.6F, 2.7F);
                this.bodyhead.addChild(tail);
                this.tail.setTextureOffset(17, 0).addCuboid(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

                this.bb_main = new ModelPart(this);
                this.bb_main.setPivot(0.0F, 24.0F, 0.0F);
                this.bb_main.setTextureOffset(0, 18).addCuboid(-3.0F, -9.0F, -2.0F, 6.0F, 8.0F, 5.0F, 0.1F, false);
            } else {
                this.leftleg = new ModelPart(this);
                this.leftleg.setPivot(-1.5F, 22.5F, 0.5F);
                this.leftleg.setTextureOffset(22, 6).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

                this.rightleg = new ModelPart(this);
                this.rightleg.setPivot(1.5F, 22.5F, 0.5F);
                this.rightleg.setTextureOffset(22, 9).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

                this.bodyhead = new ModelPart(this);
                this.bodyhead.setPivot(0.0F, 18.4F, -0.2F);
                this.bodyhead.setTextureOffset(26, 3).addCuboid(2.0F, -4.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(26, 0).addCuboid(-4.0F, -4.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(0, 13).addCuboid(-2.0F, -0.4F, -2.8F, 4.0F, 2.0F, 1.0F, 0.0F, false);
                this.bodyhead.setTextureOffset(0, 0).addCuboid(-3.0F, -3.4F, -1.8F, 6.0F, 8.0F, 5.0F, 0.0F, false);

                this.tail = new ModelPart(this);
                this.tail.setPivot(0.0F, 3.6F, 2.7F);
                this.bodyhead.addChild(tail);
                this.tail.setTextureOffset(17, 0).addCuboid(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

                this.bb_main = new ModelPart(this);
                this.bb_main.setPivot(0.0F, 24.0F, 0.0F);
                this.bb_main.setTextureOffset(0, 18).addCuboid(-3.0F, -9.0F, -2.0F, 6.0F, 8.0F, 5.0F, 0.1F, false);
            }
        }

}