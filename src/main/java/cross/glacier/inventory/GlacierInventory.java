package cross.glacier.inventory;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet101CloseWindow;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventorySorter;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GlacierInventory implements IInventory {
	public static class InventoryPlayer {
		public final EntityPlayerMP playerMP;
		int id;
		GlacierInventory inventory;

		public InventoryPlayer(EntityPlayerMP player, int id, GlacierInventory inventory) {
			this.playerMP = player;
			this.id = id;
			this.inventory = inventory;
		}

		public int getId() {
			return id;
		}

		public GlacierInventory getInventory() {
			return inventory;
		}

		public String toString() {
			return String.format("Player {id: %d; inventory = %s}", id, inventory);
		}
	}

	public final String windowName;
	private final ItemStack[] inventory;

	private boolean interactable;

	private static final HashMap<EntityPlayerMP, InventoryPlayer> inventoryPlayers = new HashMap<>();
	private final ArrayList<InventoryInteract> interactions;
	private final HashSet<InventoryPlayer> viewers;

	public GlacierInventory(String windowName, int size) {
		this.windowName = windowName;
		this.inventory = new ItemStack[size];
		this.interactions = new ArrayList<>();
		this.interactable = false;

		this.viewers = new HashSet<>();
	}
	public static InventoryPlayer getInventoryPlayer(EntityPlayerMP playerMP) {
		inventoryPlayers.putIfAbsent(playerMP, new InventoryPlayer(playerMP, -1, null));
		return inventoryPlayers.get(playerMP);
	}
	public void reloadFor(EntityPlayerMP playerMP) {
		InventoryPlayer player = getInventoryPlayer(playerMP);
		System.out.println(player);
		if (player.inventory != this) return;
		open(playerMP);
	}
	public void open(EntityPlayerMP player) {
		player.displayGUIChest(this);
		Integer id;
		try {
			Field f = EntityPlayerMP.class.getDeclaredField("currentWindowId");
			f.setAccessible(true);
			id = (Integer) f.get(player);
			f.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
		InventoryPlayer invPlayer = getInventoryPlayer(player);
		invPlayer.id = id;
		invPlayer.inventory = this;
		viewers.add(invPlayer);
    }

	public Set<InventoryPlayer> getViewers() {
		return this.viewers;
	}

	public void close(EntityPlayer player) {
		viewers.forEach(it -> {
			if (it.playerMP == player) {
				it.playerMP.playerNetServerHandler.sendPacket(new Packet101CloseWindow(it.id));
			}
		});
	}

	public void closeAllViewers() {
		viewers.forEach(it -> close(it.playerMP));
	}

	public void removePlayer(EntityPlayer player) {
		System.out.println("closed inventory");
		viewers.removeIf(it -> it.playerMP == player);
		InventoryPlayer invPlayer = getInventoryPlayer((EntityPlayerMP) player);
		this.viewers.remove(invPlayer);
		invPlayer.inventory = null;
		invPlayer.id = -1;
	}

	public void addInteraction(InventoryInteract i) {
		interactions.add(i);
	}

	public ArrayList<InventoryInteract> getInteractions() {
		return this.interactions;
	}

	public void setInteractable(boolean b) {
		this.interactable = b;
	}

	public boolean isInteractable() {
		return this.interactable;
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (!this.interactable) return null;
		if (this.inventory[i] != null) {
			ItemStack itemstack1;
			if (this.inventory[i].stackSize <= j) {
				itemstack1 = this.inventory[i];
				this.inventory[i] = null;
				this.onInventoryChanged();
				return itemstack1;
			} else {
				itemstack1 = this.inventory[i].splitStack(j);
				if (this.inventory[i].stackSize <= 0) {
					this.inventory[i] = null;
				}

				this.onInventoryChanged();
				return itemstack1;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.inventory[i] = itemStack;
	}

	@Override
	public String getInvName() {
		return this.windowName;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.interactable;
	}

	@Override
	public void sortInventory() {
		if (!this.interactable) return;
		InventorySorter.sortInventory(inventory);
	}
	// TODO
}
