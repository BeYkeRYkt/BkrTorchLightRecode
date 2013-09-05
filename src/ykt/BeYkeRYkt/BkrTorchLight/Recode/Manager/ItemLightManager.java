package ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import ykt.BeYkeRYkt.BkrTorchLight.Recode.BTL;

public class ItemLightManager{
	
	private ArrayList<ItemType> itemList = new ArrayList<ItemType>();
	private BTL plugin;
	
	public void createLight(ItemType type, Player player, int level){
		type.createLightSource(player, level);	
	}
	
	public void createLight(Location loc, ItemType type, Player player, int level){
		type.createLightSource(loc, player, level);	
	}
	
	public void removeLight(Location loc, ItemType type, Player player){
		type.deleteLightSource(loc, player);	
	}
	
	public void removeLight(ItemType type, Player player){
		type.deleteLightSource(player);	
	}
	
	public void addLightSource(ItemType type){
		itemList.add(type);
	}
	
	public void removeLightSource(ItemType type){
		itemList.remove(type);
	}
	
	public void setEnabled(ItemType type, boolean enable){
		type.setEnabled(enable);
	}
	
	public boolean isEnabled(ItemType type){
		return type.isEnabled();
	}
	
	public void registerListener(Listener listener){
		Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
	}
	
	
}