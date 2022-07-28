package fr.skhr.loyto.skhrAdmin.schem;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SchemCommandExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.schem")) {
				if(args.length > 0) {
					String schemDir = "plugins/WorldEdit/schematics/" + player.getName() + "/";
					if(!new File(schemDir).exists()) {
						new File(schemDir).mkdir();
					}
					if(args[0].equals("list")) {
						File dir = new File(schemDir);
						if(dir.exists() & dir.listFiles().length > 0) {
							File[] schems = dir.listFiles();
							player.sendMessage(ChatColor.GREEN + "Liste des schematics:");
							for(File schem : schems) {
								player.sendMessage(ChatColor.GRAY + schem.getName());
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "Aucun schematic");
						}
					}
					else if(args[0].equals("load")) {
						if(args.length == 2) {
							Schem.load(player, args[1], schemDir);
							player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " chargé");
						}
						else {
							player.sendMessage(ChatColor.RED + "Utilisation: /pschem load <schematic>");
						}
					}
					else if(args[0].equals("save")) {
						if(args.length == 2 ) {
							Schem.save(player, args[1], schemDir);
							player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " sauvegardé");
						}
						else {
							player.sendMessage(ChatColor.RED + "Utilisation: /pschem save <schematic>");
						}
					}
					else if(args[0].equals("remove")) {
						if(args.length == 2) {
							File schem = new File(schemDir + args[1] + ".schematic");
							if(schem.delete()) {
								player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " supprimé");
							}
							else {
								player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.RED + " n'existe pas");
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "Utilisation: /pschem remove <schematic>");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Utilisation: /pschem <list|load|save|remove>");
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "Utilisation: /pschem <list|load|save|remove>");
				}
				return true;
			}
			else {
				player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande");
			}
		}
		
		return true;
	}
}
