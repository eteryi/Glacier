package cross.glacier.inventory;

import cross.glacier.events.wrappers.WindowClickPacket;
import net.minecraft.server.entity.player.EntityPlayerMP;

public interface InventoryInteract {
	void run(EntityPlayerMP player, WindowClickPacket packet);
}
