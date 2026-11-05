package daw.ka.informejtycy.entity.custom.boss.alphawolf;

public enum AlphaWolfAction {
	WALK(0, 0),
	SLASH(28, 8),
	GROUND_PUNCH(40, 16),
	ROAR(64, 18),
	THROW_ROCK(34, 16),
	THROW_SHURIKEN(22, 8),
	MASK_OFF(60, 20),
	DEATH(0, 0);

	public final int ticks;
	public final int hitTick;

	AlphaWolfAction(int ticks, int hitTick) {
		this.ticks = ticks;
		this.hitTick = hitTick;
	}
}
