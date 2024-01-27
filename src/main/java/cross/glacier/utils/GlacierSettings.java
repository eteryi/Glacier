package cross.glacier.utils;

import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.commands.ColorCommand;
import net.minecraft.core.net.command.commands.NicknameCommand;
import net.minecraft.server.MinecraftServer;

public class GlacierSettings {
	private static boolean allowNicknames = true;
	private static boolean resetNicknames = false;
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

	public static boolean shouldResetNicknames() {
		return resetNicknames;
	}

	public static void setResetNicknames(boolean b) {
		resetNicknames = b;
	}

	private static void removeNicknameCommands() {
		if (Commands.commands == null) return;
		Commands.commands.removeIf(it -> it.getClass() == NicknameCommand.class || it.getClass() == ColorCommand.class);
	}
	private static void addNicknameCommands() {
		if (Commands.commands == null) return;
		long nicknameCommand = Commands.commands.stream().filter(it -> it.getClass() == NicknameCommand.class).count();
		if (nicknameCommand == 0) Commands.commands.add(new NicknameCommand(MinecraftServer.getInstance()));
		long colorCommand = Commands.commands.stream().filter(it -> it.getClass() == ColorCommand.class).count();
		if (colorCommand == 0) Commands.commands.add(new ColorCommand(MinecraftServer.getInstance()));
	}
}
