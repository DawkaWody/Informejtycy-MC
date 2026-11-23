package daw.ka.informejtycy.event;

import daw.ka.informejtycy.event.custom.ApplyArmorHealthEvent;
import daw.ka.informejtycy.event.custom.MusicDiscDropEvent;
import daw.ka.informejtycy.event.custom.ZmysioChestplateAttackEvent;

public class InformejtycyEvents {
	public static void registerAll() {
		MusicDiscDropEvent.register();
		ApplyArmorHealthEvent.register();
		ZmysioChestplateAttackEvent.register();
	}
}
