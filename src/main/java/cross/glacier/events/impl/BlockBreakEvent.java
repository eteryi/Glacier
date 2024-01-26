package cross.glacier.events.impl;

import net.minecraft.core.net.packet.Packet14BlockDig;
import net.minecraft.server.entity.player.EntityPlayerMP;
import cross.glacier.events.ServerEvent;

public class BlockBreakEvent implements ServerEvent {
	public final BlockWrapper block;
	public final Packet14BlockDig packet;
	public final EntityPlayerMP playerMP;

	private boolean cancel;

	public BlockBreakEvent(EntityPlayerMP playerMP, BlockWrapper block, Packet14BlockDig packet) {
		this.cancel = false;
		this.playerMP = playerMP;
		this.packet = packet;
		this.block = block;
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
