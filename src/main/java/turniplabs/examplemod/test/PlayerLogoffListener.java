package turniplabs.examplemod.test;

import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.MinecraftServer;
import turniplabs.examplemod.events.EventListener;
import turniplabs.examplemod.events.impl.PlayerQuitEvent;

public class PlayerLogoffListener implements EventListener<PlayerQuitEvent> {
	@Override
	public void run(PlayerQuitEvent event) {
		event.format = TextFormatting.LIGHT_GRAY +  "[" + TextFormatting.RED + "-" + TextFormatting.GRAY + "] %s " + TextFormatting.GRAY + "(" + (MinecraftServer.getInstance().playerList.playerEntities.size() - 1) + "/12)";
	}

	@Override
	public Class<PlayerQuitEvent> getEvent() {
		return PlayerQuitEvent.class;
	}
}
