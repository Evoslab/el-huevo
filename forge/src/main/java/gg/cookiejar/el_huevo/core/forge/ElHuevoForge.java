package gg.cookiejar.el_huevo.core.forge;

import gg.cookiejar.el_huevo.core.ElHuevo;
import net.minecraftforge.fml.common.Mod;

@Mod(ElHuevo.MOD_ID)
public class ElHuevoForge {
    public ElHuevoForge() {
        ElHuevo.PLATFORM.setup();
    }
}
