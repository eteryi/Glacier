package cross.glacier.events.impl;

import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.server.entity.player.EntityPlayerMP;
import cross.glacier.events.ServerEvent;

public class PlayerJoinEvent implements ServerEvent {

	public final EntityPlayerMP player;
	public String format;
	public final Packet1Login packet;
	public boolean showLoginMessageToPlayer;
	private boolean rawUsername;

	public PlayerJoinEvent(EntityPlayerMP playerMP, Packet1Login packet) {
		this.player = playerMP;
		this.packet = packet;
		this.format = "[+] %s";
		this.rawUsername = false;
		this.showLoginMessageToPlayer = false;
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
