package turniplabs.examplemod.test;

import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import turniplabs.examplemod.events.EventListener;
import turniplabs.examplemod.events.impl.PlayerHurtEvent;

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
