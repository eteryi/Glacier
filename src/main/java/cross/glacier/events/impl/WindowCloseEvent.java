package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet101CloseWindow;

public class WindowCloseEvent implements ServerEvent {
	public final Packet101CloseWindow packet;
	public final EntityPlayer player;

	public WindowCloseEvent(EntityPlayer player, Packet101CloseWindow packet) {
		this.packet = packet;
		this.player = player;
	}

	@Override
	public void setCancelled(boolean b) {

	}

	@Override
	public boolean isCancelled() {
		return false;
	}
}
