ButtonTeleport
==============

Most recent CraftBukkit version tested with: 1090

This plugin allows a server admin to create teleport regions that are activated by pressing buttons. This allows the creation of button activated "Teleport pads".

Permissions
-----------

    ButtonTeleport.Admin		Users with this permission are able to create regions. OPs have this enabled by default.

Commands
--------

    /btr create <name>			Creates a region with the provided name, assuming the boundaries have been
    							created using the region tool
    /btr info <region name>		Returns useful information about region with the specified name
    /btr list					Lists all loaded regions
    /btr remove <region name>	Removes region (by name)
    /btr save					Forces the plugin to save regions to disk
    /btr load					Forces the plugin to reload regions from disk
    /btr dest <region name>		Sets the destination of a region to the block the player is standing on

Creating a region
-----------------

The default item for this tool is the wooden bowl (281)

**Left clicking** a block will tell you if there are any regions set at that location

**Right clicking** a block will start creating a region. Clicking once will save the first location, clicking again will save the second, and inform you that you are ready to create a region using the /btr command.

Once a region is created, you will need to set a destination using the following command:

    /btr dest <name>

Where "<name>" is the name of an existing region.

Finally, place a Stone Button (Block id 77) anywhere inside the region. Pressing the button will teleport all players in the region to the region's destination.
 