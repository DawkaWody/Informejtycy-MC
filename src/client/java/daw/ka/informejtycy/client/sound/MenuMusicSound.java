package daw.ka.informejtycy.client.sound;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;

public class MenuMusicSound extends AbstractSoundInstance {
	public MenuMusicSound(SoundEvent sound, float volume) {
		super(sound, SoundSource.VOICE, RandomSource.create());
		this.volume = volume;
		this.pitch = 1.0f;
		this.looping = false;
		this.relative = true;
		this.attenuation = Attenuation.NONE;
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}
}
