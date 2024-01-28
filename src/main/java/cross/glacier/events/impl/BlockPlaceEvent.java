package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.core.net.packet.Packet15Place;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class BlockPlaceEvent implements ServerEvent {
	private boolean cancel;

	public final EntityPlayerMP player;
	public final Packet15Place packet;

	public BlockPlaceEvent(EntityPlayerMP player, Packet15Place packet) {
		this.cancel = false;
		this.player = player;
		this.packet = packet;
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
