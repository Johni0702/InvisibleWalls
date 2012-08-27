package me.johni0702.invisiblewalls;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

public class Zone 
{
	private Region region;
	private int id;
	private boolean killZone;
	private String enterString;
	private ArrayList<String> players;
	private ArrayList<String> groups;
	
	public Zone(Region region, boolean killZone)
	{
		this.region = region;
		id = getNewId();
		players = new ArrayList<String>();
		groups = new ArrayList<String>();
		this.killZone = killZone;
		enterString = "";
	}
	
	public Zone(ConfigurationSection s)
	{
		id = getNewId();
		players = new ArrayList<String>(s.getStringList("players"));
		groups = new ArrayList<String>(s.getStringList("groups"));
		region = new CuboidRegion(
				Zone.toWEVector(s.getVector("pos1")), 
				Zone.toWEVector(s.getVector("pos2")));
		killZone = s.getBoolean("kill");
		enterString = s.getString("onEnter");
	}
	
	public static Vector toWEVector(org.bukkit.util.Vector v)
	{
		return new Vector(v.getX(),v.getY(),v.getZ());
	}
	
	public static org.bukkit.util.Vector toBukkitVector(Vector v)
	{
		return new org.bukkit.util.Vector(v.getX(),v.getY(),v.getZ());
	}
	
	public int getId()
	{
		return id;
	}
	
	private static int count = 0;
	private static synchronized int getNewId()
	{
		count++;
		return count;
	}

	public boolean addPlayer(String s) 
	{
		if (players.contains(s))
			return false;
		players.add(s);
		return true;
	}

	public boolean addGroup(String s) 
	{
		if (groups.contains(s))
			return false;
		
		groups.add(s);
		return true;
	}

	public boolean removePlayer(String s) 
	{
		if (!players.contains(s))
			return false;
		players.remove(s);
		return true;
	}

	public boolean removeGroup(String s) 
	{
		if (!groups.contains(s))
			return false;
		groups.remove(s);
		return true;
	}
	
	public Region getRegion()
	{
		return region;
	}
	
	public boolean proceed(Player p, Location target)
	{
		if (!region.contains(toWEVector(target.toVector())))
			return true;
		if (players.contains(p.getName()))
			return false;
		for (String group : groups)
			if (InvisibleWallsPermission.isInGroup(p, group))
				return false;
		return true;
	}
	
	public boolean isInside(Location target)
	{
		return region.contains(toWEVector(target.toVector()));
	}

	public boolean isKillZone() 
	{
		return killZone;
	}

	public void setKillZone(boolean kill) 
	{
		killZone = kill;
	}

	public void setOnEnter(String str) 
	{
		enterString = str;
	}
	
	public String getOnEnter()
	{
		return enterString;
	}
	
	public void save(ConfigurationSection s)
	{
		s.set("pos1", toBukkitVector(region.getMaximumPoint()));
		s.set("pos2", toBukkitVector(region.getMinimumPoint()));
		s.set("kill", isKillZone());
		s.set("onEnter", getOnEnter());
		s.set("players", players);
		s.set("groups", groups);
	}
}
