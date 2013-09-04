package ykt.BeYkeRYkt.BkrTorchLight.Recode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minecraft.server.v1_5_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.IWorldAccess;
import net.minecraft.server.v1_5_R3.Packet56MapChunkBulk;
import net.minecraft.server.v1_5_R3.PlayerChunkMap;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_5_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class Chunks {
	  public static int f;
	
		private static Method cachedPlayerChunk;		
	private static Field cachedDirtyField;
	
	private static BlockFace[] SIDES = {

		BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH,

		BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
		
	 

	private static Block getAdjacentAirBlock(Block block) {

	// Find the first adjacent air block

	for (BlockFace face : SIDES) {

	// Don't use these sides

	if (block.getY() == 0x0 && face == BlockFace.DOWN)

	continue;

	if (block.getY() == 0xFF && face == BlockFace.UP)
		continue;
		if (face == BlockFace.EAST)
			continue;
			if (face == BlockFace.NORTH)
				continue;
				if (face == BlockFace.SOUTH)
					continue;
					if (face == BlockFace.WEST)

	continue;

	 

	Block candidate = block.getRelative(face);

	 

	if (candidate.getType().isTransparent()) {

	return candidate;

	}

	}

	return block;

	}


	  private static IWorldAccess countLightUpdates(final org.bukkit.World world, final PlayerChunkMap map) {

	  return new IWorldAccess() {
		  @Override

		//markBlockForUpdate

		public void a(int x, int y, int z) {

		map.flagDirty(x, y, z);

		}

		 

		@Override

		//markBlockForRenderUpdate

		public void b(int x, int y, int z) {

		map.flagDirty(x, y, z);

		}

	   

	  @Override

	  //destroyBlockPartially

	  public void b(int arg0, int arg1, int arg2, int arg3, int arg4) { }

	   

	  @Override

	  //playAuxSFX

	  public void a(EntityHuman arg0, int arg1, int arg2, int arg3, int arg4, int arg5) { }

	   

	  @Override

	  //markBlockRangeForRenderUpdate

	  public void a(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {

	  // Ignore

	  }

	   

	  @Override

	  //broadcastSound

	  public void a(int arg0, int arg1, int arg2, int arg3, int arg4) { }

	   

	  @Override

	  //playSound

	  public void a(String arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {

	  }

	   

	  @Override

	  //playSoundToNearExcept

	  public void a(EntityHuman arg0, String arg1, double arg2, double arg3, double arg4, float arg5,float arg6) {

	  }

	   

	  @Override

	  //spawnParticle

	  public void a(String arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6) { }

	   

	  @Override

	  //playRecord

	  public void a(String arg0, int arg1, int arg2, int arg3) { }

	   

	  @Override

	  //onEntityCreate

	  public void a(Entity arg0) { }

	   

	  @Override

	  //onEntityDestroy (probably)

	  public void b(Entity arg0) { }


	  };

	  }

	   

	  private static Method getPlayerCountMethod() throws NoSuchMethodException, SecurityException {

	  if (cachedPlayerChunk == null) {

	  cachedPlayerChunk = PlayerChunkMap.class.getDeclaredMethod("a", int.class, int.class, boolean.class);

	  cachedPlayerChunk.setAccessible(true);

	  }

	  return cachedPlayerChunk;

	  }

	   

	  private static Field getDirtyField(Object playerChunk) throws NoSuchFieldException, SecurityException {

	  if (cachedDirtyField == null) {

	  cachedDirtyField = playerChunk.getClass().getDeclaredField("dirtyCount");

	  cachedDirtyField.setAccessible(true);

	  }

	  return cachedDirtyField;

	  }
	
	//Default
	public static List<Chunk> getChunks(Player p) {

		List<Chunk> res = new ArrayList<Chunk>();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		Location loc = new Location(p.getWorld(), x, y, z);
		Location posx = new Location(p.getWorld(), x + 16, y, z);
		Location negx = new Location(p.getWorld(), x - 16, y, z);
		Location posz = new Location(p.getWorld(), x, y, z + 16);
		Location negz = new Location(p.getWorld(), x, y, z - 16);
		Location diag1 = new Location(p.getWorld(), x + 16, y, z + 16);
		Location diag2 = new Location(p.getWorld(), x - 16, y, z + 16);
		Location diag3 = new Location(p.getWorld(), x + 16, y, z - 16);
		Location diag4 = new Location(p.getWorld(), x - 16, y, z - 16);
		res.add((Chunk) loc.getChunk());
		res.add((Chunk) posx.getChunk());
		res.add((Chunk) negx.getChunk());
		res.add((Chunk) posz.getChunk());
		res.add((Chunk) negz.getChunk());
		res.add((Chunk) diag1.getChunk());
		res.add((Chunk) diag2.getChunk());
		res.add((Chunk) diag3.getChunk());
		res.add((Chunk) diag4.getChunk());
		return res;
	}
	
	public static void updateChunk(WorldServer nmsWorld, Player player, String string) {
		 for (Player p : Bukkit.getOnlinePlayers()){
			 Location loc = player.getLocation();
				for (Chunk c : getChunks(p)) {
					try {

						PlayerChunkMap map = nmsWorld.getPlayerChunkMap();

						IWorldAccess access = countLightUpdates(loc.getWorld(), map);

						nmsWorld.addIWorldAccess(access);

						// Update the light itself

						Block adjacent = getAdjacentAirBlock(loc.getBlock());

						nmsWorld.A(adjacent.getX(), adjacent.getY(), adjacent.getZ());
						
						Field field = World.class.getDeclaredField("u");

						field.setAccessible(true);

						((List) field.get(nmsWorld)).remove(access);

						} catch (SecurityException e) {

						throw new RuntimeException("Access denied", e);

						} catch (ReflectiveOperationException e) {

						throw new RuntimeException("Reflection problem.", e);

						}
				}
		 }
				}
		 
}