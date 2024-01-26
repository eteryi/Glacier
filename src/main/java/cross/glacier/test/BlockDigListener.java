package cross.glacier.test;

import cross.glacier.events.EventListener;
import net.minecraft.core.player.gamemode.Gamemode;
import cross.glacier.events.impl.BlockBreakEvent;

public class BlockDigListener implements EventListener<BlockBreakEvent> {
	@Override
	public void run(BlockBreakEvent event) {
		if (event.playerMP.isOperator() && event.playerMP.gamemode == Gamemode.creative) return;
		event.setCancelled(true);
	}

	@Override
	public Class<BlockBreakEvent> getEvent() {
		return BlockBreakEvent.class;
	}
}
