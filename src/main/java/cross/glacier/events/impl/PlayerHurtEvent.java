package cross.glacier.events.impl;

import cross.glacier.events.ServerEvent;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;

public class PlayerHurtEvent implements ServerEvent {
	public final EntityPlayer entity;
	public final Entity damager;
	public final DamageType type;

	private boolean cancel;
	private int damage;

    public PlayerHurtEvent(EntityPlayer entity, Entity damager, DamageType type, int damage) {
        this.entity = entity;
        this.damager = damager;
		this.type = type;
		this.damage = damage;
		this.cancel = false;
    }


    @Override
	public void setCancelled(boolean b) {
		this.cancel = b;
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int i) {
		this.damage = i;
	}
}
