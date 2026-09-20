package daw.ka.informejtycy.event;

import daw.ka.informejtycy.event.custom.ApplyArmorHealthEvent;
import daw.ka.informejtycy.event.custom.MusicDiscDropEvent;

public class InformejtycyEvents {
	public static void registerAll() {
		MusicDiscDropEvent.register();
		ApplyArmorHealthEvent.register();
	}
}
