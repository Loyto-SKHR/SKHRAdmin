package fr.skhr.loyto.skhrAdmin.home;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.home")) {
				if(args.length == 1) {
					int dp = args[0].indexOf(":");
					if(dp > 0 & player.hasPermission("skhr.home.other")) {
						String pseudo = args[0].substring(0, dp);
						if(!Home.verifPlayer(pseudo)) {
							player.sendMessage(ChatColor.RED + "Le joueur n'existe pas ou n'as pas mis de home");
							return true;
						}
						if(dp == args[0].length()-1) {
							Home.listHome(pseudo, player);
						}
						else {
							String home = args[0].substring(dp+1, args[0].length());
							if(!Home.verifHome(pseudo, home)) {
								player.sendMessage(ChatColor.GREEN + "Le home " + ChatColor.GOLD + home + ChatColor.GREEN + " n'existe pas");
								return true;
							}
							Home.tpHome(pseudo, home, player);
							player.sendMessage(ChatColor.GREEN + "Téléportation au home " + ChatColor.GOLD + home + ChatColor.GREEN + " éffectuée");
						}
						return true;
					}
					
					if(!Home.verifHome(player.getName(), args[0])) {
						player.sendMessage(ChatColor.GREEN + "Le home " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " n'existe pas");
						return true;
					}
					Home.tpHome(player.getName(), args[0], player);
					player.sendMessage(ChatColor.GREEN + "Téléportation au home " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " éffectuée");
				}
				else {
					if(Home.nbHome(player.getName()) == 0) {
						player.sendMessage(ChatColor.RED + "Aucun home définit");
						
						return true;
					}
					Home.listHome(player.getName(), player);
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}

}
