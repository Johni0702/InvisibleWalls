package me.johni0702.invisiblewalls;

import org.bukkit.entity.Player;

public class InvisibleWallsPermission 
{
	public static boolean canCreate(Player player)
	{
		return InvisibleWallsPlugin.perms.has(player, "InvisibleWalls.create");
	}
	
	public static boolean canDelete(Player player)
	{
		return InvisibleWallsPlugin.perms.has(player, "InvisibleWalls.delete");
	}
	
	public static boolean canAdd(Player player)
	{
		return InvisibleWallsPlugin.perms.has(player, "InvisibleWalls.add");
	}
	
	public static boolean canRem(Player player)
	{
		return InvisibleWallsPlugin.perms.has(player, "InvisibleWalls.rem");
	}
	
	public static boolean canInfo(Player player)
	{
		return InvisibleWallsPlugin.perms.has(player, "InvisibleWalls.info");
	}
	
	public static boolean canKill(Player player)
	{
		return InvisibleWallsPlugin.perms.has(player, "InvisibleWalls.kill");
	}
	
	public static boolean isInGroup(Player player, String group)
	{
		return InvisibleWallsPlugin.perms.playerInGroup(player, group);
	}
}
