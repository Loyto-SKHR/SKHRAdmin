package fr.skhr.loyto.skhrAdmin.warp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.warp.del")) {
				if(args.length == 1) {
					if(!Warp.verifWarp(args[0])) {
						player.sendMessage(ChatColor.RED + "Le warp " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " n'existe pas");
						return true;
					}
					Warp.delWarp(args[0]);
					player.sendMessage(ChatColor.GREEN + "Le warp " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " à été suprimé");
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /delwarp <nom>");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}

}
