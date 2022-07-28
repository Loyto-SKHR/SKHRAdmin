package fr.skhr.loyto.skhrAdmin.tpa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpdenyCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.tpa")) {
				if(Tpa.verifRequete(player.getName())) {
					player.sendMessage(ChatColor.GREEN + "T�l�portation annul�e");
					Bukkit.getPlayer(Tpa.getPlayer(player.getName())).sendMessage(ChatColor.RED + "T�l�portation annul�e");
					Tpa.remRequete(player.getName());
				}
				else {
					player.sendMessage(ChatColor.RED + "Aucune resu�te de t�l�portation en attente");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Vous n'avez pas l'autorisation d'�xecuter cette commande");
			}
		}

		
		return false;
	}

}
