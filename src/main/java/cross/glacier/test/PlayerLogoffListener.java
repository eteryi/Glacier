package cross.glacier.test;

import cross.glacier.events.EventListener;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.MinecraftServer;
import cross.glacier.events.impl.PlayerQuitEvent;

public class PlayerLogoffListener implements EventListener<PlayerQuitEvent> {
	@Override
	public void run(PlayerQuitEvent event) {
		event.format = TextFormatting.LIGHT_GRAY +  "[" + TextFormatting.RED + "-" + TextFormatting.LIGHT_GRAY + "] %s " + TextFormatting.GRAY + "(" + (MinecraftServer.getInstance().playerList.playerEntities.size() - 1) + "/12)";
	}

	@Override
	public Class<PlayerQuitEvent> getEvent() {
		return PlayerQuitEvent.class;
	}
}
