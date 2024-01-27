package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import cross.glacier.events.wrappers.WindowClickPacket;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet102WindowClick;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class WindowInteractEvent implements ServerEvent {
	private boolean cancelled = false;
	public final WindowClickPacket packet;
	public final EntityPlayer player;
	public WindowInteractEvent(EntityPlayerMP player, Packet102WindowClick packet) {
		this.player = player;
		this.packet = new WindowClickPacket(packet);
	}

    @Override
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
}
