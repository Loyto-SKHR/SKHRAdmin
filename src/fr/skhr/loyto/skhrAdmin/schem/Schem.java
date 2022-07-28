package fr.skhr.loyto.skhrAdmin.schem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.common.io.Closer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;

public class Schem {
	public static boolean save(Player p, String schematicName, String dir) {
		WorldEditPlugin wep = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
        LocalSession session = wep.getSession(p);
        com.sk89q.worldedit.entity.Player player = wep.wrapPlayer(p);
        EditSession editSession = session.createEditSession(player);
        Closer closer = Closer.create();
        try {
            Region region = session.getSelection(player.getWorld());
            Clipboard cb = new BlockArrayClipboard(region);
            ForwardExtentCopy copy = new ForwardExtentCopy(editSession, region, cb, region.getMinimumPoint());
            copy.setCopyingEntities(true);
            Operations.completeLegacy(copy);

            File schematicFile = new File(dir + schematicName + ".schematic");
            schematicFile.createNewFile();

            FileOutputStream fos = closer.register(new FileOutputStream(schematicFile));
            BufferedOutputStream bos = closer.register(new BufferedOutputStream(fos));
            
            WorldData worldData = session.getClipboard().getWorldData();
            ClipboardWriter writer = closer.register(ClipboardFormat.SCHEMATIC.getWriter(bos));
            cb.setOrigin(session.getClipboard().getClipboard().getOrigin());
            writer.write(cb, worldData);
            
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (EmptyClipboardException|IncompleteRegionException e) {
            e.printStackTrace();
        } catch (WorldEditException e) {
			e.printStackTrace();
		} finally {
            try {
                closer.close();
            } catch (IOException ignore) {
            }
        }
        
        
        
        return false;
    }
	
	public static boolean load(Player player, String schematic, String dir) {
		String filename = schematic + ".schematic";
		File f = new File(dir + filename);
		
		if (!f.exists()) {
			System.out.println("Schematic " + filename + " does not exist!");
			return false;
		}
		
		ClipboardFormat fileFormat = ClipboardFormat.findByFile(f);
		ClipboardFormat aliasFormat = ClipboardFormat.findByAlias("mcedit");
		ClipboardFormat format = (fileFormat == null) ? aliasFormat : fileFormat;
		if (format == null) {
			player.sendMessage(ChatColor.RED + "Le schematic n'existe pas");
			return false;
		}
		
		Closer closer = Closer.create();
		try {
			String filePath = f.getCanonicalPath();
			String dirPath = new File(dir).getCanonicalPath();
		
			if (!filePath.substring(0, dirPath.length()).equals(dirPath)) {
				player.sendMessage(ChatColor.RED + "Le schematic n'existe pas");
			} else {
				FileInputStream fis = closer.register(new FileInputStream(f));
				BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
				ClipboardReader reader = format.getReader(bis);
				WorldData worldData = new BukkitWorld(player.getWorld()).getWorldData();
				Clipboard clipboard = reader.read(worldData);
				WorldEditPlugin wep = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
		        LocalSession session = wep.getSession(player);
				ClipboardHolder c = new ClipboardHolder(clipboard, worldData);
				session.setClipboard(c);
			}
		} catch (IOException e) {
			System.out.println("Schematic could not be read or it does not exist:");
			e.printStackTrace();
		}
		return true;
	}
}
