package fr.skhr.loyto.skhrAdmin.warp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpsCommandExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.warp")) {
				if(Warp.nbWarp() == 0) {
					player.sendMessage(ChatColor.RED + "Il y a aucun warp.");
					
					return true;
				}
				
				if(args.length == 1) {
					Warp.listWarp(Integer.parseInt(args[0]), player);
				}
				else {
					Warp.listWarp(1, player);
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}
}
