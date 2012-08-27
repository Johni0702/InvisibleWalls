package me.johni0702.invisiblewalls;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class InvisibleWallsListener implements Listener
{
	private InvisibleWallsPlugin plugin;
	
	public InvisibleWallsListener(InvisibleWallsPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Zone zone = plugin.canPlayerWalkTo(e.getPlayer(), e.getTo());
		if (zone != null)
		{
			if (!e.getPlayer().isDead())
				e.getPlayer().sendMessage(zone.getOnEnter());
			if (zone.isKillZone())
			{
				if (!e.getPlayer().isDead())
					e.getPlayer().setHealth(0);
			}
			else
				e.setCancelled(true);
		}
	}
}
