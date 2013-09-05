package ykt.BeYkeRYkt.BkrTorchLight.Recode.Listeners.Default;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import ykt.BeYkeRYkt.BkrTorchLight.Recode.BTL;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.ItemLightManager;

public class HeadLamp implements Listener {

	private BTL plugin;
	public Location toPlayerLocation;
	public Location fromPlayerLocation;
	public ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.ItemLightManager lamp = new ItemLightManager();
	
public ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.HeadLamp hl = new ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.HeadLamp();

    public HeadLamp(BTL instance)
    {
      this.plugin = instance;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    Location l = player.getLocation();
    			lamp.removeLight(l, hl, player);
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
    Player player = event.getPlayer();
    Location l = player.getLocation();
    			lamp.removeLight(l, hl, player);
        }

    @EventHandler
    public void onPlayerChangeWorlds(PlayerChangedWorldEvent event) {
    	Player player = event.getPlayer();
        Location l = player.getLocation();
    	lamp.removeLight(l, hl, player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Location l = player.getLocation();
        lamp.removeLight(l, hl, player);
    	plugin.isHelmetUse.remove(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event){
    	Player player = event.getPlayer();
    	Location l = player.getLocation();
        if(player.hasPermission("BTL.light")){
        if ((event.getAction() == (Action.RIGHT_CLICK_AIR))) {
        	if(player.isSneaking()){
    		if (plugin.isHelmet(event.getPlayer().getInventory().getArmorContents()[3].getTypeId())) {
        	if (plugin.getConfig().getBoolean("Worlds." + ((Player)player).getWorld().getName()) || ((Player)player).getPlayer().isOp()) {
    			if (!plugin.isHelmetUse.contains(player)) {
    				plugin.isHelmetUse.add((Player)player);
    				lamp.createLight(hl, player, plugin.isHelmetLight(event.getPlayer().getInventory().getArmorContents()[3].getTypeId()));
    				if(plugin.getConfig().getBoolean("message-headlamp-enable", true)) {
    				player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("HeadLamp.activate"));
    				}
    			} else {
    				if (plugin.isHelmet(event.getPlayer().getInventory().getArmorContents()[3].getTypeId())){
    					lamp.removeLight(hl, player);
    				}
    				plugin.isHelmetUse.remove(player);
    				if(plugin.getConfig().getBoolean("message-torch-enable", true)) {
    				player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("HeadLamp.deactivate"));
    				}
    			}
        	} else if (!plugin.getConfig().getBoolean("Worlds." + ((Player)player).getWorld().getName())) {
        		if(plugin.getConfig().getBoolean("message-headlamp-enable", true)) {
        		player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("HeadLamp.disabled"));
        		}
    		}
        }
        }
        }
        }
    }
    
    
    @EventHandler
    public void onPlayerMoveHeadLamp(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("Worlds." + event.getPlayer().getWorld().getName()) || event.getPlayer().isOp()) {
    			  {
    				    this.fromPlayerLocation = event.getFrom();
    				    this.toPlayerLocation = event.getTo();
    				    if (plugin.isHelmetUse.contains(event.getPlayer())) {
    				    
				if (plugin.isHelmet(event.getPlayer().getInventory().getArmorContents()[3].getTypeId())) {
                lamp.removeLight(this.fromPlayerLocation, hl, player);
                lamp.createLight(this.toPlayerLocation, hl,player, plugin.isHelmetLight(event.getPlayer().getInventory().getArmorContents()[3].getTypeId()));
		
            }else{
            lamp.removeLight(this.toPlayerLocation, hl, player);
            }
    		}
        }
    	}
    }
}