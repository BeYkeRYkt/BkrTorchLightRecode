package ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items;

import net.minecraft.server.v1_5_R3.EnumSkyBlock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.BkrTorchLight.Recode.BTL;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.Chunks;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.ItemType;

public class HeadLamp implements ItemType{
	private BTL plugin;
	public boolean enable;
	public String name = "HeadLamp";
	
	public HeadLamp(){
	}
	
	  //Light
	  public void createLightSource(Location toPlayerLocation, Player player, int level)
	  {
	    CraftWorld cWorld = (CraftWorld)toPlayerLocation.getWorld();    
	    int xNew = toPlayerLocation.getBlockX();
	    int yNew = toPlayerLocation.getBlockY() + 2;
	    int zNew = toPlayerLocation.getBlockZ();

	    
	    int lightLevel = level;
	    
		cWorld.getHandle().b(EnumSkyBlock.BLOCK, xNew, yNew, zNew, lightLevel);
		
	    Location newSource = new Location(cWorld, xNew, yNew-1, zNew);
	    Material blockMaterial = newSource.getBlock().getType();
	    byte blockData = newSource.getBlock().getData();
	    newSource.getBlock().setType(blockMaterial);
	    newSource.getBlock().setData(blockData);
	    Chunks.sendClientChanges();
	}

	  public void createLightSource(Player player, int level)
	  {
	    CraftWorld cWorld = (CraftWorld)player.getWorld();
	    Location playerLocation = player.getLocation().getBlock().getLocation();

	    int xNew = playerLocation.getBlockX();
	    int yNew = playerLocation.getBlockY() + 2;
	    int zNew = playerLocation.getBlockZ();

	    int lightLevel = level;

	    cWorld.getHandle().b(EnumSkyBlock.BLOCK, xNew , yNew, zNew, lightLevel);

	    Location newSource = new Location(cWorld, xNew, yNew+1, zNew);
	    Material blockMaterial = newSource.getBlock().getType();
	    byte blockData = newSource.getBlock().getData();
	    newSource.getBlock().setType(blockMaterial);
	    newSource.getBlock().setData(blockData);
	    Chunks.sendClientChanges();
	  }
	  
	  
	  public void deleteLightSource(Location fromPlayerLocation, Player player)
	  {
	    CraftWorld cWorld = (CraftWorld)fromPlayerLocation.getWorld();

	    int xPrevious = fromPlayerLocation.getBlockX();
	    int yPrevious = fromPlayerLocation.getBlockY() + 2;
	    int zPrevious = fromPlayerLocation.getBlockZ();
	    
	    Location previousSource = new Location(cWorld, xPrevious, yPrevious, zPrevious);
	    Material blockMaterial = previousSource.getBlock().getType();
	    byte blockData = previousSource.getBlock().getData();
	    previousSource.getBlock().setType(blockMaterial);
	    previousSource.getBlock().setData(blockData); 
	    Chunks.sendClientChanges();
	  }
	  
	  public void deleteLightSource(Player player)
	  {
	    CraftWorld cWorld = (CraftWorld)player.getWorld();
	    Location playerLocation = player.getLocation().getBlock().getLocation();

	      int xPrevious = playerLocation.getBlockX();
	      int yPrevious = playerLocation.getBlockY() + 2;
	      int zPrevious = playerLocation.getBlockZ();
	      
	      Location previousSource = new Location(cWorld, xPrevious, yPrevious, zPrevious);
	      Material blockMaterial = previousSource.getBlock().getType();
	      byte blockData = previousSource.getBlock().getData();
	      previousSource.getBlock().setType(blockMaterial);
	      previousSource.getBlock().setData(blockData);
	      Chunks.sendClientChanges();
	    }

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enable;
	}

	@Override
	public void setEnabled(boolean enable) {
this.enable = enable;
		
	}
}