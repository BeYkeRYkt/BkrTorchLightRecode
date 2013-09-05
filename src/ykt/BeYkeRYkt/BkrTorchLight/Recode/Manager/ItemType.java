package ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ItemType{
	
	public String getName();
	
	public void createLightSource(Location toPlayerLocation, Player player, int level);
	
	public void createLightSource(Player player, int level);
	
	public void deleteLightSource(Location fromPlayerLocation, Player player);
	
	public void deleteLightSource(Player player);
	
	public boolean isEnabled();
	
	public void setEnabled(boolean enable);
	
}