package turniplabs.examplemod.test;

import turniplabs.examplemod.events.EventListener;
import turniplabs.examplemod.events.impl.PlayerChatEvent;

public class PlayerChatListener implements EventListener<PlayerChatEvent> {
	@Override
	public void run(PlayerChatEvent event) {
		event.setFormat("\"%2$s\" %1$s said calmly.");
	}

	@Override
	public Class<PlayerChatEvent> getEvent() {
		return PlayerChatEvent.class;
	}
}
