package turniplabs.examplemod.events.impl;

import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.core.net.packet.Packet255KickDisconnect;
import net.minecraft.server.entity.player.EntityPlayerMP;
import turniplabs.examplemod.events.ServerEvent;

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
