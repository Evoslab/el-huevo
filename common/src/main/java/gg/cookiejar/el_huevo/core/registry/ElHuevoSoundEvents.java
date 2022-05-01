package gg.cookiejar.el_huevo.core.registry;

import gg.cookiejar.el_huevo.core.ElHuevo;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ElHuevoSoundEvents {
    public static final PollinatedRegistry<SoundEvent> SOUND_EVENTS = PollinatedRegistry.create(Registry.SOUND_EVENT, ElHuevo.MOD_ID);

    public static final Supplier<SoundEvent> HUEVO_AMBIENT = registerSound("huevo_ambient");
    public static final Supplier<SoundEvent> HUEVO_DEATH = registerSound("huevo_death");
    public static final Supplier<SoundEvent> HUEVO_HURT = registerSound("huevo_hurt");
    public static final Supplier<SoundEvent> HUEVO_PANT = registerSound("huevo_pant");
    public static final Supplier<SoundEvent> HUEVO_STEP = registerSound("huevo_step");
    public static final Supplier<SoundEvent> HUEVO_WHINE = registerSound("huevo_whine");

    private static Supplier<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(ElHuevo.generateResourceLocation(name)));
    }
}