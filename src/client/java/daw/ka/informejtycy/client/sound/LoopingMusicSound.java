package daw.ka.informejtycy.client.sound;

import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class LoopingMusicSound extends AbstractSoundInstance implements TickableSoundInstance {
	private boolean done = false;

	public LoopingMusicSound(SoundEvent sound, SoundCategory category, float volume) {
		super(sound, category, Random.create());
		this.volume = volume;
		this.pitch = 1.0f;
		this.repeat = true;
		this.repeatDelay = 0;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	public void stop() {
		this.done = true;
	}

	@Override
	public void tick() {

	}
}
