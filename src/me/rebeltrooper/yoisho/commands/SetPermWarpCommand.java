package me.rebeltrooper.yoisho.commands;

import java.util.ArrayList;
import java.util.UUID;

import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPermWarpCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public SetPermWarpCommand(Yoisho plugin)
	{
		m_config = plugin.getYoishoConfig();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
	{
	    if (!(sender instanceof Player))
	    {
			sender.sendMessage(ChatColor.RED + "Thou art not a player!");
			return false;
	    }
	    
	    Player player = (Player)sender;
	    
	    if (player.hasPermission("yoisho.setpermwarp"))
	    {
		    if (args.length == 1)
		    {
		        String warpName = args[0];
		        
		        if (m_config.setWarp(warpName, player.getLocation(), player.getUniqueId(), true, false, new ArrayList<UUID>()))       // perms are always public
		        {
		            player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' set!");
		            return true;
		        }

				player.sendMessage(ChatColor.RED + "A warp already exists with the name " + warpName + "!");
		        return false;
		    }
		    
		    player.sendMessage(ChatColor.RED + "Usage: /setpermwarp <warpName>");
		    return false;
	    }

	    player.sendMessage(ChatColor.RED + "You do not have enough permissions!");
	    return false;
	}

}
