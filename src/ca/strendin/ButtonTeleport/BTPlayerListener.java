package ca.strendin.ButtonTeleport;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BTPlayerListener extends PlayerListener {
    public static ButtonTeleport plugin;
    
    public BTPlayerListener(ButtonTeleport thisplugin) {
        plugin = thisplugin;
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        // Clean up some things here
    }
    
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (BTPermissions.canCreateRegions(event.getPlayer())) {
          if (event.getPlayer().getItemInHand().getTypeId() == 281) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                CuboidRegionHandler.getRegionInfoHere(event.getPlayer(), event.getClickedBlock());
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                CuboidRegionHandler.inputCoordinate(event.getPlayer(), event.getClickedBlock());
            }
          }
        }
    }
}
