package com.cookiejarmodding.el_huevo.client.render.entity;

import com.cookiejarmodding.el_huevo.common.entity.ElHuevoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ElHuevoRender<T extends ElHuevoEntity> extends MobEntityRenderer<T, ElHuevo<T>> {
}