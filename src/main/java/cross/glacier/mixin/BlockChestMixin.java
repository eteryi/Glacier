package cross.glacier.mixin;

import cross.glacier.events.wrappers.BlockWrapper;
import net.minecraft.core.block.BlockChest;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cross.glacier.events.GlacierEvents;
import cross.glacier.events.impl.ChestOpenEvent;

import static net.minecraft.core.block.BlockChest.getInventory;

@Mixin(BlockChest.class)
public class BlockChestMixin {

	@Inject(at = @At("HEAD"), method = "blockActivated", remap = false, cancellable = true)
	public void blockActivated(World world, int x, int y, int z, EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {
		if (!world.isClientSide) {
			IInventory inv = getInventory(world, x, y, z);
			ChestOpenEvent event = new ChestOpenEvent(player, new BlockWrapper(x, y, z, world), inv);
			GlacierEvents.runEventsFor(ChestOpenEvent.class, event);

			if (event.isCancelled()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
