package me.rebeltrooper.yoisho;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class YoishoConfig
{
	private Yoisho mPlugin;
	
	YoishoConfig(Yoisho plugin)
	{
		mPlugin = plugin;
	}
	
	public void setSpawnLocation(Location location)
	{
		setLocation("spawn", location);
		mPlugin.saveConfig();
	}
	
	public Location getSpawnLocation()
	{
		if (mPlugin.getConfig().contains("spawn"))
		{
			return getLocation("spawn");
		}
		
		return null;
	}
	
	public ArrayList<Warp> getAllWarps()
	{
		ArrayList<Warp> warps = new ArrayList<>();
		
		ConfigurationSection warpSection = mPlugin.getConfig().getConfigurationSection("warps");
		
		if (warpSection == null) return warps;
		
		for (String key: warpSection.getKeys(false))
		{
			Warp warp = getWarp(key);

			warps.add(warp);
		}
		
		return warps;
	}
	
	public Warp getWarp(String warpName)
	{
		if (!mPlugin.getConfig().contains("warps." + warpName))
		{
			return null;
		}
		
		Location location = getLocation("warps." + warpName + ".location");
		boolean permanent = mPlugin.getConfig().getBoolean("warps." + warpName + ".permanent");
		
		// was implemented later - values might be null
		
		UUID owner = UUID.fromString(mPlugin.getConfig().getString("warps." + warpName + ".owner", "00000000-0000-0000-0000-000000000000"));
		boolean isPrivate = mPlugin.getConfig().getBoolean("warps." + warpName + ".private", false);		// assumed public
		
		ArrayList<String> strUUIDs = (ArrayList<String>) mPlugin.getConfig().getStringList("warps." + warpName + ".whitelist");
		ArrayList<UUID> whitelistUUIDs = new ArrayList<>();
		
		for (String strUUID : strUUIDs)
			whitelistUUIDs.add(UUID.fromString(strUUID));
		
		return new Warp(warpName, location, owner, permanent, isPrivate, whitelistUUIDs);
	}
	
	public boolean setWarp(String warpName, Location location, UUID ownerUUID, boolean permanent, boolean isPrivate, ArrayList<UUID> whitelist)
	{
		if (mPlugin.getConfig().contains("warps." + warpName))
		{
			return false;
		}
		
		ArrayList<String> strUUIDs = new ArrayList<>();
		
		for (UUID uuid : whitelist)
			strUUIDs.add(uuid.toString());
		
		setLocation("warps." + warpName + ".location", location);
		mPlugin.getConfig().set("warps." + warpName + ".permanent", permanent);
		mPlugin.getConfig().set("warps." + warpName + ".owner", ownerUUID.toString());
		mPlugin.getConfig().set("warps." + warpName + ".private", isPrivate);
		mPlugin.getConfig().set("warps." + warpName + ".whitelist", strUUIDs);
		mPlugin.saveConfig();	
		
		return true;
	}
	
	public boolean deleteWarp(String warpName)
	{
		if (!mPlugin.getConfig().contains("warps." + warpName))
		{
			return false;
		}
		
		mPlugin.getConfig().set("warps." + warpName, null);
		mPlugin.saveConfig();
		return true;
	}
	
	private Location getLocation(String path)
	{
		double x = mPlugin.getConfig().getDouble(path + ".x");
		double y = mPlugin.getConfig().getDouble(path + ".y");
		double z = mPlugin.getConfig().getDouble(path + ".z");
		float pitch = (float)mPlugin.getConfig().getDouble(path + ".pitch");
		float yaw = (float)mPlugin.getConfig().getDouble(path + ".yaw");
		World world = Bukkit.getWorld(mPlugin.getConfig().getString(path + ".world"));
		
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	private void setLocation(String path, Location location)
	{
		mPlugin.getConfig().set(path + ".x", location.getX());
		mPlugin.getConfig().set(path + ".y", location.getY());
		mPlugin.getConfig().set(path + ".z", location.getZ());
		mPlugin.getConfig().set(path + ".pitch", (double)location.getPitch());
		mPlugin.getConfig().set(path + ".yaw", (double)location.getYaw());
		mPlugin.getConfig().set(path + ".world", location.getWorld().getName());
	}
}
