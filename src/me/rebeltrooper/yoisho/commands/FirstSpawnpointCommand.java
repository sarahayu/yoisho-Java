package me.rebeltrooper.yoisho.commands;

import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FirstSpawnpointCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	
	public FirstSpawnpointCommand(Yoisho plugin)
	{
		m_config = plugin.getYoishoConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args)
	{
		if (! (sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Thou art not a player!");
			return false;
		}
		
		Player player = (Player)sender;
		
		if (!player.hasPermission("yoisho.firstspawnpoint"))
		{
			player.sendMessage(ChatColor.RED + "You do not have enough permissions!");
			return false;
		}
		
		if (args.length == 0)
		{
			m_config.setSpawnLocation(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "Spawnpoint set!");
			return true;
		}
		
		player.sendMessage(ChatColor.RED + "Usage: /firstspawnpoint");
		
		return false;
	}

}
