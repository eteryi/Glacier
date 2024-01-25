package turniplabs.examplemod.events.impl;

import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import turniplabs.examplemod.events.ServerEvent;

public class PlayerChatEvent implements ServerEvent {
	public final EntityPlayerMP player;
	public final Packet3Chat packet;
	private final String[] message;
	private String format;
	public PlayerChatEvent(EntityPlayerMP player, Packet3Chat packet3Chat, String message) {
		this.player = player;
		this.packet = packet3Chat;
		this.message = new String[1];
		this.message[0] = message;
		this.format = "%1$s >> %2$s";
	}

	public String getMessage() {
		return this.message[0];
	}

	public void setMessage(String s) {
		this.message[0] = s;
	}

	public void setFormat(String s) {
		this.format = s;
	}

	public String getFormat() {
		return format;
	}
}