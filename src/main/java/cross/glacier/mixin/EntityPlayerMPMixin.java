package cross.glacier.mixin;

import cross.glacier.events.GlacierEvents;
import cross.glacier.events.impl.PlayerHurtEvent;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Stack;

@Mixin(value = EntityPlayerMP.class, remap = false)
public class EntityPlayerMPMixin extends EntityPlayer {

	public EntityPlayerMPMixin(World world) {
		super(world);
	}

	@Unique
	private static Stack<PlayerHurtEvent> events = new Stack<>();

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void onHurt(Entity attacker, int i, DamageType type, CallbackInfoReturnable<Boolean> cir) {
		if (type == null) return;
		PlayerHurtEvent event = new PlayerHurtEvent(this, attacker, type, i);
		GlacierEvents.runEventsFor(PlayerHurtEvent.class, event);
		if (event.isCancelled()) {
			cir.setReturnValue(false);
			return;
		}
		events.push(event);
	}
	@ModifyArgs(
		method = "hurt",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/EntityPlayer;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", ordinal = 0)
	)
	public void doLogin(Args args) {
		if (events.isEmpty()) return;
		PlayerHurtEvent event = events.pop();
		args.set(1, event.getDamage());
	}

	@Override
	public void func_6420_o() {

	}
}
