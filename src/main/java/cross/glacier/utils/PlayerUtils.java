package cross.glacier.utils;

import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class PlayerUtils {
	public static void sendMessage(EntityPlayerMP playerMP, String message) {
		playerMP.playerNetServerHandler.sendPacket(new Packet3Chat(message));
	}

	public static void resetPlayer(EntityPlayerMP playerMP) {
		playerMP.heal(40);
		for (int i = 0; i < playerMP.inventory.getSizeInventory(); i++) {
			playerMP.inventory.setInventorySlotContents(i, null);
		}
	}
}
