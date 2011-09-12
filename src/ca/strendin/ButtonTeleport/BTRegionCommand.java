package ca.strendin.ButtonTeleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BTRegionCommand implements CommandExecutor {
    private final ButtonTeleport plugin;
    
    public BTRegionCommand(ButtonTeleport plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player requestplayer = (Player)sender;
           if (BTPermissions.canCreateRegions(requestplayer)) { 
            if (args.length > 0) {
                String param = args[0].toLowerCase();                
                if (param.toLowerCase().equals("create")) {
                    HandleCreateCommand(requestplayer,args);
                } else if (param.toLowerCase().equals("dest")) {
                    HandleDestCommand(requestplayer,args);                    
                } else if (param.toLowerCase().equals("list")) {
                    HandleListCommand(requestplayer,args);                    
                } else if (param.toLowerCase().equals("remove")) {
                    HandleRemoveCommand(requestplayer,args);               
                } else if (param.toLowerCase().equals("load")) {
                    HandleLoadCommand(requestplayer,args);
                } else if (param.toLowerCase().equals("save")) {
                    HandleSaveCommand(requestplayer,args);
                } else if (param.toLowerCase().equals("info")) {
                    HandleInfoCommand(requestplayer,args);
                }
            } else {
                BTLogging.sendPlayer(requestplayer, "/btr");
                BTLogging.sendPlayerInfo(requestplayer, "    create <name>");
                BTLogging.sendPlayerInfo(requestplayer, "    info <region name>");                    
                BTLogging.sendPlayerInfo(requestplayer, "    remove <region name>");
                BTLogging.sendPlayerInfo(requestplayer, "    list");                
                BTLogging.sendPlayerInfo(requestplayer, "    save");
                BTLogging.sendPlayerInfo(requestplayer, "    load");
                BTLogging.sendPlayerInfo(requestplayer, "    dest <region name>");
            }
            
           }
        } else {
            BTLogging.sendConsoleOnly("This command is designed for players only, sorry");
        }        
        return true;
    }
    
    private void HandleDestCommand(Player player, String[] args) {
       if (args.length > 1) {
           CuboidRegionHandler.setDestination(player, args[1]);           
       } else {
           BTLogging.sendPlayerError(player, "Region name required");
           BTLogging.sendPlayerError(player, "Usage /btr dest <name>");
       }
             
        
    }

    private void HandleSaveCommand(Player player, String[] args) {
        CuboidRegionHandler.saveAllRegions();
    }
    
    private void HandleListCommand(Player player, String[] args) {
        CuboidRegionHandler.listRegions(player);        
    }
    
    private void HandleLoadCommand(Player play, String[] args) {
        CuboidRegionHandler.initRegions(plugin);
    }
    
    private void HandleRemoveCommand(Player player, String[] args) {
     // Make sure the player entered a name
        if (args.length > 1) {
            CuboidRegionHandler.removeRegion(player,args[1]);                            
        } else {
            BTLogging.sendPlayerError(player, "Region name required");
            BTLogging.sendPlayerError(player, "Usage /btr remove <name>");
        }
    }
    
    
    
    private void HandleInfoCommand(Player player, String[] args) {
        if (args.length > 1) {
            String regionName = args[1];
            CuboidRegion specifiedRegion = CuboidRegionHandler.getRegionByName(regionName);
            
            if (specifiedRegion != null) {
                CuboidRegionHandler.sendRegionInfo(player,specifiedRegion);
            } else {
                BTLogging.sendPlayerError(player, "Region \""+regionName+"\" not found");
            }
        } else {
            BTLogging.sendPlayerError(player, "Region name required");
            BTLogging.sendPlayerError(player, "Usage /btr info <name>");
        }
    }
    
    private void HandleCreateCommand(Player player, String[] args) {
        if (args.length > 1) {
            CuboidRegionHandler.createRegion(player,args[1]);                            
        } else {
            BTLogging.sendPlayerError(player, "Region name required");
            BTLogging.sendPlayerError(player, "Usage /btr create <name>");
        }
    }
    

}
