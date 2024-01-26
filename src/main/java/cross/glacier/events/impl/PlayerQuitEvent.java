package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class PlayerQuitEvent implements ServerEvent {

	public final EntityPlayerMP player;
	public String format;

	public PlayerQuitEvent(EntityPlayerMP playerMP) {
		this.player = playerMP;
		this.format = "[-] %s";
	}

	@Override
	public void setCancelled(boolean b) {

	}

	@Override
	public boolean isCancelled() {
		return false;
	}
}
