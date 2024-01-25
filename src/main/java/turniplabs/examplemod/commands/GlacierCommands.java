package turniplabs.examplemod.commands;


import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.Commands;

import java.util.Stack;

public class GlacierCommands {
	private static final Stack<Command> preRegister = new Stack<>();

	public static void register(String name, CommandExecutor command, String... aliases) {
		Command newCommand = new Command(name, aliases) {
			@Override
			public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
				command.run(commandSender, commandHandler, strings);
				return true;
			}

			@Override
			public boolean opRequired(String[] strings) {
				return false;
			}

			@Override
			public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
				commandSender.sendMessage("/" + name);
			}
		};

		if (!Commands.commands.isEmpty()) { Commands.commands.add(newCommand); }
		else { preRegister.push(newCommand); }
	}

	public static Stack<Command> getPreRegister() {
		return preRegister;
	}
}
