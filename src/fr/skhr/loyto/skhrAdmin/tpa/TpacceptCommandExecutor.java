package fr.skhr.loyto.skhrAdmin.tpa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			
			if(player.hasPermission("skhr.tpa")) {
				if(Tpa.verifRequete(player.getName())) {
					if(Bukkit.getPlayer(Tpa.getPlayer(player.getName())) == null) {
						player.sendMessage(ChatColor.RED + "Le joueur n'est plus connecté");
						return true;
					}
					Tpa.acceptRequete(player.getName());
				}
				else {
					player.sendMessage(ChatColor.RED + "Aucunne requête de tp en attente");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Vous ne n'avez pas la permission de faire cette commande.");
			}
		}
		
		return true;
	}
}
