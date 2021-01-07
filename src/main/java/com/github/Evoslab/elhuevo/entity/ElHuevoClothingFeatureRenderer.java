package com.github.Evoslab.elhuevo.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ElHuevoClothingFeatureRenderer extends FeatureRenderer<ElHuevoEntity, ElHuevoEntityModel<ElHuevoEntity>> {

    public static final Identifier SKIN_BLACK = new Identifier("elhuevo","textures/entity/elhueve/black.png");
    public static final Identifier SKIN_RED = new Identifier("elhuevo","textures/entity/elhueve/red.png");
    public static final Identifier SKIN_GREEN = new Identifier("elhuevo","textures/entity/elhueve/green.png");
    public static final Identifier SKIN_BROWN = new Identifier("elhuevo","textures/entity/elhueve/brown.png");
    public static final Identifier SKIN_BLUE = new Identifier("elhuevo","textures/entity/elhueve/blue.png");
    public static final Identifier SKIN_PURPLE = new Identifier("elhuevo","textures/entity/elhueve/purple.png");
    public static final Identifier SKIN_CYAN = new Identifier("elhuevo","textures/entity/elhueve/cyan.png");
    public static final Identifier SKIN_LIGHT_GRAY  = new Identifier("elhuevo","textures/entity/elhueve/light_gray.png");
    public static final Identifier SKIN_GRAY  = new Identifier("elhuevo","textures/entity/elhueve/gray.png");
    public static final Identifier SKIN_PINK  = new Identifier("elhuevo","textures/entity/elhueve/pink.png");
    public static final Identifier SKIN_LIME  = new Identifier("elhuevo","textures/entity/elhueve/lime.png");
    public static final Identifier SKIN_YELLOW  = new Identifier("elhuevo","textures/entity/elhueve/yellow.png");
    public static final Identifier SKIN_LIGHT_BLUE  = new Identifier("elhuevo","textures/entity/elhueve/light_blue.png");
    public static final Identifier SKIN_MAGENTA  = new Identifier("elhuevo","textures/entity/elhueve/magenta.png");
    public static final Identifier SKIN_ORANGE  = new Identifier("elhuevo","textures/entity/elhueve/orange.png");
    public static final Identifier SKIN_WHITE  = new Identifier("elhuevo","textures/entity/elhueve/white.png");

    public ElHuevoClothingFeatureRenderer(FeatureRendererContext<ElHuevoEntity, ElHuevoEntityModel<ElHuevoEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    private void renderModelWithSkin(Identifier identifier, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ElHuevoEntity entity) {
        float[] colors = entity.getClothingColor().getColorComponents();
        renderModel(this.getContextModel(), identifier, matrixStack, vertexConsumerProvider, i, entity, colors[0], colors[1], colors[2]);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ElHuevoEntity elHuevoEntity, float f, float g, float h, float j, float k, float l) {
        if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible()) {
            Identifier skin;

            switch (elHuevoEntity.getClothingColor()) {
                case RED:
                    skin = SKIN_RED;
                    break;
                case BLUE:
                    skin = SKIN_BLUE;
                    break;
                case CYAN:
                    skin = SKIN_CYAN;
                    break;
                case GRAY:
                    skin = SKIN_GRAY;
                    break;
                case LIME:
                    skin = SKIN_LIME;
                    break;
                case PINK:
                    skin = SKIN_PINK;
                    break;
                case BLACK:
                    skin = SKIN_BLACK;
                    break;
                case BROWN:
                    skin = SKIN_BROWN;
                    break;
                case GREEN:
                    skin = SKIN_GREEN;
                    break;
                case ORANGE:
                    skin = SKIN_ORANGE;
                    break;
                case PURPLE:
                    skin = SKIN_PURPLE;
                    break;
                case YELLOW:
                    skin = SKIN_YELLOW;
                    break;
                case MAGENTA:
                    skin = SKIN_MAGENTA;
                    break;
                case LIGHT_BLUE:
                    skin = SKIN_LIGHT_BLUE;
                    break;
                case LIGHT_GRAY:
                    skin = SKIN_LIGHT_GRAY;
                    break;
                default:
                case WHITE:
                    skin = SKIN_WHITE;
                    break;
            }
            this.renderModelWithSkin(skin, matrixStack, vertexConsumerProvider, i, elHuevoEntity);
        }

    }
}
