package turniplabs.examplemod.utils;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class Location {
	public final double x;
	public final double y;
	public final double z;

	public final int dimension;

	public final double yaw;
	public final double pitch;
	public Location(double x, double y, double z, int dimension, double yaw, double pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	public Location(double x, double y, double z, int dimension) {
		this(x, y, z, dimension, 90, 0);
	}

	private static TextFormatting getColorFromDimension(int dim) {
		switch (dim) {
			case 0:
				return TextFormatting.LIME;
			case 1:
				return TextFormatting.RED;
			case 2:
				return TextFormatting.YELLOW;
			default:
				return TextFormatting.LIGHT_GRAY;
		}
	}

	private static String getDimension(int dim) {
		switch (dim) {
			case 0:
				return "Overworld";
			case 1:
				return "Nether";
			case 2:
				return "End";
			default:
				return "Unknown";
		}
	}

	public void teleport(EntityPlayerMP playerMP) {
		teleport(playerMP, this.dimension, this.x, this.y, this.z, this.yaw, this.pitch);
	}

	public String toString() {
		return String.format("(x: %.1f, y: %.1f, z: %.1f) " + getColorFromDimension(this.dimension) + "[" + getDimension(this.dimension) + "]", this.x, this.y, this.z);
	}


	private static void teleport(EntityPlayer p1, Integer dimension, double x, double y, double z, double yaw, double pitch) {
		if (p1 instanceof EntityPlayerMP) {
			EntityPlayerMP p1MP = (EntityPlayerMP)p1;
			if (dimension != null && p1MP.dimension != dimension) {
				p1MP.mcServer.playerList.sendPlayerToOtherDimension(p1MP, dimension);
			}

			p1MP.playerNetServerHandler.teleportAndRotate(x, y, z, (float) pitch, (float) yaw);
		} else {
			p1.absMoveTo(x, y, z, (float) pitch, (float) yaw);
		}
	}

	public static Location from(EntityPlayer player) {
		return new Location(player.x, player.y, player.z, player.dimension, player.xRot, player.yRot);
	}
}
