package fr.skhr.loyto.skhrAdmin.warp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Warp {
	static File warpList = new File("plugins/SKHRAdmin/warp.yml");
	public static FileConfiguration fileWarp = YamlConfiguration.loadConfiguration(warpList);
	
	public static void saveFile() {
		fileWarp.options().copyDefaults(true);
		try {
			fileWarp.save(warpList);
		} catch(IOException e) {
			e.printStackTrace();
		}
		fileWarp = YamlConfiguration.loadConfiguration(warpList);
	}
	
	public static boolean verifWarp(String name) {
		if(fileWarp.get("warp." + name) == null) {
			return false;
		}
		
		return true;
	}
	
	public static void addWarp(String name, Player player) {
		fileWarp.set("warp." + name, "");
		fileWarp.set("warp." + name + ".dim", player.getWorld().getName());
		fileWarp.set("warp." + name + ".x", player.getLocation().getX());
		fileWarp.set("warp." + name + ".y", player.getLocation().getY());
		fileWarp.set("warp." + name + ".z", player.getLocation().getZ());
		fileWarp.set("warp." + name + ".yaw", player.getLocation().getYaw());
		fileWarp.set("warp." + name + ".pitch", player.getLocation().getPitch());
		saveFile();
	}
	
	public static void tpWarp(String name, Player player) {
		player.teleport( new Location(				
				Bukkit.getWorld(fileWarp.getString("warp." + name + ".dim")),
				fileWarp.getDouble("warp." + name + ".x"),
				fileWarp.getDouble("warp." + name + ".y"),
				fileWarp.getDouble("warp." + name + ".z"),
				(float)fileWarp.getDouble("warp." + name + ".yaw"),
				(float)fileWarp.getDouble("warp." + name + ".pitch")
			)
		);
	}
	
	public static void delWarp(String name) {
		fileWarp.set("warp." + name, null);
		saveFile();
	}
	
	public static void listWarp(int page, Player player) {
		Object[] warps = fileWarp.getConfigurationSection("warp").getKeys(false).toArray();
		int nbPages = (int)(warps.length/10);
		if(warps.length%10 > 0) {
			nbPages++;
		}
		
		if(page < 1) {
			page = 1;
		}
		else if(page > nbPages) {
			page = nbPages;
		}
		
		int max = (10+(10*(page-1)));
		
		if(warps.length < 10) {
			max = warps.length;
		}
		else if(page == nbPages & warps.length%10 < 10) {
			max = ((warps.length%10)+(10*(page-1)));
		}
		
		String msg = " ";
		player.sendMessage(ChatColor.GOLD + "=============== " + ChatColor.WHITE + page + "/" + nbPages + ChatColor.GOLD + " ===============");
		for(int i = (0+(10*(page-1))); i < max; i++) {
			msg += (String) warps[i];
			if(max-1 != i) {
				msg += ", ";
			}
			if(i%10 == 4) {
				msg += "\n";
			}
		}
		player.sendMessage(msg);
	}
	
	public static int nbWarp() {
		try {
			return fileWarp.getConfigurationSection("warp").getKeys(false).toArray().length;
		}
		catch(Exception e) {
			return 0;
		}
	}
}
