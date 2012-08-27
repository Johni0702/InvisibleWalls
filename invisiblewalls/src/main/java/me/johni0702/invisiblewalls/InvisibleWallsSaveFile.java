package me.johni0702.invisiblewalls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

public class InvisibleWallsSaveFile 
{
	static String mainDir = "plugins/InvisibleWalls";
	static File file = new File(mainDir + File.separator + "zones.yml");
	static FileConfiguration config;
	
	public static void init(Plugin plugin) throws IOException, InvalidConfigurationException
	{
		config = plugin.getConfig();
		if (!file.exists())
		{
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile();
		}
		config.load(file);
	}
	
	public static ArrayList<Zone> getZones()
	{
		ArrayList<Zone> result = new ArrayList<Zone>();
		
		for (int i = 0; config.isConfigurationSection(Integer.toString(i)); i++)
			result.add(new Zone(config.getConfigurationSection(Integer.toString(i))));
		return result;
	}
	
	public static void saveZones(ArrayList<Zone> zones)
	{
		int i = 0;
		while (i < zones.size())
		{
			if (zones.get(i) == null) continue;
	
			zones.get(i).save(config.createSection(Integer.toString(i)));
			i++;
		}
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
