package turniplabs.examplemod.mixin;


import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.MeCommand;
import net.minecraft.core.net.command.commands.*;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.examplemod.commands.GlacierCommands;
import turniplabs.examplemod.utils.GlacierSettings;

import java.util.List;
import java.util.Stack;

import static net.minecraft.core.net.command.Commands.initCommands;

@Mixin(value = Commands.class, remap = false)
public class CommandsMixin {
	@Shadow
	public static List<Command> commands;

	@Inject(method = "initServerCommands", at = @At("HEAD"), cancellable = true)
	private static void onServerInit(MinecraftServer server, CallbackInfo ci) {
		if (!commands.isEmpty()) {
			throw new RuntimeException();
		} else {
			initCommands();
			commands.add(new StopCommand(server));
			commands.add(new OpCommand(server));
			commands.add(new DeopCommand(server));
			commands.add(new ListCommand(server));
			commands.add(new DifficultyCommand(server));
			commands.add(new WhoisCommand(server));
			commands.add(new ScoreCommand(server));
			commands.add(new MeCommand(server));
			commands.add(new EmotesCommand(server));
			commands.add(new BiomeCommand());

			if (GlacierSettings.shouldAllowNicknames()) {
				commands.add(new ColorCommand(server));
				commands.add(new NicknameCommand(server));
			}

			Stack<Command> preRegister = GlacierCommands.getPreRegister();
			while (!preRegister.isEmpty()) {
				commands.add(preRegister.pop());
			}
		}
		ci.cancel();
	}

}
