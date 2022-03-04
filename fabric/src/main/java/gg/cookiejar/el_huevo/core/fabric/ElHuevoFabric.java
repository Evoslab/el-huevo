package gg.cookiejar.el_huevo.core.fabric;

import gg.cookiejar.el_huevo.core.ElHuevo;
import net.fabricmc.api.ModInitializer;

public class ElHuevoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ElHuevo.PLATFORM.setup();
    }
}