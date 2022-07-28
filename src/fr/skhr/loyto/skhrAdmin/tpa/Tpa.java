package fr.skhr.loyto.skhrAdmin.tpa;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Tpa {
	static File tpaFile = new File("plugins/SKHRAdmin/tpa.yml");
	public static FileConfiguration tpa = YamlConfiguration.loadConfiguration(tpaFile);
	
	public static void saveFile() {
		tpa.options().copyDefaults(true);
		try {
			tpa.save(tpaFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		tpa = YamlConfiguration.loadConfiguration(tpaFile);
	}
	
	public static boolean verifRequete(String pseudo) {
		pseudo = pseudo.toLowerCase();
		if(tpa.get(pseudo) == null) {
			return false;
		}
		
		return true;
	}
	
	public static void addRequete(String pseudo1, String pseudo2, String type) {
		pseudo1 = pseudo1.toLowerCase();
		pseudo2 = pseudo2.toLowerCase();
		type = type.toLowerCase();
		
		tpa.set(pseudo1, "");
		tpa.set(pseudo1 + ".player", pseudo2);
		tpa.set(pseudo1 + ".type", type);
		saveFile();
		tpaThread requete = new tpaThread(pseudo1, pseudo2);
		requete.start();
	}
	
	public static void remRequete(String pseudo) {
		pseudo = pseudo.toLowerCase();
		
		tpa.set(pseudo, null);
		saveFile();
	}
	
	public static void acceptRequete(String pseudo) {
		pseudo = pseudo.toLowerCase();
		
		if(!verifRequete(pseudo)) {
			Bukkit.getPlayer(pseudo).sendMessage(ChatColor.RED + "Aucunne requête de tp en attente");
			return;
		}
		
		Player p1 = Bukkit.getPlayer(tpa.getString(pseudo + ".player"));
		Player p2 = Bukkit.getPlayer(pseudo);
		
		if(tpa.getString(pseudo + ".type").equals("tpa")) {
			p1.teleport(p2.getLocation());
			p1.sendMessage(ChatColor.GREEN + "Vous avez été téléporté sur " + ChatColor.GOLD + p2.getName());
			p2.sendMessage(ChatColor.GOLD + p1.getName() + ChatColor.GREEN + " s'est téléporté sur vous");
		}
		else {
			p2.teleport(p1.getLocation());
			p2.sendMessage(ChatColor.GREEN + "Vous avez été téléporté sur " + ChatColor.GOLD + p1.getName());
			p1.sendMessage(ChatColor.GOLD + p2.getName() + ChatColor.GREEN + " s'est téleporté sur vous");
		}
		
		remRequete(pseudo);
	}
	
	public static String getPlayer(String pseudo) {
		pseudo = pseudo.toLowerCase();
		return tpa.getString(pseudo + ".player");
	}
}

class tpaThread extends Thread {
	String pseudo1 = "";
	String pseudo2 = "";
	
	public tpaThread(String p1, String p2) {
		pseudo1 = p1;
		pseudo2 = p2;
	}
	
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(Tpa.verifRequete(pseudo1)) {
			Tpa.remRequete(pseudo1);
		}
	}
}
