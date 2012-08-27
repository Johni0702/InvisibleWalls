package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.Zone;

import org.bukkit.entity.Player;

public class SelectExecutor 
{
	private InvisibleWallsCommandExecutor executor;
	
	protected SelectExecutor(InvisibleWallsCommandExecutor e)
	{
		executor = e;
	}
	
	protected void handleCommand(Player sender, int zoneId)
	{
		Zone zone = executor.plugin.getZone(zoneId);
		if (zone == null)
		{
			sender.sendMessage("[InvisibleWalls] That zone does not exist. To check in wich zones you are now type /iw info");
			return;
		}
		
		executor.plugin.setSelectedZone(sender, zone);
		sender.sendMessage("[InvisibleWalls] Zone " + zone.getId() + " selected.");
	}
}
