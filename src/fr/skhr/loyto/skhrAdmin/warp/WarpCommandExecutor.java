package fr.skhr.loyto.skhrAdmin.warp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.warp")) {
				if(args.length == 1) {
					if(player.hasPermission("skhr.warps." + args[0]) | player.isOp()) {
						if(!Warp.verifWarp(args[0])) {
							player.sendMessage(ChatColor.GREEN + "Le warp " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " n'existe pas");
							return true;
						}
						
						Warp.tpWarp(args[0], player);
						player.sendMessage(ChatColor.GREEN + "Téléportation au warp " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " éffectuée");
					}
					else
					{
						player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser le warp " + ChatColor.GOLD + args[0]);
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /warp <nom>");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}

}
