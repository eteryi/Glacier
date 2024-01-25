package turniplabs.examplemod.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.examplemod.events.GlacierEvents;
import turniplabs.examplemod.events.impl.ItemInteractEvent;

@Mixin(value = Item.class, remap = false)
public class ItemMixin {
	@Inject(at = @At("HEAD"), method = "onItemRightClick", remap = false, cancellable = true)
	public void onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, CallbackInfoReturnable<ItemStack> cir) {
		ItemInteractEvent event = new ItemInteractEvent(entityplayer, itemstack);
		GlacierEvents.runEventsFor(ItemInteractEvent.class, event);
		if (event.isCancelled()) {
			cir.setReturnValue(itemstack);
			return;
		}
	}
}
