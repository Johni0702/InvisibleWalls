package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.Zone;

import org.bukkit.entity.Player;

public class DeleteExecutor 
{
	private InvisibleWallsCommandExecutor executor;
	
	protected DeleteExecutor(InvisibleWallsCommandExecutor e)
	{
		executor = e;
	}
	
	protected void handleCommand(Player sender)
	{
		Zone zone = executor.plugin.getSelectedZone(sender);
		if (zone == null)
		{
			sender.sendMessage("[InvisibleWalls] Please select an zone first. (Use: /iw select <zoneId>)");
			return;
		}
		
		executor.plugin.removeZone(zone);
		executor.plugin.save();
		sender.sendMessage("[InvisibleWalls] Zone " + zone.getId() + " deleted.");
	}
}
