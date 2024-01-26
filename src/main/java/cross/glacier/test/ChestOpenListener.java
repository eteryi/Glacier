package cross.glacier.test;

import cross.glacier.events.EventListener;
import net.minecraft.core.block.Block;
import cross.glacier.events.impl.ChestOpenEvent;

public class ChestOpenListener implements EventListener<ChestOpenEvent> {
	@Override
	public void run(ChestOpenEvent event) {
		event.chestBlock.world.setBlock(event.chestBlock.x, event.chestBlock.y, event.chestBlock.z, Block.stone.id);
		event.setCancelled(true);
	}

	@Override
	public Class<ChestOpenEvent> getEvent() {
		return ChestOpenEvent.class;
	}
}