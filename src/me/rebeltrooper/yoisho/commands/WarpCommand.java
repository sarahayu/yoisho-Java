package me.rebeltrooper.yoisho.commands;

import me.rebeltrooper.yoisho.Warp;
import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public WarpCommand(Yoisho plugin)
	{
		m_config = plugin.getYoishoConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args)
	{
		if (! (sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Thou art not a player!");
			return false;
		}
	    
	    Player player = (Player)sender;
	    
	    if (args.length == 1)
	    {
	        String warpName = args[0];
	        
	        Warp warp = m_config.getWarp(warpName);
	    
	        if (warp != null)
	        {
	            if (warp.isPrivate)
	            {
	                if (warp.ownerUUID.equals(player.getUniqueId()) 
	                    || warp.whitelistUUIDs.contains(player.getUniqueId())
	                    || player.hasPermission("yoisho.bypasswhitelist")
	                    || player.hasPermission("yoisho.warpprivate"))
	                {
	                    player.teleport(warp.location);
	                    player.sendMessage(ChatColor.GREEN + "You have been warped to '" + warpName + "'!");
	                    
	                    return true;
	                }  
	                
	                player.sendMessage(ChatColor.RED + "You are not whitelisted on this warp!");
	                return false;
	            }
	            else
	            {
	                player.teleport(warp.location);
	                player.sendMessage(ChatColor.GREEN + "You have been warped to '" + warpName + "'!");
	                
	                return true;
	            }
	        }  
	                
	        player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' not found!");
	        return false;
	    }
	                
	    player.sendMessage(ChatColor.RED + "Usage: /warp <warpName>");
	    return false;
	}

}
