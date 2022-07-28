package fr.skhr.loyto.skhrAdmin.tpa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommandExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			
			if(player.hasPermission("skhr.tpa")) {
				if(args.length == 1) {
					if(Bukkit.getPlayer(args[0]) != null) {
						if(!Tpa.verifRequete(args[0])) {
							Tpa.addRequete(args[0], player.getName(), "tpa");
							player.sendMessage(ChatColor.GREEN + "Requ�te de t�l�portation envoy�e");
							Bukkit.getPlayer(args[0]).sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " demande � ce t�l�porter sur vous");
							Bukkit.getPlayer(args[0]).sendMessage(ChatColor.GREEN + "Accepter: " + ChatColor.GRAY + "/tpaccept" + ChatColor.GOLD + " | " + ChatColor.RED + "Refuser: " + ChatColor.GRAY + "/tpdeny");
						}
						else {
							player.sendMessage(ChatColor.RED + "Le joueur � d�j� une requ�te en attente, veuillez patienter ...");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Le joueur n'est pas connect�");
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /tpa <player>");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Vous ne n'avez pas la permission de faire cette commande.");
			}
		}
		
		return true;
	}

}
