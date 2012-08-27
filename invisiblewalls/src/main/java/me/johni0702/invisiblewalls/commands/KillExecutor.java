package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.Zone;

import org.bukkit.entity.Player;

public class KillExecutor 
{
	private InvisibleWallsCommandExecutor executor;
	
	protected KillExecutor(InvisibleWallsCommandExecutor e)
	{
		executor = e;
	}
	
	protected void handleCommand(Player sender, String arg)
	{
		Zone zone = executor.plugin.getSelectedZone(sender);
		if (zone == null)
		{
			sender.sendMessage("[InvisibleWalls] Please select an zone first. (Use: /iw select <zoneId>)");
			return;
		}
		
		if (arg.equalsIgnoreCase("true"))
			zone.setKillZone(true);
		else if (arg.equalsIgnoreCase("false"))
			zone.setKillZone(false);
		else
		{
			sender.sendMessage("[InvisibleWalls] Use \"true\" or \"false\"");
			return;
		}

		executor.plugin.save();
		sender.sendMessage("[InvisibleWalls] Done!");
	}
}
