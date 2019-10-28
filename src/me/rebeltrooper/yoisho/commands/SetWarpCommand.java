package me.rebeltrooper.yoisho.commands;

import java.util.ArrayList;
import java.util.UUID;

import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public SetWarpCommand(Yoisho plugin)
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
	    
	    switch (args.length)
	    {
	        case 1:
	            return setWarp(player, player.getLocation(), args[0], false); // default public
	        case 2:
	            return setWarp(player, player.getLocation(), args[0], args[1]);
	        default:
	            player.sendMessage(ChatColor.RED + "Usage: /setwarp <warpName> [private]");
	    }
	    
	    return false;
	}

	private boolean setWarp(Player cmdSender, Location location, String warpName, String strIsPrivate)
	{
	    if (strIsPrivate.equalsIgnoreCase("private")
	        || strIsPrivate.equalsIgnoreCase("true"))
	    {
	        return setWarp(cmdSender, location, warpName, true);
	    }
	    
	    cmdSender.sendMessage(ChatColor.RED + "Usage: /setwarp <warpName> [private]");
	    return false;
	}

	private boolean setWarp(Player cmdSender, Location location, String warpName, boolean isPrivate)
	{
	    if (m_config.setWarp(warpName, location, cmdSender.getUniqueId(), false, isPrivate, new ArrayList<UUID>()))
	    {
	        if (isPrivate)
	        {
	            cmdSender.sendMessage(ChatColor.GREEN + "Private warp '" + warpName + "' set!");
	            cmdSender.sendMessage(ChatColor.GRAY + "Type /addpermission <warpName> <username> to add people to this warp's whitelist!");
	            return true;
	        }
	        
	        cmdSender.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' set!");
	        return true;
	    }
	    
	    cmdSender.sendMessage(ChatColor.RED + "A warp already exists with the name " + warpName + "!");
	    return false;
	}

}