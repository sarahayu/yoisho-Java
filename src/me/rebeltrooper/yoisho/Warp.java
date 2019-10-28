package me.rebeltrooper.yoisho;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;

public class Warp
{
    public final String name;
    public final Location location;
    public final UUID ownerUUID;
    public final boolean isPermanent;
    public final boolean isPrivate;
    public final ArrayList<UUID> whitelistUUIDs;
    
    public Warp(String name, Location location, UUID ownerUUID, boolean isPermanent, boolean isPrivate, ArrayList<UUID> whitelistUUIDs)
    {
        this.name = name;
        this.location = location;
        this.ownerUUID = ownerUUID;
        this.isPermanent = isPermanent;
        this.isPrivate = isPrivate;
        this.whitelistUUIDs = whitelistUUIDs;
    }
    
    public Warp(String name, Location location, UUID ownerUUID, boolean isPermanent, boolean isPrivate)
    {
        this(name, location, ownerUUID, isPermanent, isPrivate, new ArrayList<UUID>());
    }
}