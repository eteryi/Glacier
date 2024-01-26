package cross.glacier.test;

import cross.glacier.events.EventListener;
import cross.glacier.events.impl.ItemInteractEvent;
import cross.glacier.utils.PlayerUtils;
import net.minecraft.core.item.Item;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class CompassInteract implements EventListener<ItemInteractEvent> {
	@Override
	public void run(ItemInteractEvent event) {
		if (event.item.getItem() == Item.toolCompass) {
			PlayerUtils.sendMessage((EntityPlayerMP) event.player, "You have just interacted with a compass");
		}
	}

	@Override
	public Class<ItemInteractEvent> getEvent() {
		return ItemInteractEvent.class;
	}
}
