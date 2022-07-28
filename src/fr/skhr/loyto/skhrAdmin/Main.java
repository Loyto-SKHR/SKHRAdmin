package fr.skhr.loyto.skhrAdmin;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skhr.loyto.skhrAdmin.home.DelHomeCommandExecutor;
import fr.skhr.loyto.skhrAdmin.home.HomeCommandExecutor;
import fr.skhr.loyto.skhrAdmin.home.HomesCommandExecutor;
import fr.skhr.loyto.skhrAdmin.home.SetHomeCommandExecutor;
import fr.skhr.loyto.skhrAdmin.other.SpeedCommandExecutor;
import fr.skhr.loyto.skhrAdmin.schem.AccountCommandExecutor;
import fr.skhr.loyto.skhrAdmin.schem.SchemCommandExecutor;
import fr.skhr.loyto.skhrAdmin.tpa.TpaCommandExecutor;
import fr.skhr.loyto.skhrAdmin.tpa.TpacceptCommandExecutor;
import fr.skhr.loyto.skhrAdmin.tpa.TpahereCommandExecutor;
import fr.skhr.loyto.skhrAdmin.tpa.TpdenyCommandExecutor;
import fr.skhr.loyto.skhrAdmin.warp.DelWarpCommandExecutor;
import fr.skhr.loyto.skhrAdmin.warp.SetWarpCommandExecutor;
import fr.skhr.loyto.skhrAdmin.warp.WarpCommandExecutor;
import fr.skhr.loyto.skhrAdmin.warp.WarpsCommandExecutor;

public class Main extends JavaPlugin {
	public static File configFile;
	public static FileConfiguration config;
	
	@Override
	public void onEnable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SKHR-ADMIN][LOG] Plugin demarre !");
		
		//Fichier config
		saveDefaultConfig();
		configFile = new File("plugins/SKHRAdmin/config.yml");
	    config = YamlConfiguration.loadConfiguration(configFile);
		
	    //Fichier liste des warps
		File warpList = new File("plugins/SKHRAdmin/warp.yml");
		if(!warpList.exists()) {
			try {
				warpList.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Fichier requete tpa
		File tpa = new File("plugins/SKHRAdmin/tpa.yml");
		if(!tpa.exists()) {
			try {
				tpa.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Fichier liste des homes
		File homeList = new File("plugins/SKHRAdmin/home.yml");
		if(!homeList.exists()) {
			try {
				homeList.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Comande speed
		getCommand("speed").setExecutor(new SpeedCommandExecutor());

		//Commande warp
		getCommand("warp").setExecutor(new WarpCommandExecutor());
		getCommand("warps").setExecutor(new WarpsCommandExecutor());
		getCommand("setwarp").setExecutor(new SetWarpCommandExecutor());
		getCommand("delwarp").setExecutor(new DelWarpCommandExecutor());
		
		//Commande home
		getCommand("home").setExecutor(new HomeCommandExecutor());
		getCommand("homes").setExecutor(new HomesCommandExecutor());
		getCommand("sethome").setExecutor(new SetHomeCommandExecutor());
		getCommand("delhome").setExecutor(new DelHomeCommandExecutor());
		
		//Commande tpa
		getCommand("tpa").setExecutor(new TpaCommandExecutor());
		getCommand("tpahere").setExecutor(new TpahereCommandExecutor());
		getCommand("tpaccept").setExecutor(new TpacceptCommandExecutor());
		getCommand("tpdeny").setExecutor(new TpdenyCommandExecutor());
		
		//Commande schem
		getCommand("pschem").setExecutor(new SchemCommandExecutor());
		getCommand("account").setExecutor(new AccountCommandExecutor());
	}
	
	@Override
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SKHR-ADMIN][LOG] Plugin arreter !");
	}
}
