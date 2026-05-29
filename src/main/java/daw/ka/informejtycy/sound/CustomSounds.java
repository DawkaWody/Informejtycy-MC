package daw.ka.informejtycy.sound;

import daw.ka.informejtycy.InformejtycyRegistry;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;

public class CustomSounds {
	public static SoundEvent MENU_AUDIO;
    public static SoundEvent ZALES;
	public static SoundEvent THUNDERSTRUCK;
    // Music Discs
	public static SoundEvent RIDE_THE_LIGHTNING;
	public static SoundEvent HOLY_WARS;
	public static SoundEvent YOU_MUST_BURN;
	public static SoundEvent NO_MORE_TEARS;
    public static SoundEvent ZALEWIX_BEAT;
	public static SoundEvent STELLA;

	public static final RegistryKey<JukeboxSong> RIDE_THE_LIGHTNING_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG,
			InformejtycyRegistry.id("ride_the_lightning"));
	public static final RegistryKey<JukeboxSong> HOLY_WARS_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG,
			InformejtycyRegistry.id("holy_wars"));
	public static final RegistryKey<JukeboxSong> YOU_MUST_BURN_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG,
			InformejtycyRegistry.id("you_must_burn"));
	public static final RegistryKey<JukeboxSong> NO_MORE_TEARS_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG,
			InformejtycyRegistry.id("no_more_tears"));
    public static final RegistryKey<JukeboxSong> ZALEWIX_BEAT_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG,
            InformejtycyRegistry.id("zalewix_beat"));
	public static final RegistryKey<JukeboxSong> STELLA_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG,
			InformejtycyRegistry.id("stella"));

	public static void registerAll() {
		MENU_AUDIO = InformejtycyRegistry.registerSoundEvent("menu_audio");
        ZALES = InformejtycyRegistry.registerSoundEvent("zales");
        // Music Discs
		THUNDERSTRUCK = InformejtycyRegistry.registerSoundEvent("thunderstruck");
		RIDE_THE_LIGHTNING = InformejtycyRegistry.registerSoundEvent("ride_the_lightning");
		HOLY_WARS = InformejtycyRegistry.registerSoundEvent("holy_wars");
		YOU_MUST_BURN = InformejtycyRegistry.registerSoundEvent("you_must_burn");
		NO_MORE_TEARS = InformejtycyRegistry.registerSoundEvent("no_more_tears");
        ZALEWIX_BEAT = InformejtycyRegistry.registerSoundEvent("zalewix_beat");
		STELLA = InformejtycyRegistry.registerSoundEvent("stella");
	}
}
