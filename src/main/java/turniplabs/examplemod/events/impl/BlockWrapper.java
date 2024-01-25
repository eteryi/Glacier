package turniplabs.examplemod.events.impl;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;

public class BlockWrapper {
	public final int x;
	public final int y;
	public final int z;
	public final Block block;
	public final World world;

	public BlockWrapper(int x, int y, int z, World world) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.block = world.getBlock(x,y,z);
	}
}
