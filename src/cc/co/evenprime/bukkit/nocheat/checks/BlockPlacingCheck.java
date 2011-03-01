package cc.co.evenprime.bukkit.nocheat.checks;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.block.BlockPlaceEvent;

import cc.co.evenprime.bukkit.nocheat.NoCheatPlugin;


/**
 * Check if the player tries to place blocks in midair (which shouldn't be possible)
 * 
 * @author Evenprime
 *
 */
public class BlockPlacingCheck {


	public static void check(BlockPlaceEvent event) {

		// Should we check at all?
		if(NoCheatPlugin.Permissions != null && NoCheatPlugin.Permissions.has(event.getPlayer(), "nocheat.airbuild")) {
			return;
		}
		else if(NoCheatPlugin.Permissions == null && event.getPlayer().isOp() ) {
			return;
		}

		Location l = event.getBlockPlaced().getLocation();
		World w = event.getBlock().getWorld();
		int airId = Material.AIR.getId();

		// Are all 6 sides "air-blocks" -> cancel the event
		if(w.getBlockTypeIdAt(l.getBlockX()-1, l.getBlockY(), l.getBlockZ()) == airId &&
				w.getBlockTypeIdAt(l.getBlockX()+1, l.getBlockY(), l.getBlockZ()) == airId &&
				w.getBlockTypeIdAt(l.getBlockX(), l.getBlockY()-1, l.getBlockZ()) == airId &&
				w.getBlockTypeIdAt(l.getBlockX(), l.getBlockY()+1, l.getBlockZ()) == airId &&
				w.getBlockTypeIdAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()-1) == airId &&
				w.getBlockTypeIdAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()+1) == airId) {
			event.setCancelled(true);
			NoCheatPlugin.log(Level.WARNING, "NoCheatPlugin: Airbuild violation: "+event.getPlayer().getName()+" tried to place block " + event.getBlockPlaced().getType() + " in the air at " + l.getBlockX() + "," + l.getBlockY() +"," + l.getBlockZ());
		}
	}
}
