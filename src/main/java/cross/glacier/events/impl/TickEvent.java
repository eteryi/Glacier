package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.core.world.World;

public class TickEvent implements ServerEvent {
	public final World world;
	public TickEvent(World world) {
		this.world = world;
	}
	@Override
	public void setCancelled(boolean b) {

	}

	@Override
	public boolean isCancelled() {
		return false;
	}
}
