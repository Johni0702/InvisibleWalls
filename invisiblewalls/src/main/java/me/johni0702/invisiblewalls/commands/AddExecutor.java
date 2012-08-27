package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.Zone;

import org.bukkit.entity.Player;

public class AddExecutor 
{
	private InvisibleWallsCommandExecutor executor;
	
	protected AddExecutor(InvisibleWallsCommandExecutor e)
	{
		executor = e;
	}
	
	protected void handleCommand(Player sender, String[] names)
	{
		Zone zone = executor.plugin.getSelectedZone(sender);
		if (zone == null)
		{
			sender.sendMessage("[InvisibleWalls] Please select an zone first. (Use: /iw select <zoneId>)");
			return;
		}
		
		StringBuilder str = new StringBuilder();
		for (String s : names)
		{
			if (!s.startsWith("g:"))
			{
				if (zone.addPlayer(s))
					str.append(s).append(',');
			}
			else
			{
				if (zone.addGroup(s.substring(2)))
					str.append(s).append(',');
			}
		}
		if (str.length() > 0)
			str.setLength(str.length()-1);

		executor.plugin.save();
		sender.sendMessage("[InvisibleWalls] Added: " + str.toString());
	}
}
