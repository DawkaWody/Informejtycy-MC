package daw.ka.informejtycy.event;

import daw.ka.informejtycy.event.custom.ApplyArmorHealthEvent;
import daw.ka.informejtycy.event.custom.MusicDiscDropEvent;
import daw.ka.informejtycy.event.custom.ZmysioSpawnEvent;

public class InformejtycyEvents {
	public static void registerAll() {
		MusicDiscDropEvent.register();
        ZmysioSpawnEvent.register();
		ApplyArmorHealthEvent.register();
	}
}
