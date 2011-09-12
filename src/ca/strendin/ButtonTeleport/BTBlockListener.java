package ca.strendin.ButtonTeleport;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BTBlockListener extends BlockListener {
    
    public static ButtonTeleport plugin;

    public BTBlockListener(ButtonTeleport elioTeleport) {
        plugin = elioTeleport;
    }
    
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
        if (event.getBlock().getTypeId() == 77) {
            CuboidRegion thisRegion = CuboidRegionHandler.getRegionHere(event.getBlock().getLocation());
            if (thisRegion != null) {
                if (thisRegion.hasDestination) {
                    for (Player thisPlayer : plugin.getServer().getOnlinePlayers()) {
                        if (CuboidRegionHandler.getRegionHere(thisPlayer.getLocation()) == thisRegion) {                        
                            Location teleDestination = new Location(plugin.getServer().getWorld(thisRegion.getDestWorld()),thisRegion.getDestX(),thisRegion.getDestY(),thisRegion.getDestZ(),thisRegion.getDestYaw(),thisRegion.getDestPitch());                            
                            thisPlayer.teleport(teleDestination);                            
                        }
                    }
                }
            }
            // Check the list of regions to see if this button is in a region
              // Check the list of players to see if any are in the region
                // Teleport all players in the region to the destination            
        }
    }
}
