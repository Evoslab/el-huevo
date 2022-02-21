package com.cookiejarmodding.el_huevo.client.render.model;

import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

/**
 * @author Evo, StevenPlayzz
 */
public class HuevoModel<T extends Huevo> extends AgeableListModel<T> {
	private final ModelPart root;

	public HuevoModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bodyhead = root.addOrReplaceChild("bodyhead", CubeListBuilder.create().texOffs(26, 3).addBox(2.0F, -8.9F, 0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-4.0F, -8.9F, 0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-2.0F, -4.9F, -2.8F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -7.9F, -1.8F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(-3.0F, -7.9F, -1.8F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.1F))
		.texOffs(16, 13).addBox(-3.0F, -6.5F, -3.7F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.1F, -0.2F));

		bodyhead.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -0.9F, 2.7F));
		root.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(22, 9).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.5F, 0.5F));
		root.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(22, 6).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.5F, 0.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, buffer, packedLight, packedOverlay);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.root);
	}
}