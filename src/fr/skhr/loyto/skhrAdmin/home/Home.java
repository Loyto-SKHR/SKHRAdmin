package fr.skhr.loyto.skhrAdmin.home;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Home {
	static File homeList = new File("plugins/SKHRAdmin/home.yml");
	public static FileConfiguration fileHome = YamlConfiguration.loadConfiguration(homeList);
	
	public static void saveFile() {
		fileHome.options().copyDefaults(true);
		try {
			fileHome.save(homeList);
		} catch(IOException e) {
			e.printStackTrace();
		}
		fileHome = YamlConfiguration.loadConfiguration(homeList);
	}
	
	public static boolean verifHome(String pseudo, String name) {
		pseudo = pseudo.toLowerCase();
		if(fileHome.get(pseudo + "." + name) == null) {
			return false;
		}
		
		return true;
	}
	
	public static void addHome(String pseudo, String name, Player player) {
		pseudo = pseudo.toLowerCase();
		fileHome.set(pseudo + "." + name, "");
		fileHome.set(pseudo + "." + name + ".dim", player.getWorld().getName());
		fileHome.set(pseudo + "." + name + ".x", player.getLocation().getX());
		fileHome.set(pseudo + "." + name + ".y", player.getLocation().getY());
		fileHome.set(pseudo + "." + name + ".z", player.getLocation().getZ());
		fileHome.set(pseudo + "." + name + ".yaw", player.getLocation().getYaw());
		fileHome.set(pseudo + "." + name + ".pitch", player.getLocation().getPitch());
		saveFile();
	}
	
	public static void delHome(String pseudo, String name) {
		pseudo = pseudo.toLowerCase();
		fileHome.set(pseudo + "." + name, null);
		saveFile();
	}
	
	public static void listHome(String pseudo, Player player) {
		pseudo = pseudo.toLowerCase();
		player.sendMessage(ChatColor.GOLD + "=============== " + ChatColor.WHITE + "homes" + ChatColor.GOLD + " ===============");
		Object[] homes = fileHome.getConfigurationSection(pseudo).getKeys(false).toArray();
		
		String msg = " ";
		for(int i=0; i < homes.length; i++) {
			msg += homes[i];
			
			if(homes.length-1 != i) {
				msg += ", ";
			}
			if(i%10 == 6) {
				msg += "\n";
			}
		}
		player.sendMessage(msg);
	}
	
	public static void tpHome(String pseudo, String name, Player player) {
		pseudo = pseudo.toLowerCase();
		player.teleport( new Location(				
				Bukkit.getWorld(fileHome.getString(pseudo + "." + name + ".dim")),
				fileHome.getDouble(pseudo + "." + name + ".x"),
				fileHome.getDouble(pseudo + "." + name + ".y"),
				fileHome.getDouble(pseudo + "." + name + ".z"),
				(float)fileHome.getDouble(pseudo + "." + name + ".yaw"),
				(float)fileHome.getDouble(pseudo + "." + name + ".pitch")
			)
		);
	}

	public static int nbHome(String pseudo) {
		pseudo = pseudo.toLowerCase();
		
		if(!verifPlayer(pseudo)) {
			return 0;
		}
		
		return fileHome.getConfigurationSection(pseudo).getKeys(false).toArray().length;
	}
	
	public static boolean verifPlayer(String pseudo) {
		pseudo = pseudo.toLowerCase();
		if(fileHome.get(pseudo) == null) {
			return false;
		}
		
		return true;
	}
	
}
