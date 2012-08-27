package me.johni0702.invisiblewalls.commands;

import me.johni0702.invisiblewalls.InvisibleWallsPermission;
import me.johni0702.invisiblewalls.InvisibleWallsPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.regions.RegionOperationException;

public class InvisibleWallsCommandExecutor implements CommandExecutor
{
	protected InvisibleWallsPlugin plugin;
	private CreateExecutor createExecutor;
	private SelectExecutor selectExecutor;
	private DeleteExecutor deleteExecutor;
	private AddExecutor addExecutor;
	private RemExecutor remExecutor;
	private InfoExecutor infoExecutor;
	private KillExecutor killExecutor;
	
	public InvisibleWallsCommandExecutor(InvisibleWallsPlugin plugin)
	{
		this.plugin = plugin;
		createExecutor = new CreateExecutor(this);
		selectExecutor = new SelectExecutor(this);
		deleteExecutor = new DeleteExecutor(this);
		addExecutor = new AddExecutor(this);
		remExecutor = new RemExecutor(this);
		infoExecutor = new InfoExecutor(this);
		killExecutor = new KillExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (cmd.getName().equalsIgnoreCase("iw"))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage("You have to be a player to perform this command!");
				return true;
			}
			if (args.length < 1)
			{
				sender.sendMessage("[InvisibleWalls] Please check command syntax!");
				return true;
			}
			String command = args[0];
			if (command.equalsIgnoreCase("select"))
			{
				if (args.length != 2)
				{
					sender.sendMessage("[InvisibleWalls] Use: /iw select <regionid>");
					return true;
				}
				
				try 
				{
					selectExecutor.handleCommand((Player)sender,Integer.parseInt(args[1]));
				} catch (NumberFormatException e)
				{
					sender.sendMessage("[InvisibleWalls] \"" + args[2] + "\" is not an valide number.");
				}
			}
			if (command.equalsIgnoreCase("delete"))
			{
				if (!InvisibleWallsPermission.canDelete((Player)sender))
				{
					sender.sendMessage("[InvisibleWalls] " + cmd.getPermissionMessage());
					return true;
				}
				if (args.length != 1)
				{
					sender.sendMessage("[InvisibleWalls] Use: /iw delete");
					return true;
				}
				
				deleteExecutor.handleCommand((Player)sender);
			}
			if (command.equalsIgnoreCase("kill"))
			{
				if (!InvisibleWallsPermission.canKill((Player)sender))
				{
					sender.sendMessage("[InvisibleWalls] " + cmd.getPermissionMessage());
					return true;
				}
				if (args.length != 2)
				{
					sender.sendMessage("[InvisibleWalls] Use: /iw kill <true/false>");
					return true;
				}
				
				killExecutor.handleCommand((Player)sender,args[1]);
			}
			if (command.equalsIgnoreCase("+"))
			{
				if (!InvisibleWallsPermission.canAdd((Player)sender))
				{
					sender.sendMessage("[InvisibleWalls] " + cmd.getPermissionMessage());
					return true;
				}
				if (args.length < 2)
				{
					sender.sendMessage("[InvisibleWalls] Use: /iw + [Players/Groups]");
					return true;
				}
				
				String[] names = new String[args.length-1];
				for (int i = 1; i < args.length; i++)
					names[i-1] = args[i];
				
				addExecutor.handleCommand((Player)sender,names);
			}
			if (command.equalsIgnoreCase("-"))
			{
				if (!InvisibleWallsPermission.canRem((Player)sender))
				{
					sender.sendMessage("[InvisibleWalls] " + cmd.getPermissionMessage());
					return true;
				}
				if (args.length < 2)
				{
					sender.sendMessage("[InvisibleWalls] Use: /iw - [Players/Groups]");
					return true;
				}
				
				String[] names = new String[args.length-1];
				for (int i = 1; i < args.length; i++)
					names[i-1] = args[i];
				
				remExecutor.handleCommand((Player)sender,names);
			}
			if (command.equalsIgnoreCase("create"))
			{
				if (!InvisibleWallsPermission.canCreate((Player)sender))
				{
					sender.sendMessage("[InvisibleWalls] " + cmd.getPermissionMessage());
					return true;
				}
				try 
				{
					StringBuilder str = new StringBuilder();
					for (int i = 1; i < args.length; i++)
						str.append(args[i]).append(' ');
					
					createExecutor.handleCommand((Player)sender,str.toString());
				} catch (IncompleteRegionException e) 
				{
					e.printStackTrace();
				} catch (RegionOperationException e) 
				{
					e.printStackTrace();
				}
			}
			if (command.equalsIgnoreCase("info"))
			{
				if (!InvisibleWallsPermission.canInfo((Player)sender))
				{
					sender.sendMessage("[InvisibleWalls] " + cmd.getPermissionMessage());
					return true;
				}
				infoExecutor.handleCommand((Player)sender);
			}
			return true;
		}
		return false;
	}
}
