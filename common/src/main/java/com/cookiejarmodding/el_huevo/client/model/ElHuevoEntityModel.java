package com.cookiejarmodding.el_huevo.client.model;

import com.cookiejarmodding.el_huevo.common.entity.ElHuevoEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ElHuevoEntityModel extends AnimatedTickingGeoModel<ElHuevoEntity> {
    @Override
    public Identifier getModelLocation(ElHuevoEntity object) {
        return null;
    }

    @Override
    public Identifier getTextureLocation(ElHuevoEntity object) {
        return null;
    }

    @Override
    public Identifier getAnimationFileLocation(ElHuevoEntity animatable) {
        return null;
    }
}