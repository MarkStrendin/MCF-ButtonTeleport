package ca.strendin.ButtonTeleport;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CuboidRegion implements Serializable {
    private static final long serialVersionUID = -5894508785309276848L;
    private int iHighX;
    private int iHighY;
    private int iHighZ;
    private int iLowX;
    private int iLowY;
    private int iLowZ;
    private String sOwner;
    private String sName;
    private String sWorld;
    private String destWorld;
    private int iDestX;
    private int iDestY;
    private int iDestZ;
    private float iDestYaw;
    private float iDestPitch;
    public boolean hasDestination;
    
    //Location destination;

    // If the location is in a region, returns that region. Otherwise returns null.
    public static CuboidRegion getRegion(ArrayList<CuboidRegion> regionList,Location thisLocation) {
        return null;
    }
    
    // Returns true if the specified location is inside this region
    public boolean isInThisRegion(Location thisLocation) {
        
        boolean returnMe = false;
        
        if (thisLocation.getWorld().getName().equals(sWorld)) {        
            if ((thisLocation.getBlockX() >= iLowX)  && (thisLocation.getBlockX() <= iHighX)) {
                if ((thisLocation.getBlockY() >= iLowY)  && (thisLocation.getBlockY() <= iHighY)) {
                    if ((thisLocation.getBlockZ() >= iLowZ)  && (thisLocation.getBlockZ() <= iHighZ)) {
                        returnMe = true;
                    }
                }            
            }
        }
                
        return returnMe;
    }
    
    public String toString() {
        return "Region "+sName+" ("+iHighX+","+iHighY+","+iHighZ+") ("+iLowX+","+iLowY+","+iLowZ+") Owner: "+sOwner+" Destination: ("+iDestX+","+iDestY+","+iDestZ+")";        
    }
    
    public String getOwner() {
        return sOwner;
    }
    
    public String getWorld() {
        return sWorld;
    }
    
    public int getDestX() {
        return iDestX;
    }
    
    public int getDestY() {
        return iDestY;
    }
    
    public int getDestZ() {
        return iDestZ;
    }
    
    public float getDestYaw() {
        return iDestYaw;
    }
    
    public float getDestPitch() {
        return iDestPitch;
    }
    
    
    public String getDestWorld() {
        return destWorld;
    }
    
//    public Location getDestination() {
//        //return new Location(ElioTeleport.plugin.getServer().getWorld(destWorld),iDestX,iDestY,iDestZ);        
//    }
    
    public String getCoordinateString() {
        return "("+iHighX+","+iHighY+","+iHighZ+") to ("+iLowX+","+iLowY+","+iLowZ+") in world " + sWorld;        
    }
    
    public void setDestination(Location thisLocation) {
        hasDestination = true;        
        destWorld = thisLocation.getWorld().getName();
        iDestX = (int) thisLocation.getX();
        iDestY = (int) thisLocation.getY();
        iDestZ = (int) thisLocation.getZ();
        iDestYaw = thisLocation.getYaw();
        iDestPitch = thisLocation.getPitch();
            
    }
    
    public String getDestinationString() {
        if (hasDestination) {
            return "("+iDestX+","+iDestY+","+iDestZ+") in world " + destWorld;            
        } else {
            return "This region does not have a destination";   
        }
    }    
            
    public String getName() {
        return sName;
    }
    
    public CuboidRegion(String regionName, Player owner, CuboidPreRegion preRegion) {
        // For coordinates, make sure that the high and low values get sorted out properly
        sName = regionName;
        sOwner = owner.getDisplayName();
        sWorld = preRegion.loc1.getWorld().getName();
            
        
        hasDestination = false;
        destWorld = "";
        iDestX = 0;
        iDestY = 127;
        iDestZ = 0;
        
       
        // X
        if (preRegion.loc1.getBlockX() > preRegion.loc2.getBlockX()) {
            iHighX = preRegion.loc1.getBlockX();
            iLowX = preRegion.loc2.getBlockX();
        } else {
            iHighX = preRegion.loc2.getBlockX();
            iLowX = preRegion.loc1.getBlockX();            
        }
        
        // Y
        if (preRegion.loc1.getBlockY() > preRegion.loc2.getBlockY()) {
            iHighY = preRegion.loc1.getBlockY();
            iLowY = preRegion.loc2.getBlockY();
        } else {
            iHighY = preRegion.loc2.getBlockY();
            iLowY = preRegion.loc1.getBlockY();            
        }
        
        // Z
        if (preRegion.loc1.getBlockZ() > preRegion.loc2.getBlockZ()) {
            iHighZ = preRegion.loc1.getBlockZ();
            iLowZ = preRegion.loc2.getBlockZ();
        } else {
            iHighZ = preRegion.loc2.getBlockZ();
            iLowZ = preRegion.loc1.getBlockZ();            
        }       
    }
    
    
    
    
}
