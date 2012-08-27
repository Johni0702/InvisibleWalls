package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.Zone;

import org.bukkit.entity.Player;

public class InfoExecutor 
{
	private InvisibleWallsCommandExecutor executor;
	
	protected InfoExecutor(InvisibleWallsCommandExecutor e)
	{
		executor = e;
	}
	
	protected void handleCommand(Player sender)
	{
		StringBuilder sb = new StringBuilder();
		for (Zone z : executor.plugin.getAllZones(sender.getLocation()))
			sb.append(z.getId()).append(',');
		if (sb.length() > 0)
			sb.setLength(sb.length()-1);
		sender.sendMessage("[InvisibleWalls] Zones: " + sb.toString());
	}
}
