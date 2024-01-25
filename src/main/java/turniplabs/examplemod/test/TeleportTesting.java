package turniplabs.examplemod.test;

import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;
import turniplabs.examplemod.commands.CommandExecutor;
import turniplabs.examplemod.utils.Location;

import java.util.Stack;

public class TeleportTesting implements CommandExecutor {
	public static Stack<Location> locations = new Stack<>();
	@Override
	public void run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return;

		if (locations.isEmpty()) {
			sender.sendMessage(TextFormatting.LIME + "Saved your current location to the stack!");
			locations.add(Location.from(sender.getPlayer()));
			return;
		}

		sender.sendMessage(TextFormatting.ORANGE + "Teleported to the last saved location!");
		locations.pop().teleport((EntityPlayerMP) sender.getPlayer());
	}
}
