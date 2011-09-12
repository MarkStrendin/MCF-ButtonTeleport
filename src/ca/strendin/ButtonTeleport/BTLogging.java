package ca.strendin.ButtonTeleport;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BTLogging {
    
    public static final Logger log = Logger.getLogger("Minecraft");
    
    private static ChatColor normalColor = ChatColor.DARK_GREEN;
    private static ChatColor infoColor = ChatColor.AQUA;
    private static ChatColor errorColor = ChatColor.RED;

    // Public so that BDCommands methods can see it
    public static ChatColor itemColor = ChatColor.GREEN;
   
    
    public static void sendPlayer(Player tothisplayer, String message) {
        tothisplayer.sendMessage(normalColor + message);        
    }
    
    public static void sendPlayerInfo(Player tothisplayer, String message) {
        tothisplayer.sendMessage(infoColor + message);        
    }
    
    public static void logThis(String message) {
        log.info("[ButtonTeleport] " + message);
    }
    
    
    public static void sendConsole(String message) {
        logThis(message);        
    }
    
    public static void sendConsoleOnly(String message) {
        System.out.println(message);
    }
    
    
    public static void sendPlayerError(Player tothisplayer, String message) {
        tothisplayer.sendMessage(errorColor  + message);
    }
    
    public static void permDenyMsg(Player tothisplayer) {
        tothisplayer.sendMessage(errorColor + "You do not have permission to use that command");        
    }
    
}
