package ca.strendin.ButtonTeleport;

import org.bukkit.entity.Player;

public class BTPermissions {
    
    public static boolean canCreateRegions(Player player) {
        return player.hasPermission("buttonteleport.admin");
    }
}
