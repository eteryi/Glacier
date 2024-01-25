package turniplabs.examplemod.utils;

import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class PlayerUtils {
	public static void sendMessage(EntityPlayerMP playerMP, String message) {
		playerMP.playerNetServerHandler.sendPacket(new Packet3Chat(message));
	}


}
