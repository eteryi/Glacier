package cross.glacier.test;

import cross.glacier.events.EventListener;
import net.minecraft.server.entity.player.EntityPlayerMP;
import cross.glacier.events.impl.PlayerHurtEvent;

public class PlayerHurtListener implements EventListener<PlayerHurtEvent> {
	@Override
	public void run(PlayerHurtEvent event) {
		event.setDamage(40);
		System.out.println(event.type.getLanguageKey() + " " + (event.entity instanceof EntityPlayerMP));
	}

	@Override
	public Class<PlayerHurtEvent> getEvent() {
		return PlayerHurtEvent.class;
	}
}
