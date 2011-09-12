package ca.strendin.ButtonTeleport;

import java.util.ArrayList;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.Event.Priority;

public class ButtonTeleport extends JavaPlugin {

    private final BTPlayerListener playerListener = new BTPlayerListener(this);
    private final BTBlockListener blockListener = new BTBlockListener(this);
    public static ArrayList<Player> onlinePlayers = new ArrayList<Player>();
    
    public static ButtonTeleport plugin;
    
    @Override
    public void onDisable() {
        System.out.println(this.getDescription().getName() + " disabled");
    }
    
    @Override
    public void onEnable() {
        System.out.println(this.getDescription().getName() + " v" + this.getDescription().getVersion() + " enabled");
        
        CuboidRegionHandler.initRegions(this);
        
        // Register Events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.REDSTONE_CHANGE, this.blockListener, Event.Priority.Normal, this);

        pm.registerEvent(Event.Type.PLAYER_INTERACT, this.playerListener, Priority.Normal, this);        
        pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);
        
        //pm.registerEvent(Event.Type., this.blockListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
        
        // Commands
        getCommand("btr").setExecutor(new BTRegionCommand(this));
        getCommand("bt").setExecutor(new BTRegionCommand(this));
    }
}
