package cross.glacier.mixin;


import cross.glacier.events.GlacierEvents;
import cross.glacier.events.impl.*;
import cross.glacier.events.wrappers.BlockWrapper;
import cross.glacier.inventory.GlacierInventory;
import net.minecraft.core.net.packet.*;
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

import java.util.Stack;

@Mixin(value = NetServerHandler.class, remap = false)
public abstract class NetServerHandlerMixin {

	@Shadow
	private EntityPlayerMP playerEntity;

	@Inject(method = "handleFlying", at = @At(value = "HEAD"), cancellable = true)
	public void onFlying(Packet10Flying packet, CallbackInfo ci) {
		if (packet.moving) {
			PlayerMoveEvent event = new PlayerMoveEvent(this.playerEntity, packet);
			GlacierEvents.runEventsFor(PlayerMoveEvent.class, event);
			if (event.isCancelled()) {
				ci.cancel();
            }
		}
	}
	/*
	PlayerChatEvent Functionality
	 */

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
		args.set(0, String.format(event.getFormat(), event.useRawUsername() ? event.player.username : event.player.getDisplayName(), event.getMessage()));
	}

	/*
	BlockBreakEvent Functionality
	 */

	@Inject(method = "handleBlockDig", at = @At(value = "HEAD"), cancellable = true)

	public void handleBlockDig(Packet14BlockDig packet, CallbackInfo ci) {
		BlockBreakEvent event = new BlockBreakEvent(this.playerEntity, new BlockWrapper(packet.xPosition, packet.yPosition, packet.zPosition, this.playerEntity.world), packet);
		GlacierEvents.runEventsFor(BlockBreakEvent.class, event);
		if (event.isCancelled()) {
			ci.cancel();
			event.block.world.markBlockNeedsUpdate(event.block.x, event.block.y, event.block.z);
			event.block.world.notifyBlocksOfNeighborChange(event.block.x, event.block.y, event.block.z, event.block.getId());
		}
	}

	/*
	BlockPlaceEvent Functionality
	 */
	@Inject(method = "handlePlace", at = @At(value = "HEAD"), cancellable = true)

	public void handlePlace(Packet15Place packet, CallbackInfo ci) {
		if (packet.xPosition == - 1 && packet.yPosition == -1 && packet.zPosition == -1) {
			return;
		}
		BlockPlaceEvent event = new BlockPlaceEvent(this.playerEntity, packet);
		GlacierEvents.runEventsFor(BlockPlaceEvent.class, event);
		if (event.isCancelled()) {
			ci.cancel();
			BlockWrapper block = new BlockWrapper(packet.xPosition + packet.direction.getOffsetX(), packet.yPosition + packet.direction.getOffsetY(), packet.zPosition + packet.direction.getOffsetZ(), playerEntity.world);
			playerEntity.world.markBlockNeedsUpdate(block.x, block.y, block.z);
			playerEntity.world.notifyBlocksOfNeighborChange(block.x, block.y, block.z, block.getId());
			playerEntity.playerNetServerHandler.sendPacket(new Packet104WindowItems(0, playerEntity.inventorySlots.inventoryItemStacks));
        }
	}

	/*
	PlayerQuitEvent Functionality
	 */


	@Unique
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
		args.set(0, new Packet3Chat(String.format(event.format, event.useRawUsername() ? event.player.username : event.player.getDisplayName())));
	}

	/*
	Window Events
	 */
	@Inject(
		method = "handleWindowClick",
		at = {@At("HEAD")},
		remap = false, cancellable = true)
	public void onWindowClick(Packet102WindowClick packet, CallbackInfo ci) {
		WindowInteractEvent event = new WindowInteractEvent(this.playerEntity, packet);
		GlacierEvents.runEventsFor(WindowInteractEvent.class, event);
		if (event.isCancelled()) {
			ci.cancel();
			return;
		}

		GlacierInventory.InventoryPlayer invPlayer = GlacierInventory.getInventoryPlayer((EntityPlayerMP) event.player);
		if (event.packet.rawPacket == null) return;
		if (invPlayer.getId() == event.packet.rawPacket.window_Id) {
			GlacierInventory inv = invPlayer.getInventory();
			inv.getInteractions().forEach(it -> it.run(this.playerEntity, event.packet));
			if (!inv.isInteractable()) {
				inv.reloadFor((EntityPlayerMP) event.player);
				event.setCancelled(true);
			}
		}
	}
	@Inject(
		method = "handleCloseWindow",
		at = {@At("HEAD")},
		remap = false)
	public void onWindowClose(Packet101CloseWindow packet, CallbackInfo ci) {
		WindowCloseEvent event = new WindowCloseEvent(this.playerEntity, packet);
		GlacierEvents.runEventsFor(WindowCloseEvent.class, event);
		GlacierInventory.InventoryPlayer invPlayer = GlacierInventory.getInventoryPlayer((EntityPlayerMP) event.player);
		if (invPlayer.getId() == packet.windowId) {
			invPlayer.getInventory().removePlayer(event.player);
		}
	}
}
