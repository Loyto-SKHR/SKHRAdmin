package fr.skhr.loyto.skhrAdmin.build;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuilderWandCommandExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			
			if(player.hasPermission("skhr.build.bw")) {
				ItemStack bw = new ItemStack(Material.STICK, 1);
				ItemMeta meta = bw.getItemMeta();
				meta.setDisplayName(ChatColor.AQUA + "Builder Wand");
				bw.setItemMeta(meta);
				player.getInventory().addItem(bw);
				player.sendMessage(ChatColor.AQUA + "Builder Wand" + ChatColor.GREEN + " reçu");
			}
		}
		
		return true;
	}
}
