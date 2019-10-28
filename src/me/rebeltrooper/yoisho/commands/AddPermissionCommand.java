package me.rebeltrooper.yoisho.commands;

import java.util.ArrayList;
import java.util.UUID;

import me.rebeltrooper.yoisho.Warp;
import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPermissionCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public AddPermissionCommand(Yoisho plugin)
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
	    
	    if (args.length == 2)
	    {
	        String warpName = args[0];
	        String username = args[1];
	        
	        Warp warp = m_config.getWarp(warpName);
	        
	        if (warp != null)
	        {
	            if (warp.isPrivate)
	            {
	                if (warp.ownerUUID.equals(player.getUniqueId())
	                		|| player.hasPermission("yoisho.addotherperm"))
	                {
	                    Player toAdd = Bukkit.getPlayer(username);
	                    
	                    if (toAdd != null)
	                    {
	                        if (!warp.whitelistUUIDs.contains(toAdd.getUniqueId()))
	                        {
		                        ArrayList<UUID> newWhitelist = warp.whitelistUUIDs;
		                        newWhitelist.add(toAdd.getUniqueId());
		                        
		                        m_config.deleteWarp(warpName);
		                        m_config.setWarp(warp.name, warp.location, warp.ownerUUID, warp.isPermanent, warp.isPrivate, newWhitelist);      // or whatever

		                        player.sendMessage(ChatColor.GREEN + "Player '" + username + "' added to '" + warpName + "' whitelist!");
		                        player.sendMessage(ChatColor.GRAY + "Type /viewwhitelist <warpName> to view whitelisted players!");
		                        return true;	                        	
	                        }
		                    
		                    player.sendMessage(ChatColor.RED + "Player '" + username + "' is already whitelisted on this warp!");
	                        player.sendMessage(ChatColor.GRAY + "Type /viewwhitelist <warpName> to view whitelisted players!");
		                    return false;   
	                    }
	                    
	                    player.sendMessage(ChatColor.RED + "Player '" + username + "' not found!");
	                    return false;
	                }
	                    
	                player.sendMessage(ChatColor.RED + "You cannot edit other's warps!");
	                return false;
	            }
	                    
	            player.sendMessage(ChatColor.RED + "You cannot edit a public warp!");
	            return false;
	        }
	                    
	        player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' not found!");
	        return false;
	    }
	                    
	    player.sendMessage(ChatColor.RED + "Usage: /addpermission <warpName> <username>");
	    return false;
	}

}
