package me.rebeltrooper.yoisho.listeners;

import net.minecraft.server.v1_12_R1.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerPlaceListener implements Listener
{	
	@EventHandler
	public void onSpawnerPlace(BlockPlaceEvent event)
	{
		Block block = event.getBlock();
		
		if (block.getType() == Material.MOB_SPAWNER)
		{
			Player player = event.getPlayer();
			int slot = player.getInventory().getHeldItemSlot();
			
			NBTTagCompound tag = ((CraftPlayer)player).getHandle().inventory.getItem(slot).getTag();
			
			if (tag.getCompound("BlockEntityTag") != null
					&& tag.getCompound("BlockEntityTag").getCompound("SpawnData") != null
					&& tag.getCompound("BlockEntityTag").getCompound("SpawnData").getString("id") != null)
			{
				String strEntity = tag.getCompound("BlockEntityTag").getCompound("SpawnData").getString("id");
				
				@SuppressWarnings("deprecation")
				EntityType entType = EntityType.fromName(strEntity.substring(strEntity.indexOf(":") + 1));
				
				if (entType != null)
				{
					BlockState blockState = block.getState();
					
					CreatureSpawner spawner = (CreatureSpawner)blockState;
					spawner.setSpawnedType(entType);
					
					blockState.update();
				}
			}
		}
	}
	
	
}
