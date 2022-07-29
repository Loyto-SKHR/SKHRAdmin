package fr.skhr.loyto.skhrAdmin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.skhr.loyto.skhrAdmin.build.Build;

public class PluginListener implements Listener {

	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(event.useItemInHand() == Result.DENY | player.getItemInHand().getType() == Material.AIR) {
			return;
		}
		
		World world = player.getWorld();
		Action action = event.getAction();
		
		if(action == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand().getType() == Material.STICK) {
				if(player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Builder Wand"))
				{
					if(player.hasPermission("skhr.build.bw")) {
						Block clickedBlock = event.getClickedBlock();
			
						int x = clickedBlock.getX();
						int y = clickedBlock.getY();
						int z = clickedBlock.getZ();
						BlockFace blockFace = event.getBlockFace();
						
						Build.builderWand(player, world, x, y, z, clickedBlock, blockFace);
					}
				}
			}
			
		}
	}
}
