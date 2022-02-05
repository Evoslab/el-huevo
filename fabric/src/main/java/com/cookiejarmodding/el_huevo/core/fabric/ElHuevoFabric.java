package com.cookiejarmodding.el_huevo.core.fabric;

import com.cookiejarmodding.el_huevo.core.ElHuevo;
import net.fabricmc.api.ModInitializer;

public class ElHuevoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
//        GeckoLib.initialize();
        ElHuevo.PLATFORM.setup();
    }
}