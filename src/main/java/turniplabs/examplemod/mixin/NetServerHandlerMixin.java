package turniplabs.examplemod.mixin;


import net.minecraft.core.net.packet.Packet14BlockDig;
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
import turniplabs.examplemod.events.impl.*;

import java.util.Stack;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin {

	@Shadow
	private EntityPlayerMP playerEntity;

	@Unique
	private static Stack<PlayerChatEvent> events = new Stack<>();

	@Inject(method = "handleChat(Lnet/minecraft/core/net/packet/Packet3Chat;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/ChatEmotes;process(Ljava/lang/String;)Ljava/lang/String;", shift = At.Shift.AFTER, ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	public void onChatMessage(Packet3Chat packet, CallbackInfo ci, String s) {
		PlayerChatEvent chatEvent = new PlayerChatEvent(playerEntity, packet, s);
		GlacierEvents.runEventsFor(PlayerChatEvent.class, chatEvent);
		if (chatEvent.isCancelled()) {
			ci.cancel();
			return;
		}
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

	@Inject(method = "handleBlockDig", at = @At(value = "HEAD"), cancellable = true)

	public void handleBlockDig(Packet14BlockDig packet, CallbackInfo ci) {
		BlockBreakEvent event = new BlockBreakEvent(this.playerEntity, new BlockWrapper(packet.xPosition, packet.yPosition, packet.zPosition, this.playerEntity.world), packet);
		GlacierEvents.runEventsFor(BlockBreakEvent.class, event);
		if (event.isCancelled()) {
			ci.cancel();
			event.block.world.markBlockNeedsUpdate(event.block.x, event.block.y, event.block.z);
			event.block.world.notifyBlocksOfNeighborChange(event.block.x, event.block.y, event.block.z, event.block.getId());
			return;
		}
	}

	private static Stack<PlayerQuitEvent> playerQuitEvents = new Stack<>();

	@Inject(method = "handleErrorMessage", at = @At("HEAD"))
	public void disconnect(String s, Object[] aobj, CallbackInfo ci) {
		PlayerQuitEvent event = new PlayerQuitEvent(this.playerEntity);
		GlacierEvents.runEventsFor(PlayerQuitEvent.class, event);
		playerQuitEvents.push(event);
	}

	@ModifyArgs(
		method = "handleErrorMessage",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;sendPacketToAllPlayers(Lnet/minecraft/core/net/packet/Packet;)V", ordinal = 0)
	)
	public void onDisconnect(Args args) {
		PlayerQuitEvent event = playerQuitEvents.pop();
		if (event == null) return;
		args.set(0, new Packet3Chat(String.format(event.format, this.playerEntity.getDisplayName())));
	}

}
