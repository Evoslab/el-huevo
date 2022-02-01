package com.cookiejarmodding.el_huevo.core.fabric;

import com.cookiejarmodding.el_huevo.core.ElHuevo;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ElHuevoFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        ElHuevo.PLATFORM.dataSetup(dataGenerator);
    }
}
