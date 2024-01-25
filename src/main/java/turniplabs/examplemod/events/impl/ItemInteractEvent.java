package turniplabs.examplemod.events.impl;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import turniplabs.examplemod.events.ServerEvent;
import turniplabs.examplemod.mixin.EntityPlayerMPMixin;

public class ItemInteractEvent implements ServerEvent {

	public final EntityPlayer player;
	public final ItemStack item;

	private boolean cancel;

	public ItemInteractEvent(EntityPlayer player, ItemStack item) {
		this.player = player;
		this.item = item;
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
