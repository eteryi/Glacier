package cross.glacier.commands;

import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public interface CommandExecutor {
	void run(CommandSender sender, CommandHandler handler, String[] args);
}
