package me.johni0702.invisiblewalls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import me.johni0702.invisiblewalls.commands.InvisibleWallsCommandExecutor;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class InvisibleWallsPlugin extends JavaPlugin
{
	private ArrayList<Zone> zones;
	private Hashtable<Player,Zone> selectedZones = new Hashtable<Player, Zone>();
	private WorldEdit we;
	public static PermissionHook perms;
	
	@Override
	public void onEnable()
	{
		we = ((WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit")).getWorldEdit();
		
		getCommand("iw").setExecutor(new InvisibleWallsCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new InvisibleWallsListener(this), this);
		
		if (getServer().getPluginManager().isPluginEnabled("Vault"))
			setupPermissions();
		else
			perms = new PermissionHook();
		
		try {
			InvisibleWallsSaveFile.init(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		zones = InvisibleWallsSaveFile.getZones();
	}
	
	private void setupPermissions() 
	{
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = new PermissionHook(rsp.getProvider());
    }

	public WorldEdit getWorldEdit() 
	{
		return we;
	}
	
	public void setSelectedZone(Player p, Zone z)
	{
		if (z == null)
			selectedZones.remove(p);
		else
			selectedZones.put(p, z);
	}
	
	public void addZone(Zone zone)
	{
		zones.add(zone);
	}
	
	public Zone getZone(int zoneId)
	{
		for (Zone z : zones)
			if (z.getId() == zoneId)
				return z;
		return null;
	}

	public Zone getSelectedZone(Player sender) 
	{
		if (sender == null || !selectedZones.containsKey(sender))
			return null;
		else
			return selectedZones.get(sender);
	}

	public void removeZone(Zone zone) 
	{
		if (zone != null)
			zones.remove(zone);
	}
	
	public Zone canPlayerWalkTo(Player player, Location target)
	{
		for (Zone z : zones)
		{
			if (!z.proceed(player, target))
				return z;
		}
		return null;
	}
	
	public ArrayList<Zone> getAllZones(Location loc)
	{
		ArrayList<Zone> result = new ArrayList<Zone>();
		for (Zone z : zones)
		{
			if (z.isInside(loc))
				result.add(z);
		}
		return result;
	}

	public void save() 
	{
		InvisibleWallsSaveFile.saveZones(zones);
	}
}
