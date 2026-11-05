package daw.ka.informejtycy.entity.custom.boss.alphawolf;

public enum AlphaWolfPhase {
	STATUE(0, true, Sizes.SITTING_SCALE, 0.88f, 1.16f),
	AWAKENING(24, true, Sizes.SITTING_SCALE, 0.88f, 1.16f),
	SITTING(0, false, Sizes.SITTING_SCALE, 0.88f, 1.16f),
	RISING_1(6, true, 5.2, 0.92f, 1.42f),
	RISING_2(6, true, 4.4, 0.9f, 1.74f),
	STANDING(0, false, Sizes.STANDING_SCALE, 0.9f, 1.9f),
	MASK_OFF(60, true, Sizes.STANDING_SCALE, 0.9f, 1.9f),
	UNMASKED(0, false, Sizes.STANDING_SCALE, 0.9f, 1.9f),
	DEAD(80, true, Sizes.STANDING_SCALE, 0.9f, 0.7f);

	public final int ticks;
	public final boolean invulnerable;
	public final double scale;
	public final float width;
	public final float height;

	AlphaWolfPhase(int ticks, boolean invulnerable, double scale, float width, float height) {
		this.ticks = ticks;
		this.invulnerable = invulnerable;
		this.scale = scale;
		this.width = width;
		this.height = height;
	}

	private static final class Sizes {
		private static final double SITTING_SCALE = 6.0;
		private static final double STANDING_SCALE = 4.0;
	}

	public boolean canAct() {
		return this == STANDING || this == UNMASKED;
	}
}
