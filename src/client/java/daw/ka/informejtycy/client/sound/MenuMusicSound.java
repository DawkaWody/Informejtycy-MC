package daw.ka.informejtycy.client.sound;

import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class MenuMusicSound extends AbstractSoundInstance {
	public MenuMusicSound(SoundEvent sound, float volume) {
		super(sound, SoundCategory.VOICE, Random.create());
		this.volume = volume;
		this.pitch = 1.0f;
		this.repeat = false;
		this.relative = true;
		this.attenuationType = AttenuationType.NONE;
	}

	@Override
	public boolean shouldAlwaysPlay() {
		return true;
	}
}
