package fr.skhr.loyto.skhrAdmin.home;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.home")) {
				if(args.length == 1) {
					Home.addHome(player.getName() ,args[0], player);
					player.sendMessage(ChatColor.GREEN + "Le home " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " à été placé");
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /sethome <nom>");
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}
}
