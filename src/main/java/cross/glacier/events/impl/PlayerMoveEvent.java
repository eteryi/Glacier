package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.core.net.packet.Packet10Flying;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class PlayerMoveEvent implements ServerEvent {
	public final EntityPlayerMP player;
	public final Packet10Flying packet;

	private boolean cancel;

	public PlayerMoveEvent(EntityPlayerMP player, Packet10Flying packet) {
		this.player = player;
		this.packet = packet;
		this.cancel = false;
	}

	@Override
	public void setCancelled(boolean b) {
		this.cancel = b;
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}
}
