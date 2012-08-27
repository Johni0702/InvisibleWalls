package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.InvisibleWallsSaveFile;
import me.johni0702.invisiblewalls.Zone;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionOperationException;

public class CreateExecutor 
{
	private InvisibleWallsCommandExecutor executor;
	
	protected CreateExecutor(InvisibleWallsCommandExecutor e)
	{
		executor = e;
	}
	
	@SuppressWarnings("deprecation")
	protected void handleCommand(Player sender, String str) throws IncompleteRegionException, RegionOperationException 
	{
		LocalSession session = executor.plugin.getWorldEdit().getSession(sender.getName());
		if (session == null)
		{
			sender.sendMessage("[InvisibleWalls] Please make an world edit selection first.");
			return;
		}
		
		Region region = session.getSelection(session.getSelectionWorld());
		Region wallRegion = new CuboidRegion(region.getMaximumPoint(),region.getMinimumPoint());
		wallRegion.expand(new Vector(1,-1,1));
		Zone zone  = new Zone(wallRegion,false);
		zone.setOnEnter(str);
		executor.plugin.addZone(zone);
		executor.plugin.setSelectedZone(sender, zone);
		executor.plugin.save();
		sender.sendMessage("[InvisibleWalls] Zone " + zone.getId() + " created and selected.");
	}
}
