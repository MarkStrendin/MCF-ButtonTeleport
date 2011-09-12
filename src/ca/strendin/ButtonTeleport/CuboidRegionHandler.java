package ca.strendin.ButtonTeleport;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CuboidRegionHandler {   
    
    // Temporary workspace for inputting coordinates into a new region
    private static Hashtable<Player,Block> playerWorkspace = new Hashtable<Player,Block>();    
    private static Hashtable<Player,CuboidPreRegion> preRegions = new Hashtable<Player,CuboidPreRegion>();    
    private static ArrayList<CuboidRegion> regions = new ArrayList<CuboidRegion>(); 
    
    public static CuboidRegion getRegionByName(String name) {        
        for (CuboidRegion thisRegion : regions) {
            if (thisRegion.getName().toLowerCase().equals(name.toLowerCase())) {
                return thisRegion;
            }
        }
        return null;
    }
    
    // Deserializes regions
    public static void initRegions(ButtonTeleport plugin) {        
        BTLogging.logThis("Initializing regions");
        // Clear current region lists
        playerWorkspace.clear();
        preRegions.clear();
        regions.clear();
        

        // Check to see if the regions directory exists
        File regionDir = new File("btregions");
        
        if (!regionDir.exists()) {
            BTLogging.logThis("Region directory does not exist - creating");
            regionDir.mkdir();
            // Since we know there will be no regions to load, don't bother trying
            return;
        }
        
        // For each file contained in it, attempt to load
        FilenameFilter filter = new FilenameFilter() {
          public boolean accept(File dir, String name) {
              return name.endsWith(".btregion");          
          }
        };
        
        String[] regionFileNames = regionDir.list(filter);
        
        for (String thisRegionFile : regionFileNames) {
            try {
                FileInputStream fileIn = new FileInputStream(regionDir + "/" + thisRegionFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                regions.add((CuboidRegion) in.readObject());
                BTLogging.logThis("Loaded region: " + thisRegionFile);
                in.close();
                fileIn.close();
            } catch (Exception e) {
                BTLogging.logThis("Failed to deserialize " + thisRegionFile);
                BTLogging.logThis("Error was: " + e.getMessage());
                
            }
        }
    }
    
    
    // Serializes all regions
    public static void saveAllRegions(){        
        //TODO SAVEDIR - get from the main class somehow
        for (CuboidRegion thisRegion : regions) {
            try {
                FileOutputStream fileout = new FileOutputStream("btregions/" + thisRegion.getName() + ".btregion");
                ObjectOutputStream out = new ObjectOutputStream(fileout);
                out.writeObject(thisRegion);
                out.close();
                fileout.close();                
                BTLogging.logThis("Saved region " + thisRegion.getName());
            } catch (IOException i) {
                BTLogging.logThis("Failed to save region data for " + thisRegion.getName());
                BTLogging.logThis("Error was: " + i.getMessage());
            }
        }
    }
    
    //TODO: Actually sanitize the string
    public static String sanitizeInput(String input) {
        
        String working = null;
        
        // Only allow a region name to be 20 characters long (just because)
        // only allow a region name to be lower case
        if (input.length() > 20) {
            working = input.substring(0, 20).toLowerCase();            
        } else {
            working = input.toLowerCase();
        }
        
        // only output alphabet characters and numbers - remove anything else
        
        String REGEX = "[^a-z0-9]";
        
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(working); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
          m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        
        working = sb.toString();
        return working;
    }
    
    public static void sendRegionInfo(Player player, CuboidRegion specifiedRegion) {        
        BTLogging.sendPlayer(player, "Info for region \""+specifiedRegion.getName()+"\":");
        BTLogging.sendPlayerInfo(player," Coordinates: " + specifiedRegion.getCoordinateString());                
        BTLogging.sendPlayerInfo(player," Creator: " + specifiedRegion.getOwner());
        BTLogging.sendPlayerInfo(player," Destination: " + specifiedRegion.getDestinationString());
    }
    
    public static void removeRegion (Player thePlayer, String regionName) {
        
        boolean removedRegion = false;        
        CuboidRegion foundRegion = null;
        
        for (CuboidRegion thisRegion : regions) {
            if (thisRegion.getName().endsWith(regionName)) {
                foundRegion = thisRegion;
                removedRegion = true;
            }
        }
 
        if (foundRegion != null) {
            if (removedRegion = true) {
                regions.remove(foundRegion);
                File thisRegionFile = new File("btregions/" + foundRegion.getName() + ".btregion");
                if (thisRegionFile.exists()) {
                    thisRegionFile.delete();
                }
            }
        }
        
        if (removedRegion) {            
            BTLogging.sendPlayer(thePlayer, "Region removed");
        } else {
            BTLogging.sendPlayerError(thePlayer, "Region not found");
        }
    }    
    
    public static void createRegion(Player thePlayer, String regionName) {
        // Coordinates should already be stored in the preRegions hashtable        
        
        // Check for a pre-region
        if (preRegions.containsKey(thePlayer)) {           
            CuboidRegion newRegion = new CuboidRegion(sanitizeInput(regionName.toLowerCase()), thePlayer,preRegions.get(thePlayer));
            if (doesRegionNameExist(regionName)) {
                BTLogging.sendPlayerError(thePlayer, "Region with that name already exists!");
            } else {
              regions.add(newRegion);
              saveAllRegions();            
              BTLogging.sendPlayer(thePlayer, "New region created: " + ChatColor.AQUA + sanitizeInput(regionName.toLowerCase()));
            }
        } else {
            BTLogging.sendPlayerError(thePlayer, "Not ready to create a region yet!");
        }        
    }
    
    public static void inputCoordinate(Player thePlayer, Block theBlock) {               
        
        // If there is already a saved pre-region, delete it
        if (preRegions.containsKey(thePlayer)) {
            preRegions.remove(thePlayer);
        }
        
        
        // If the player has a block stored already, create a region
        if (playerWorkspace.containsKey(thePlayer)) {
           CuboidPreRegion preRegion = new CuboidPreRegion(theBlock,playerWorkspace.get(thePlayer));
           
           playerWorkspace.remove(thePlayer);
           
           if (preRegions.containsKey(thePlayer)) {
               preRegions.remove(thePlayer);
           }            
           
           preRegions.put(thePlayer, preRegion);
           
           BTLogging.sendPlayer(thePlayer,"Ready to create a region!");  
           BTLogging.sendPlayerInfo(thePlayer,"Use \"/btr create <name>\" to create a region");  
        } else {
        // If the player does not have a block stored already, just store it
            playerWorkspace.put(thePlayer, theBlock);
            BTLogging.sendPlayer(thePlayer,"Location 1 stored. Select another block to complete the region boundary");            
        }
    }

    public static void listRegions(Player thePlayer) {
        BTLogging.sendPlayer(thePlayer, "All regions:");
        for (CuboidRegion thisRegion : regions) {
            BTLogging.sendPlayer(thePlayer, " " + thisRegion.toString());
        }
    }
    
    public static void setDestination(Player thePlayer, String regionName) {
        
        boolean didThisWork = false;        
        CuboidRegion foundRegion = null;
        
        for (CuboidRegion thisRegion : regions) {
            if (thisRegion.getName().endsWith(regionName)) {
                foundRegion = thisRegion;
                didThisWork = true;
            }
        }
 
        if (foundRegion != null) {
            if (didThisWork = true) {
                foundRegion.setDestination(thePlayer.getLocation());
            }
        }
        
        if (didThisWork) {
            saveAllRegions();
            BTLogging.sendPlayer(thePlayer, "Destination set for region \""+foundRegion.getName()+"\"");
        } else {
            BTLogging.sendPlayerError(thePlayer, "Region not found");
        }
         
    }
    
    public static void getRegionInfoHere(Player thePlayer, Block thisBlock) {
        // Go through list of regions and check
        
        Location blockLocation = thisBlock.getLocation();
        
        boolean foundMatch = false;
        
        for (CuboidRegion thisRegion : regions) {
            if (thisRegion.isInThisRegion(blockLocation)) {
                sendRegionInfo(thePlayer,thisRegion);                               
                foundMatch = true;
            }
        }
        
        if (foundMatch == false) {
            BTLogging.sendPlayerInfo(thePlayer,"No regions here!");
        }
    }
    
    public static CuboidRegion getRegionHere(Location thisLocation) {
        // Go through list of regions and check
        
        for (CuboidRegion thisRegion : regions) {
            if (thisRegion.isInThisRegion(thisLocation)) {  
                return thisRegion;
            }
        }
        return null;
    }
    
    public static boolean doesRegionNameExist(String regionName) {                
        for (CuboidRegion thisRegion : regions) {
            if (thisRegion.getName().equalsIgnoreCase(regionName)) {
                return true;
            }            
        }        
        return false;
    }

    
    
    
    
   
}
