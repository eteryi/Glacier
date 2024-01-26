package cross.glacier.utils;

import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.commands.ColorCommand;
import net.minecraft.core.net.command.commands.NicknameCommand;
import net.minecraft.server.MinecraftServer;

public class GlacierSettings {
	private static boolean allowNicknames = true;

	public static boolean shouldAllowNicknames() {
		return allowNicknames;
	}

	public static void setNicknames(boolean b) {
		allowNicknames = b;
		if (allowNicknames) {
			addNicknameCommands();
			return;
		}
		removeNicknameCommands();
    }

	private static void removeNicknameCommands() {
		Commands.commands.removeIf(it -> it.getClass() == NicknameCommand.class || it.getClass() == ColorCommand.class);
	}
	private static void addNicknameCommands() {
		long nicknameCommand = Commands.commands.stream().filter(it -> it.getClass() == NicknameCommand.class).count();
		if (nicknameCommand == 0) Commands.commands.add(new NicknameCommand(MinecraftServer.getInstance()));
		long colorCommand = Commands.commands.stream().filter(it -> it.getClass() == ColorCommand.class).count();
		if (colorCommand == 0) Commands.commands.add(new ColorCommand(MinecraftServer.getInstance()));
	}
}
