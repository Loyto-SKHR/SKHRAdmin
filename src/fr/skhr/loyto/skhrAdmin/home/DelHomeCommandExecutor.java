package fr.skhr.loyto.skhrAdmin.home;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.home")) {
				if(args.length == 1) {
					if(!Home.verifHome(player.getName(), args[0])) {
						player.sendMessage(ChatColor.RED + "Le home " + ChatColor.GOLD + args[0] + ChatColor.RED + " n'existe pas");
						return true;
					}
					Home.delHome(player.getName(), args[0]);
					player.sendMessage(ChatColor.GREEN + "Le home " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " à été suprimé");
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /delhome <nom>");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}

}
