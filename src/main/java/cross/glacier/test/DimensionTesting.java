package cross.glacier.test;

import cross.glacier.commands.CommandExecutor;
import cross.glacier.inventory.GlacierInventory;
import cross.glacier.utils.PlayerUtils;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class DimensionTesting implements CommandExecutor {

	@Override
	public void run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return;
        GlacierInventory inventory = new GlacierInventory("test", 9);
		int i = Integer.parseInt(args[0]);
		inventory.setInteractable(i == 1);
		inventory.setInventorySlotContents(4, new ItemStack(Item.ammoArrow, 1));

		inventory.addInteraction((player, packet) -> {
			if (packet.getSlot() == 4) {
				PlayerUtils.sendMessage(player, TextFormatting.LIME + "You have clicked the inventory!");
			}
        });
		inventory.open((EntityPlayerMP) sender.getPlayer());
	}
}




