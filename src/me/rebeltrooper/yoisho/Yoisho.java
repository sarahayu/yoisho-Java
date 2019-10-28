package me.rebeltrooper.yoisho;

import me.rebeltrooper.yoisho.commands.AddPermissionCommand;
import me.rebeltrooper.yoisho.commands.DelWarpCommand;
import me.rebeltrooper.yoisho.commands.FirstSpawnpointCommand;
import me.rebeltrooper.yoisho.commands.RemovePermissionCommand;
import me.rebeltrooper.yoisho.commands.SetPermWarpCommand;
import me.rebeltrooper.yoisho.commands.SetWarpCommand;
import me.rebeltrooper.yoisho.commands.ViewWhitelistCommand;
import me.rebeltrooper.yoisho.commands.WarpCommand;
import me.rebeltrooper.yoisho.commands.WarpListCommand;
import me.rebeltrooper.yoisho.listeners.FirstJoinListener;
import me.rebeltrooper.yoisho.listeners.SpawnerPlaceListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Yoisho extends JavaPlugin
{
	private YoishoConfig yConfig;
	
	public void onEnable()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();

		yConfig = new YoishoConfig(this);

		getCommand("warp").setExecutor(new WarpCommand(this));
		getCommand("setwarp").setExecutor(new SetWarpCommand(this));
		getCommand("setpermwarp").setExecutor(new SetPermWarpCommand(this));
		getCommand("delwarp").setExecutor(new DelWarpCommand(this));
		getCommand("warps").setExecutor(new WarpListCommand(this));
		getCommand("addpermission").setExecutor(new AddPermissionCommand(this));
		getCommand("removepermission").setExecutor(new RemovePermissionCommand(this));
		getCommand("viewwhitelist").setExecutor(new ViewWhitelistCommand(this));
		getCommand("firstspawnpoint").setExecutor(new FirstSpawnpointCommand(this));

		getServer().getPluginManager().registerEvents(new FirstJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(), this);
		
	}
	
	public YoishoConfig getYoishoConfig()
	{
		return yConfig;
	}
}
