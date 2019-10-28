package me.rebeltrooper.yoisho.commands;

import java.util.ArrayList;
import java.util.UUID;

import me.rebeltrooper.yoisho.Warp;
import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewWhitelistCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public ViewWhitelistCommand(Yoisho plugin)
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
							|| player.hasPermission("yoisho.viewwhitelistprivate"))
					{
						ArrayList<String> warpPage = new ArrayList<>();
						ArrayList<UUID> whitelist = warp.whitelistUUIDs;

						warpPage.add("");
						warpPage.add(ChatColor.GRAY + "-+*+-+*+-+*+-+*+- " + ChatColor.DARK_AQUA + ChatColor.BOLD
								+ warpName + ChatColor.GRAY
								+ " -+*+-+*+-+*+-+*+- ");
						warpPage.add("");
						
						for (UUID uuid : whitelist)
						{
							OfflinePlayer uuidPlayer = Bukkit.getOfflinePlayer(uuid);
							
							if (uuidPlayer.hasPlayedBefore())
							{
								warpPage.add(" " + ChatColor.GRAY + ChatColor.stripColor(uuidPlayer.getName()));
							}
						}

						warpPage.add("");
						warpPage.add(ChatColor.GRAY + " Type /removepermission <warpName> <username> to remove a player from a warp's whitelist!");
						warpPage.add(ChatColor.GRAY + "-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-");
						
						player.sendMessage(warpPage.toArray(new String[0]));
						return true;
					}

			        player.sendMessage(ChatColor.RED + "You cannot view this warp's whitelist!");
			        return false;
				}

		        player.sendMessage(ChatColor.RED + "This warp does not have a whitelist!");
		        return false;
				
			}

	        player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' not found!");
	        return false;
		}

        player.sendMessage(ChatColor.RED + "Usage: /viewwhitelist <warpName>");
		return false;
	}

}
