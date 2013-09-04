package ykt.BeYkeRYkt.BkrTorchLight.Recode.Listeners.Default;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import ykt.BeYkeRYkt.BkrTorchLight.Recode.BTL;


public class TorchLight
implements Listener
{
public BTL plugin;

public Location toPlayerLocation;
public Location fromPlayerLocation;
public ykt.BeYkeRYkt.BkrTorchLight.Recode.Items.TorchLight light;

public TorchLight(BTL instance)
{
  this.plugin = instance;
}

@EventHandler
public void onPlayerDeath(PlayerDeathEvent event) {
Player player = event.getEntity();
Location l = player.getLocation();
			light.deleteLightSource(l, player);
    }


@EventHandler
public void onPlayerTeleport(PlayerTeleportEvent event) {
Player player = event.getPlayer();
Location l = player.getLocation();
			light.deleteLightSource(l, player);
    }

@EventHandler
public void onPlayerChangeWorlds(PlayerChangedWorldEvent event) {
	Player player = event.getPlayer();
	Location l = player.getLocation();
	light.deleteLightSource(l, player);
}

@EventHandler
public void onPlayerMove(PlayerMoveEvent event)
{
  Player player = event.getPlayer();
  String playerName = player.getName();
	if (plugin.getConfig().getBoolean("Worlds." + event.getPlayer().getWorld().getName()) || event.getPlayer().isOp()) {
		if (plugin.isUsing.contains(event.getPlayer())) {

	 for (Player p : Bukkit.getOnlinePlayers()){

  {
    this.fromPlayerLocation = event.getFrom();
    this.toPlayerLocation = event.getTo();
    
	if (plugin.isValid(event.getPlayer().getItemInHand().getTypeId())) {
    {
        if ((this.fromPlayerLocation.getBlockX() != this.toPlayerLocation
          .getBlockX()) || 
          (this.fromPlayerLocation.getBlockY() != this.toPlayerLocation
          .getBlockY()) || (this.fromPlayerLocation
          .getBlockZ() != this.toPlayerLocation.getBlockZ()))
        {
          light.deleteLightSource(this.fromPlayerLocation, player);
          light.createLightSource(this.toPlayerLocation, player, plugin.isLight(event.getPlayer().getItemInHand().getTypeId()));
        }

    }
    }else{
     light.deleteLightSource(this.fromPlayerLocation, player);
  }
}
	 }
		}
}
}

@EventHandler
public void onItemHeldChange(PlayerItemHeldEvent event)
{
  Player player = event.getPlayer();
	if (plugin.getConfig().getBoolean("Worlds." + event.getPlayer().getWorld().getName()) || event.getPlayer().isOp()) {
		if (this.plugin.isUsing.contains(event.getPlayer())) {
  {
    try
    {
		if (event.getPlayer().getInventory().getItem(event.getNewSlot()) != null) {
			if (plugin.isValid(event.getPlayer().getInventory().getItem(event.getNewSlot()).getTypeId())) {
        light.createLightSource(toPlayerLocation, player, plugin.isLight(event.getPlayer().getItemInHand().getTypeId()));
      }else{
          light.deleteLightSource(this.fromPlayerLocation, player);
      }
		}else{
        light.deleteLightSource(this.fromPlayerLocation, player);
    }
    }
    catch (NullPointerException localNullPointerException)
    {
    }
  }
  }
	}}

@EventHandler
public void onPlayerClick(PlayerInteractEvent event){
	Player player = event.getPlayer();
	Location l = player.getLocation();
    if(player.hasPermission("BTL.light")){
    if ((event.getAction() == (Action.RIGHT_CLICK_AIR))) {
    	if(!player.isSneaking()){
		if (plugin.isValid(((Player)player).getItemInHand().getTypeId())) {
    	if (plugin.getConfig().getBoolean("Worlds." + ((Player)player).getWorld().getName()) || ((Player)player).getPlayer().isOp()) {
			if (!plugin.isUsing.contains(player)) {
				plugin.isUsing.add((Player)player);
				player.getWorld().playEffect(l, Effect.SMOKE, 20);
				light.createLightSource(player, plugin.isLight(event.getPlayer().getItemInHand().getTypeId()));
				if(plugin.getConfig().getBoolean("message-torch-enable", true)) {
				player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("Strings.activate"));
				}
			} else {
				if (plugin.isValid((player).getItemInHand().getTypeId())){
					light.deleteLightSource(player);
					player.getWorld().playEffect(l, Effect.SMOKE, 20);
					player.getWorld().playSound(l, Sound.FIRE_IGNITE, 5, 1);
				}
				plugin.isUsing.remove(player);
				if(plugin.getConfig().getBoolean("message-torch-enable", true)) {
				player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("Strings.deactivate"));
				}
			}
    	} else if (!plugin.getConfig().getBoolean("Worlds." + ((Player)player).getWorld().getName())) {
    		if(plugin.getConfig().getBoolean("message-torch-enable", true)) {
    		player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("Strings.disabled"));
    		}
		}
		}
    }
    }
    }
    }


@EventHandler
public void onPlayerQuit(PlayerQuitEvent event)
{
  Player player = event.getPlayer();
  Location l = player.getLocation();
 light.deleteLightSource(l, player);
	plugin.isUsing.remove(event.getPlayer());
}

@EventHandler
public void onPlayerLogin(PlayerLoginEvent event)
{

  Player player = event.getPlayer();
	if (plugin.getConfig().getBoolean("Worlds." + event.getPlayer().getWorld().getName()) || event.getPlayer().isOp()) {
    
  }
}
}