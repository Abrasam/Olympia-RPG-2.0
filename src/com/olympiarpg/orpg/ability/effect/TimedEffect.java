package com.olympiarpg.orpg.ability.effect;

import com.olympiarpg.orpg.main.OlympiaRPG;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class TimedEffect extends BukkitRunnable implements Listener {
	
	public TimedEffect(int secs) {
		OlympiaRPG.INSTANCE.getServer().getPluginManager().registerEvents(this, OlympiaRPG.INSTANCE);
		this.runTaskLater(OlympiaRPG.INSTANCE, 20*secs);
	}
	
	@Override
	public void run() {
		HandlerList.unregisterAll(this);
        end();
	}

	@Override
	public void cancel() {
		HandlerList.unregisterAll(this);
		super.cancel();
	}

	protected void end() {}
}