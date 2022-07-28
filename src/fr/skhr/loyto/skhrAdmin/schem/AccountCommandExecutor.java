package fr.skhr.loyto.skhrAdmin.schem;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class AccountCommandExecutor implements CommandExecutor {
	static File configFile = new File("plugins/SKHRAdmin/config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

	String host = config.getString("sql.host");
	String port = config.getString("sql.port");
	String user = config.getString("sql.user");
	String pass = config.getString("sql.password");
	String bdd = config.getString("sql.bdd");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player)) {
			Player player = (Player)sender;
			if(player.hasPermission("skhr.schem")) {
				String schemDir = "plugins/WorldEdit/schematics/" + player.getName() + "/";
				if(!new File(schemDir).exists()) {
					new File(schemDir).mkdir();
				}
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + bdd, user, pass);
					Statement stmt = conn.createStatement();
					ResultSet res = stmt.executeQuery("SELECT * FROM schem WHERE pseudo='" + player.getName() + "' LIMIT 1");
					while(res.next()) {
						player.sendMessage(ChatColor.GREEN + "Ton compte:");
						player.sendMessage(ChatColor.GRAY + "Pseudo: " + ChatColor.WHITE + res.getString(2));
						player.sendMessage(ChatColor.GRAY + "Mot de passe: " + ChatColor.WHITE + res.getString(3));
						player.sendMessage(ChatColor.GRAY + "Site: " + ChatColor.GOLD + "https://skhr.ddns.net/deltabuild/");
						return true;
					}
					
					String password = getRandomStr();
					
					player.sendMessage(ChatColor.GREEN + "Ton compte:");
					player.sendMessage(ChatColor.GRAY + "Pseudo: " + ChatColor.WHITE + player.getName());
					player.sendMessage(ChatColor.GRAY + "Mot de passe: " + ChatColor.WHITE + password);
					player.sendMessage(ChatColor.GRAY + "Site: " + ChatColor.GOLD + "https://skhr.ddns.net/deltabuild/");
					
					stmt.executeUpdate("INSERT INTO schem (id, pseudo, password) Values (NULL, '" + player.getName() + "', '" + password + "')");
					conn.commit();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande");
			}
		}
		
		return true;
	}
	
	public static String getRandomStr() {
		int n = 8;
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "abcdefghijklmnopqrstuvxyz"
                    + "0123456789"; 
  
        StringBuilder s = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
            int index = (int)(str.length() * Math.random()); 
            s.append(str.charAt(index)); 
        } 
        return s.toString(); 
    } 

}
