package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class PlayerQuitEvent implements ServerEvent {

	public final EntityPlayerMP player;
	public String format;

	private boolean rawUsername;
	public PlayerQuitEvent(EntityPlayerMP playerMP) {
		this.player = playerMP;
		this.format = "[-] %s";
		this.rawUsername = false;
	}
	public boolean useRawUsername() {
		return this.rawUsername;
	}

	public void shouldUseRawUsername(boolean b) {
		this.rawUsername = b;
	}
	@Override
	public void setCancelled(boolean b) {

	}

	@Override
	public boolean isCancelled() {
		return false;
	}
}
