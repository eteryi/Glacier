package cross.glacier.mixin;


import cross.glacier.events.impl.PlayerJoinEvent;
import cross.glacier.utils.GlacierSettings;
import cross.glacier.utils.PlayerUtils;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetLoginHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import cross.glacier.events.GlacierEvents;

import java.util.Stack;

@Mixin(value = NetLoginHandler.class, remap = false)
public class NetLoginHandlerMixin {
	@Unique
	private static Stack<PlayerJoinEvent> events = new Stack<>();

	@Inject(method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;func_28170_a(Lnet/minecraft/server/entity/player/EntityPlayerMP;Lnet/minecraft/server/world/WorldServer;)V", ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void sendEvent(Packet1Login packet1login, CallbackInfo ci, EntityPlayerMP entityplayermp) {
		if (GlacierSettings.shouldResetNicknames()) {
			entityplayermp.nickname = TextFormatting.RESET + entityplayermp.username;
		}
		PlayerJoinEvent event = new PlayerJoinEvent(entityplayermp, packet1login);
		GlacierEvents.runEventsFor(PlayerJoinEvent.class, event);
		events.push(event);
		if (event.showLoginMessageToPlayer) {
			PlayerUtils.sendMessage(entityplayermp, String.format(event.format, event.useRawUsername() ? event.player.username : event.player.getDisplayName()));
		}
	}

	@ModifyArgs(
		method = "doLogin",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;sendPacketToAllPlayers(Lnet/minecraft/core/net/packet/Packet;)V", ordinal = 0)
	)
	public void doLogin(Args args) {
		PlayerJoinEvent event = events.pop();
		args.set(0, new Packet3Chat(String.format(event.format, event.useRawUsername() ? event.player.username : event.player.getDisplayName())));
	}
}
