package fr.skhr.loyto.skhrAdmin.build;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class Build {
	
	@SuppressWarnings("deprecation")
	public static void builderWand(Player player, World world, int x, int y, int z, Block block, BlockFace blockFace) {
		List<int[]> listPos = listPosition(block, blockFace, world, x, y, z);
		
		int bp = 0;
		
		int id = block.getTypeId();
		byte data = block.getData();
		Block b;
		for(int[] pos : listPos) {
			b = world.getBlockAt(pos[0], pos[1], pos[2]);
			if(b.getTypeId() == 0) {
				b.setTypeId(id);
				b.setData(data);
				bp++;
			}
		}
		
		player.sendMessage(ChatColor.GOLD + String.valueOf(bp) + ChatColor.GREEN + " blocks pos�s");
	}
	
	@SuppressWarnings("deprecation")
	private static List<int[]> listPosition(Block block, BlockFace blockFace, World world, int x, int y, int z) {
		List<int[]> listPos = new ArrayList<int[]>();
		
		if(blockFace == BlockFace.UP) {
			listPos.add(new int[]{x, y + 1, z});
		} else if(blockFace == BlockFace.DOWN) {
			listPos.add(new int[]{x, y - 1, z});
		} else if(blockFace == BlockFace.NORTH) {
			listPos.add(new int[]{x, y, z - 1});
		} else if(blockFace == BlockFace.SOUTH) {
			listPos.add(new int[]{x, y, z + 1});
		} else if(blockFace == BlockFace.WEST) {
			listPos.add(new int[]{x - 1, y, z});
		} else if(blockFace == BlockFace.EAST) {
			listPos.add(new int[]{x + 1, y, z});
		}
		
		boolean continuer;
		boolean contient;
		
		int id = block.getTypeId();
		byte data = block.getData();
		
		List<Block> b = null;
		int[] p = null;
		
		do {
			continuer = false;
			b = new ArrayList<Block>();
			for(int[] pos : listPos) {
				
				
				if(blockFace == BlockFace.UP) {
					b.add(world.getBlockAt(pos[0] + 1, pos[1] - 1, pos[2]));
					b.add(world.getBlockAt(pos[0] - 1, pos[1] - 1, pos[2]));
					b.add(world.getBlockAt(pos[0], pos[1] - 1, pos[2] + 1));
					b.add(world.getBlockAt(pos[0], pos[1] - 1, pos[2] - 1));
				} else if(blockFace == BlockFace.DOWN) {
					b.add(world.getBlockAt(pos[0] + 1, pos[1] + 1, pos[2]));
					b.add(world.getBlockAt(pos[0] - 1, pos[1] + 1, pos[2]));
					b.add(world.getBlockAt(pos[0], pos[1] + 1, pos[2] + 1));
					b.add(world.getBlockAt(pos[0], pos[1] + 1, pos[2] - 1));
				} else if(blockFace == BlockFace.NORTH) {
					b.add(world.getBlockAt(pos[0], pos[1] + 1, pos[2] + 1));
					b.add(world.getBlockAt(pos[0], pos[1] - 1, pos[2] + 1));
					b.add(world.getBlockAt(pos[0] + 1, pos[1], pos[2] + 1));
					b.add(world.getBlockAt(pos[0] - 1, pos[1], pos[2] + 1));
				} else if(blockFace == BlockFace.SOUTH) {
					b.add(world.getBlockAt(pos[0], pos[1] + 1, pos[2] - 1));
					b.add(world.getBlockAt(pos[0], pos[1] - 1, pos[2] - 1));
					b.add(world.getBlockAt(pos[0] + 1, pos[1], pos[2] - 1));
					b.add(world.getBlockAt(pos[0] - 1, pos[1], pos[2] - 1));
				} else if(blockFace == BlockFace.WEST) {
					b.add(world.getBlockAt(pos[0] + 1, pos[1] + 1, pos[2]));
					b.add(world.getBlockAt(pos[0] + 1, pos[1] - 1, pos[2]));
					b.add(world.getBlockAt(pos[0] + 1, pos[1], pos[2] + 1));
					b.add(world.getBlockAt(pos[0] + 1, pos[1], pos[2] - 1));
				} else if(blockFace == BlockFace.EAST) {
					b.add(world.getBlockAt(pos[0] - 1, pos[1] + 1, pos[2]));
					b.add(world.getBlockAt(pos[0] - 1, pos[1] - 1, pos[2]));
					b.add(world.getBlockAt(pos[0] - 1, pos[1], pos[2] + 1));
					b.add(world.getBlockAt(pos[0] - 1, pos[1], pos[2] - 1));
				}
			}
			
			for(Block bv : b) {
				if(bv.getTypeId() == id & bv.getData() == data) {
					if(blockFace == BlockFace.UP) {
						p = new int[]{bv.getX(), bv.getY() + 1, bv.getZ()};
					} else if(blockFace == BlockFace.DOWN) {
						p = new int[]{bv.getX(), bv.getY() - 1, bv.getZ()};
					} else if(blockFace == BlockFace.NORTH) {
						p = new int[]{bv.getX(), bv.getY(), bv.getZ() - 1};
					} else if(blockFace == BlockFace.SOUTH) {
						p = new int[]{bv.getX(), bv.getY(), bv.getZ() + 1};
					} else if(blockFace == BlockFace.WEST) {
						p = new int[]{bv.getX() - 1, bv.getY(), bv.getZ()};
					} else if(blockFace == BlockFace.EAST) {
						p = new int[]{bv.getX() + 1, bv.getY(), bv.getZ()};
					}
					
					contient = false;
					for(int[] pos : listPos) {
						if(pos[0] == p[0] & pos[1] == p[1] & pos[2] == p[2]) {
							contient = true;
						}
					}
					
					if(!contient) {
						listPos.add(p);
						continuer = true;
						
					}
				}
			}
			if(listPos.size() > 200) {
				continuer = false;
			}
		} while(continuer);
		
		return listPos;
	}
}