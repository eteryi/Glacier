package turniplabs.examplemod.test;

import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.MinecraftServer;
import turniplabs.examplemod.events.EventListener;
import turniplabs.examplemod.events.impl.PlayerJoinEvent;
import turniplabs.examplemod.utils.Location;

public class PlayerLoginListener implements EventListener<PlayerJoinEvent> {

	@Override
	public void run(PlayerJoinEvent event) {
		event.showLoginMessageToPlayer = true;
		event.player.heal(20);
		event.player.score = 0;
		Location location = new Location(192.5, 135.0, 57.5, 0);
		location.teleport(event.player);
		event.format = TextFormatting.LIGHT_GRAY +  "[" + TextFormatting.LIME + "+" + TextFormatting.GRAY + "] %s " + TextFormatting.GRAY + "(" + (MinecraftServer.getInstance().playerList.playerEntities.size() + 1) + "/12)";
	}

	@Override
	public Class<PlayerJoinEvent> getEvent() {
		return PlayerJoinEvent.class;
	}
}
