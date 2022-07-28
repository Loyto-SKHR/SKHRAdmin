package fr.skhr.loyto.skhrAdmin.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommandExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			Player po = null;
			boolean walk = true;
			if(player.hasPermission("skhr.speed")) {
				if(args.length == 1) {
					if(player.isFlying()) {
						walk = false;
					}
				}
				else if(args.length == 2) {
					if(args[1].equals("fly")) {
						walk = false;
					}
					else if(!args[1].equals("walk")) {
						player.sendMessage(ChatColor.RED + "Utilisation: /speed <vitesse> [type] [joueur]");
						player.sendMessage(ChatColor.RED + "Type: fly ou walk");
						player.sendMessage(ChatColor.RED + "Vitesse: 0 à 10");
						return true;
					}
				}
				else if(args.length == 3) {
					if(player.hasPermission("skhr.speed.other")) {
						if(Bukkit.getPlayer(args[2]) != null)
						{
							po = player;
							player = Bukkit.getPlayer(args[2]);
						}
						else {
							player.sendMessage(ChatColor.RED + "Le joueur n'existe pas ou est déconnecté!");
							return true;
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande sur les autres joueurs!");
						return true;
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /speed <vitesse> [type] [joueur]");
					player.sendMessage(ChatColor.RED + "Type: fly ou walk");
					player.sendMessage(ChatColor.RED + "Vitesse: 0 à 10");
					return true;
				}

				float speed = Float.parseFloat(args[0]);
				if(speed > 10) {
					player.sendMessage(ChatColor.RED + "Valeur maximum 10!");
					return true;
				}
				else if(speed < 0) {
					player.sendMessage(ChatColor.RED + "Valeur minimum 0!");
					return true;
				}

				if(walk) {
					player.sendMessage(ChatColor.GREEN + "Speed apliqué en marche à la valeur " + ChatColor.GOLD + speed);
					player.setWalkSpeed((speed)/10);
					
					if(po != null) {
						po.sendMessage(ChatColor.GREEN + "Speed apliqué en marche à la valeur " + ChatColor.GOLD + speed + ChatColor.GREEN + " pour " + ChatColor.GOLD + args[2]);
					}
				}
				else {
					player.sendMessage(ChatColor.GREEN + "Speed apliqué en vole à la valeur " + ChatColor.GOLD + speed);
					player.setFlySpeed((speed)/10);
					
					if(po != null) {
						po.sendMessage(ChatColor.GREEN + "Speed apliqué en vole à la valeur " + ChatColor.GOLD + speed + ChatColor.GREEN + " pour " + ChatColor.GOLD + args[2]);
					}
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Tu n'as pas la permission d'utiliser cette commande!");
			}
		}
		
		return true;
	}
}
