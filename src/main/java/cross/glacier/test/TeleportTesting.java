package cross.glacier.test;

import cross.glacier.utils.Location;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;
import cross.glacier.commands.CommandExecutor;
import cross.glacier.runnable.GlacierRunnable;

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
		GlacierRunnable runnable = new GlacierRunnable(_void -> {
			sender.sendMessage("Oh this was lateeee");
		});
		int[] times = {1};
		GlacierRunnable runnable2 = new GlacierRunnable(runnable_ -> {
			sender.sendMessage("Oh this was later even");
			if (times[0] == 5) runnable_.stop();
			times[0] = times[0] + 1;
		});
		runnable.runLater(20);
		runnable2.runTimer(40);

		locations.pop().teleport((EntityPlayerMP) sender.getPlayer());
	}
}

