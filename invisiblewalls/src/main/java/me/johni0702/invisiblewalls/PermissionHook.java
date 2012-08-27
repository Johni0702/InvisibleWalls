package me.johni0702.invisiblewalls;

import org.bukkit.entity.Player;

import net.milkbowl.vault.permission.Permission;

public class PermissionHook 
{
	private Permission vaultPerms;
	
	public PermissionHook(Permission provider) 
	{
		vaultPerms = provider;
	}
	
	public PermissionHook() 
	{
		vaultPerms = null;
	}

	public boolean has(Player player, String permission) 
	{
		if (vaultPerms != null)
			return vaultPerms.has(player, permission);
		else
			return player.hasPermission(permission);
	}

	public boolean playerInGroup(Player player, String group) 
	{
		if (vaultPerms == null)
			return false;
		return vaultPerms.playerInGroup(player, group);
	}
}
