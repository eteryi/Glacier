package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import cross.glacier.events.wrappers.BlockWrapper;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;

public class ChestOpenEvent implements ServerEvent {
	public final EntityPlayer player;
	public final IInventory chestInventory;
	public final BlockWrapper chestBlock;
	private boolean cancelled;
	public ChestOpenEvent(EntityPlayer player, BlockWrapper chestBlock, IInventory inventory) {
		this.player = player;
		this.chestInventory = inventory;
		this.chestBlock = chestBlock;
		this.cancelled = false;
	}

	public void setCancelled(boolean b) {
		this.cancelled = b;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}
}
