// OtherDrops - a Bukkit plugin
// Copyright (C) 2011 Robert Sargant, Zarius Tularial, Celtic Minstrel
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.	 If not, see <http://www.gnu.org/licenses/>.

package com.gmail.zariust.otherdrops;

import static com.gmail.zariust.common.Verbosity.EXTREME;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zariust.common.Verbosity;
import com.gmail.zariust.otherdrops.listener.OdBlockGrowListener;
import com.gmail.zariust.otherdrops.listener.OdBlockListener;
import com.gmail.zariust.otherdrops.listener.OdBlockPlaceListener;
import com.gmail.zariust.otherdrops.listener.OdEntityListener;
import com.gmail.zariust.otherdrops.listener.OdFishingListener;
import com.gmail.zariust.otherdrops.listener.OdPlayerConsumeListener;
import com.gmail.zariust.otherdrops.listener.OdPlayerJoinListener;
import com.gmail.zariust.otherdrops.listener.OdPlayerListener;
import com.gmail.zariust.otherdrops.listener.OdPlayerMoveListener;
import com.gmail.zariust.otherdrops.listener.OdPlayerRespawnListener;
import com.gmail.zariust.otherdrops.listener.OdProjectileHitListener;
import com.gmail.zariust.otherdrops.listener.OdRedstoneListener;
import com.gmail.zariust.otherdrops.listener.OdSpawnListener;
import com.gmail.zariust.otherdrops.listener.OdVehicleListener;

public class OtherDrops extends JavaPlugin {
    public static OtherDrops     plugin;
    boolean                      enabled;
    public PluginDescriptionFile info   = null;
    static String                pluginName = "";
    static String                pluginVersion = "";
    static Logger logger = Logger.getLogger("Minecraft");
    public Log log = null;
    public SectionManager sectionManager;

    // Global random number generator - used throughout the whole plugin
    public static Random         rng    = new Random();

    // Config stuff
    public OtherDropsConfig      config = null;
    protected boolean            enableBlockTo;
    protected boolean            disableEntityDrops;

    public OtherDrops() {
        plugin = this;
        this.sectionManager = new SectionManager(this);
    }

    @Override
    public void onEnable() {
        initLogger();
        registerParameters();
        initConfig();
        registerCommands();
        Log.logInfo("OtherDrops loaded.");
    }

    private void registerCommands() {
        this.getCommand("od").setExecutor(new OtherDropsCommand(this));
    }

    private void initConfig() {
        // Create the data folder (if not there already) and load the config
        getDataFolder().mkdirs();
        config = new OtherDropsConfig(this);
        config.load(null); // load global config, dependencies then scan drops file
    }

    private void initLogger() {
        // Set plugin name & version, this must be at the start of onEnable
        // Used in log messages throughout
        pluginName = this.getDescription().getName();
        pluginVersion = this.getDescription().getVersion();
        this.log = new Log();
    }

    private void registerParameters() {
        com.gmail.zariust.otherdrops.parameters.Action.registerDefaultActions();
    }

    @Override
    public void onDisable() {
        Log.logInfo("Unloaded.");
    }

    public static void enableOtherDrops() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        String registered = "Loaded listeners: ";

        if (OtherDropsConfig.dropForBlocks) {
            registered += "Block, ";
            pm.registerEvents(new OdBlockListener(plugin), plugin);
            // registered += "PistonListener, ";
            // pm.registerEvents(new OdPistonListener(plugin), plugin);

        }
        if (OtherDropsConfig.dropForCreatures) {
            registered += "Entity, ";
            pm.registerEvents(new OdEntityListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForClick) {
            registered += "Player (left/rightclick), ";
            pm.registerEvents(new OdPlayerListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForFishing) {
            registered += "Fishing, ";
            pm.registerEvents(new OdFishingListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForSpawned) {
            registered += "MobSpawn, ";
            pm.registerEvents(new OdSpawnListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForRedstoneTrigger) {
            registered += "Redstone, ";
            pm.registerEvents(new OdRedstoneListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForPlayerJoin) {
            registered += "PlayerJoin, ";
            pm.registerEvents(new OdPlayerJoinListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForPlayerRespawn) {
            registered += "PlayerRespawn, ";
            pm.registerEvents(new OdPlayerRespawnListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForPlayerConsume) {
            registered += "PlayerConsume, ";
            pm.registerEvents(new OdPlayerConsumeListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForPlayerMove) {
            registered += "Playermove, ";
            pm.registerEvents(new OdPlayerMoveListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForBlockGrow) {
            registered += "BlockGrow, ";
            pm.registerEvents(new OdBlockGrowListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForProjectileHit) {
            registered += "ProjectileHit, ";
            pm.registerEvents(new OdProjectileHitListener(plugin), plugin);
        }
        if (OtherDropsConfig.dropForBlockPlace) {
            registered += "BlockPlace, ";
            pm.registerEvents(new OdBlockPlaceListener(plugin), plugin);
        }
        registered += "Vehicle.";
        pm.registerEvents(new OdVehicleListener(plugin), plugin);

        // BlockTo seems to trigger quite often, leaving off unless explicitly
        // enabled for now
        if (OtherDropsConfig.enableBlockTo) {
            // pm.registerEvent(Event.Type.BLOCK_FROMTO, blockListener,
            // config.priority, this);
        }

        plugin.enabled = true;
        Log.logInfo("Register listeners: " + registered, Verbosity.HIGH);
    }

    public static void disableOtherDrops() {
        HandlerList.unregisterAll(plugin);
        plugin.enabled = false;
    }

    public List<String> getGroups(Player player) {
        List<String> foundGroups = new ArrayList<String>();
        Set<PermissionAttachmentInfo> permissions = player
                .getEffectivePermissions();
        for (PermissionAttachmentInfo perm : permissions) {
            String groupPerm = perm.getPermission();
            if (groupPerm.startsWith("group."))
                foundGroups.add(groupPerm.substring(6));
            else if (groupPerm.startsWith("groups."))
                foundGroups.add(groupPerm.substring(7));
        }
        return foundGroups;
    }

    public static boolean inGroup(Player agent, String group) {
        return agent.hasPermission("group." + group)
                || agent.hasPermission("groups." + group);
    }

    // TODO: This is only for temporary debug purposes.
    public static void stackTrace() {
        if (OtherDropsConfig.verbosity.exceeds(EXTREME))
            Thread.dumpStack();
    }
}
