package cross.glacier.mixin;


import cross.glacier.events.GlacierEvents;
import cross.glacier.events.impl.TickEvent;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldServer.class, remap = false)
public abstract class WorldServerMixin extends World {

	public WorldServerMixin(LevelStorage saveHandler, String name, long seed, Dimension dimension, WorldType worldType) {
		super(saveHandler, name, seed, dimension, worldType);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo ci) {
		TickEvent event = new TickEvent(this);
		GlacierEvents.runEventsFor(TickEvent.class, event);
	}
}
