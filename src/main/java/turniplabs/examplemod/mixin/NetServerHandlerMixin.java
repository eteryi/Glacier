package turniplabs.examplemod.mixin;


import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import turniplabs.examplemod.events.GlacierEvents;
import turniplabs.examplemod.events.impl.PlayerChatEvent;

import java.util.Stack;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin {

	@Shadow
	private EntityPlayerMP playerEntity;

	@Unique
	private static Stack<PlayerChatEvent> events = new Stack<>();

	@Inject(method = "handleChat(Lnet/minecraft/core/net/packet/Packet3Chat;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/ChatEmotes;process(Ljava/lang/String;)Ljava/lang/String;", shift = At.Shift.AFTER, ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	public void onChatMessage(Packet3Chat packet, CallbackInfo ci, String s) {
		PlayerChatEvent chatEvent = new PlayerChatEvent(playerEntity, packet, s);
		GlacierEvents.runEventsFor(PlayerChatEvent.class, chatEvent);
		events.add(chatEvent);
	}

	@ModifyArgs(
		method = "handleChat",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;sendEncryptedChatToAllPlayers(Ljava/lang/String;)V", ordinal = 0)
	)
	public void sendPacket(Args args) {
		PlayerChatEvent event = events.pop();
		if (event == null) return;
		args.set(0, String.format(event.getFormat(), event.player.getDisplayName(), event.getMessage()));
	}



}
