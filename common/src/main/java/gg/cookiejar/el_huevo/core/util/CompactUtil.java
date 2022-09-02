package gg.cookiejar.el_huevo.core.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import net.minecraft.world.item.Item;

public final class CompactUtil {
    @ExpectPlatform
    public static Item getKnife() {
        return Platform.error();
    }
}