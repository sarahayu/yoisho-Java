package me.rebeltrooper.yoisho.listeners;

import me.rebeltrooper.yoisho.Yoisho;
import me.rebeltrooper.yoisho.YoishoConfig;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinListener implements Listener
{
	private YoishoConfig m_config;
	
	public FirstJoinListener(Yoisho plugin)
	{
		m_config = plugin.getYoishoConfig();
	}
	
	@EventHandler
	public void onFirstJoin(PlayerJoinEvent event)
	{
		if (!event.getPlayer().hasPlayedBefore())
		{
			Location spawn = m_config.getSpawnLocation();
		
			if (spawn != null)
			{
				event.getPlayer().teleport(spawn);
			}
		}
	}
}
