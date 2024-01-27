package cross.glacier.events.wrappers;

import net.minecraft.core.net.packet.Packet102WindowClick;

public class WindowClickPacket {
	public final Packet102WindowClick rawPacket;

	public WindowClickPacket(Packet102WindowClick rawPacket) {
		this.rawPacket = rawPacket;
	}

	public int getSlot() {
		if (rawPacket.args.length <= 0) {
			return -1;
		}
		return rawPacket.args[0];
	}
}
