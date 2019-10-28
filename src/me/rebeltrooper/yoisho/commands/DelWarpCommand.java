package me.rebeltrooper.yoisho.commands;

import java.util.UUID;

import me.rebeltrooper.yoisho.Warp;
import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DelWarpCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public DelWarpCommand(Yoisho plugin)
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
	            if (warp.isPermanent)       // disregard owner if warp is permanent
	            {
	                if (player.hasPermission("yoisho.delpermwarp"))         // or whatever perm
	                {
	                	m_config.deleteWarp(warpName);
	                	
	                    player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' deleted!");
	                    return true;
	                }
	        
	                player.sendMessage(ChatColor.RED + "You do not have enough permission!");
	                return false;
	            }
	                
	            if (warp.ownerUUID.equals(player.getUniqueId())
	                || warp.ownerUUID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))
	                || player.hasPermission("yoisho.delwarpothers"))
	            {
                	m_config.deleteWarp(warpName);
                	
	                player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' deleted!");
	                return true;
	            }
	            
	            player.sendMessage(ChatColor.RED + "You cannot delete other's warps!");
	            return false;
	        }
	            
	        player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' not found!");       // or whatever message
	        return false;
	    }
	            
	    player.sendMessage(ChatColor.RED + "Usage: /delwarp <warpName>");       // or whatever message
	    return false;
	}
}
