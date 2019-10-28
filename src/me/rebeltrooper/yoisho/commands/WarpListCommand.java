package me.rebeltrooper.yoisho.commands;

import java.util.ArrayList;

import me.rebeltrooper.yoisho.Warp;
import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpListCommand implements CommandExecutor
{
	private YoishoConfig m_config;
	private final int MAX_WARPS = 7;
	
	public WarpListCommand(Yoisho plugin)
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
		
		switch (args.length)
		{
		case 0:
			return showPage(player, 1);
		case 1:
			return showPage(player, args[0]);
		default:
			player.sendMessage(ChatColor.RED + "Usage: /warps <page>");
			
		}
		
		
		/*
		if (! (sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Thou art not a player!");
			return false;
		}
		
		Player player = (Player)sender;
		ArrayList<Warp> allWarps = m_config.getAllWarps();
		
		boolean canSeePrivate = player.hasPermission("yoisho.seeprivatewarps");
		UUID playerUUID = player.getUniqueId();

		ArrayList<Warp> warps = new ArrayList<>();
		
		for (Warp warp : allWarps)
		{
			if ((warp.isPrivate() && canSeePrivate)
					|| (warp.isPrivate() && warp.getOwnerUUID().equals(playerUUID))
					|| !warp.isPrivate())
			{
				warps.add(warp);
			}
		}
		
		if (args.length > 1)
		{
			player.sendMessage(ChatColor.RED + "Usage: /warps [pageNum]");
			return false;
		}
		
		int pages = (int)(warps.size() / MAX_WARPS) + 1;

		int page;
		int start;

		if (args.length == 1)
		{
			String pageStr = args[0];
			
			try
			{
				page = Integer.parseInt(pageStr);
			}
			catch (NumberFormatException e)
			{
				player.sendMessage(ChatColor.RED + "'" + pageStr + "' is not a number!");
				return false;
			}
			
			start = (int)((page - 1) * MAX_WARPS) + 1;
			
			if (start > warps.size() || start <= 0)
			{
				player.sendMessage(ChatColor.RED + "Page " + page + " doesn't exist!");
				return false;
			}
		}
		else
		{
			page = 1;
			start = 1;
		}
		
		int counter = 1;
		int end = page * MAX_WARPS;

		player.sendMessage("");
		player.sendMessage(ChatColor.GRAY + "-+*+-+*+-+*+-+*+-" + ChatColor.DARK_AQUA + ChatColor.BOLD
				+ " Page " + page + "/" + pages + ChatColor.GRAY
				+ " -+*+-+*+-+*+-+*+- ");
		
		if (pages > 1) 
		{
			player.sendMessage("> Use /warps <num> to navigate between pages");
		}
		player.sendMessage("");
		
		for (Warp warp : warps)
		{
			if (counter > end)
			{
				break;
			}
			
			if (counter < start)
			{
				counter++;
				continue;
			}
			
			if ((warp.isPrivate() && canSeePrivate)
					|| (warp.isPrivate() && warp.getOwnerUUID().equals(playerUUID)))
			{
				player.sendMessage(" " + warp.getName());				
			}
			else if (!warp.isPrivate())
			{
				if (warp.isPermanent()) 
				{
					player.sendMessage(" " + ChatColor.GRAY + ChatColor.BOLD + warp.getName());		
				}
				else
				{
					player.sendMessage(" " + ChatColor.GRAY + warp.getName());
				}
			}
			else
			{				
				continue;
			}
			
			counter++;
		}

		player.sendMessage("");
		player.sendMessage(" " + ChatColor.BOLD + "Bold " + ChatColor.RESET + "warps are permanent");
		player.sendMessage(" White warps are private");

		player.sendMessage(ChatColor.GRAY + "-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-");
		*/
		return false;
	}

	private boolean showPage(Player player, String page)
	{
		try
		{
			return showPage(player, Integer.parseInt(page));
		}
		catch (NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "'" + page + "' is not a valid integer!");
		}
		
		return false;
	}

	private boolean showPage(Player player, int page)
	{
		ArrayList<Warp> allWarps = m_config.getAllWarps();
		ArrayList<Warp> warps = new ArrayList<>();
		ArrayList<String> warpPage = new ArrayList<>();
		
		for (Warp warp : allWarps)
		{
			if (!warp.isPrivate
					|| (warp.isPrivate && warp.ownerUUID.equals(player.getUniqueId()))
					|| player.hasPermission("yoisho.viewprivatewarps")
					|| player.hasPermission("yoisho.warpprivate"))
			{
				warps.add(warp);
			}
		}
		
		int pages = warps.size() / MAX_WARPS + 1;
		
		if (page > 0 && page <= pages)
		{
			warpPage.add("");
			warpPage.add(ChatColor.GRAY + "-+*+-+*+-+*+-+*+-" + ChatColor.DARK_AQUA + ChatColor.BOLD
					+ " Page " + page + "/" + pages + ChatColor.GRAY
					+ " -+*+-+*+-+*+-+*+- ");
			
			if (pages > 1) 
			{
				warpPage.add("> Use /warps <num> to navigate between pages");
			}

			warpPage.add("");
			
			int start = (page - 1) * MAX_WARPS;
			int end = page * MAX_WARPS - 1;
			
			for (int i = start; i <= end && i < warps.size(); i++)
			{
				Warp warp = warps.get(i);

				if (warp.isPermanent) 
				{
					warpPage.add(" " + ChatColor.GRAY + ChatColor.BOLD + warp.name);		
				}
				else if (warp.isPrivate)
				{
					warpPage.add(" " + ChatColor.WHITE + warp.name);
				}
				else
				{
					warpPage.add(" " + ChatColor.GRAY + warp.name);
				}
			}

			warpPage.add("");
			warpPage.add(" " + ChatColor.BOLD + "Bold " + ChatColor.RESET + "warps are permanent");
			warpPage.add(" White warps are private");

			warpPage.add(ChatColor.GRAY + "-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-+*+-");
			
			player.sendMessage(warpPage.toArray(new String[0]));
			
			return true;
		}
		
		player.sendMessage(ChatColor.RED + "Invalid page number!");
		
		return false;
	}

}
