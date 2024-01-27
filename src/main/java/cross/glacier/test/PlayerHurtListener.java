package cross.glacier.test;

import cross.glacier.events.EventListener;
import cross.glacier.events.impl.PlayerHurtEvent;

public class PlayerHurtListener implements EventListener<PlayerHurtEvent> {
	@Override
	public void run(PlayerHurtEvent event) {
		event.setDamage(40);
	}

	@Override
	public Class<PlayerHurtEvent> getEvent() {
		return PlayerHurtEvent.class;
	}
}
