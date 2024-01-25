package turniplabs.examplemod.test;

import net.minecraft.core.item.Item;
import net.minecraft.server.entity.player.EntityPlayerMP;
import turniplabs.examplemod.events.EventListener;
import turniplabs.examplemod.events.impl.ItemInteractEvent;
import turniplabs.examplemod.utils.PlayerUtils;

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
