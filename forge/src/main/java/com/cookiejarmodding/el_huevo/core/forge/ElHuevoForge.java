package com.cookiejarmodding.el_huevo.core.forge;

import com.cookiejarmodding.el_huevo.core.ElHuevo;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.GeckoLib;

@Mod(ElHuevo.MOD_ID)
public class ElHuevoForge {
    public ElHuevoForge() {
        GeckoLib.initialize();
        ElHuevo.PLATFORM.setup();
    }
}
