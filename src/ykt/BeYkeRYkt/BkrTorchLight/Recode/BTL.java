package ykt.BeYkeRYkt.BkrTorchLight.Recode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ykt.BeYkeRYkt.BkrTorchLight.Recode.Listeners.Default.TorchLight;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.ItemLightManager;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.FlashLight;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.HeadLamp;
import ykt.BeYkeRYkt.BkrTorchLight.Recode.mcstats.Metrics;

public class BTL extends JavaPlugin{
	
	public Set<Player> isUsing = new HashSet<Player>();
	public boolean enableOnLoad = false;
	public Set<Player> isHelmetUse = new HashSet<Player>();
	  public final TorchLight playerListener = new TorchLight(this);
	  public HashMap <Item, Location> items = new HashMap <Item, Location>();
	  public int Count = 0;
	  public ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.FlashLight flash = new FlashLight();
	private ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.HeadLamp head = new HeadLamp();
	  private ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.TorchLight tl = new ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.Items.TorchLight();
	  private ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.ItemLightManager ilm = new ykt.BeYkeRYkt.BkrTorchLight.Recode.Manager.ItemLightManager();
    public void onDisable()
    {
      PluginDescriptionFile pdfFile = getDescription();

      getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + 
        " is now disabled.");
  	 for (Player p : Bukkit.getOnlinePlayers()){
  		 Location l = p.getLocation();
  			tl.deleteLightSource(l, p);
  			head.deleteLightSource(l, p);
  		  }
    }
	
	
	public void onEnable()
	  {
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
		//Recipe FlashLight
        if (getConfig().getBoolean("flashlight-enable", true)) {
        	
            ItemStack FlashLight = new ItemStack(Material.STICK, 1);
       	    ItemMeta im1 = FlashLight.getItemMeta();
            ArrayList FlashLight_lore = new ArrayList();
            FlashLight_lore.add("Light level: 14");
            im1.setLore(FlashLight_lore);
            im1.setDisplayName("FlashLight");
            FlashLight.setItemMeta(im1);
        	
            ShapelessRecipe craft1 = new ShapelessRecipe(FlashLight);
            craft1.addIngredient(Material.REDSTONE_TORCH_ON);
            craft1.addIngredient(3, Material.GLOWSTONE_DUST);
            craft1.addIngredient(Material.getMaterial(289));
            getServer().addRecipe(craft1);
          }
		
		
		//Register Events and Types
        ilm.addLightSource(tl);
        
        ilm.setEnabled(tl, true);
        
        ilm.setEnabled(head, true);
        
        ilm.setEnabled(flash, true);
        
        if(ilm.isEnabled(tl)){
	    getServer().getPluginManager().registerEvents(playerListener, this);
	    getLogger().info(tl.name +" Enabled...");
        } if(ilm.isEnabled(head)){
	    getServer().getPluginManager().registerEvents(new ykt.BeYkeRYkt.BkrTorchLight.Recode.Listeners.Default.HeadLamp(this), this);
	    getLogger().info(head.name +" Enabled...");
        } if(ilm.isEnabled(flash)){
	    getServer().getPluginManager().registerEvents(new ykt.BeYkeRYkt.BkrTorchLight.Recode.Listeners.Default.FlashLight(this), this);
	    getLogger().info(flash.name +" Enabled...");
        }
		
		//Config
		PluginDescriptionFile pdFile = getDescription();
	    getLogger().info(pdFile.getName() + " version " + pdFile.getVersion() + 
	      " is now enabled.");
		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header("BkrTorchLight v" + pdFile.getVersion() + " Configuration" + 
						"\nby It's Hard :D" +
					"\nby BeYkeRYkt");
				fc.addDefault("light", "DEFAULT");
				fc.addDefault("message-torch-enable", true);
				fc.addDefault("message-headlamp-enable", true);
				fc.addDefault("flashlight-enable", true);
				
				fc.addDefault("ItemIDs.slot1", 10);
				fc.addDefault("ItemIDs.slot2", 11);
				fc.addDefault("ItemIDs.slot3", 50);
				fc.addDefault("ItemIDs.slot4", 51);
				fc.addDefault("ItemIDs.slot5", 89);
				fc.addDefault("ItemIDs.slot6", 91);
				fc.addDefault("ItemIDs.slot7", 327);
				fc.addDefault("ItemIDs.slot8", 369);
				fc.addDefault("ItemIDs.slot9", 76);
				
				fc.addDefault("LightIDs.slot1", 15);
				fc.addDefault("LightIDs.slot2", 15);
				fc.addDefault("LightIDs.slot3", 14);
				fc.addDefault("LightIDs.slot4", 15);
				fc.addDefault("LightIDs.slot5", 14);
				fc.addDefault("LightIDs.slot6", 15);
				fc.addDefault("LightIDs.slot7", 15);
				fc.addDefault("LightIDs.slot8", 5);
				fc.addDefault("LightIDs.slot9", 9);
				
				fc.addDefault("Strings.activate", "Torch enabled.");
				fc.addDefault("Strings.deactivate", "Torch disabled.");
				fc.addDefault("Strings.disabled", "The plugin is disabled in this world.");
								
				fc.addDefault("Helmets.slot1", 89);
				fc.addDefault("Helmets.slot2", 91);
				fc.addDefault("Helmets.slot3", 124);
				
				fc.addDefault("LightHelmets.slot1", 5);
				fc.addDefault("LightHelmets.slot2", 10);
				fc.addDefault("LightHelmets.slot3", 15);
				
				fc.addDefault("HeadLamp.activate", "HeadLamp enabled.");
				fc.addDefault("HeadLamp.deactivate", "HeadLamp disabled.");
				fc.addDefault("HeadLamp.disabled", "The plugin is disabled in this world.");
				
				List<World> worlds = getServer().getWorlds();
				for (World world: worlds) {
					fc.addDefault("Worlds." + world.getName(), true);
				}
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }	
	
	public ItemLightManager getManager(){
		return getManager();
	}
	
	
	public boolean isValid(int itemID) {
		if (itemID == getConfig().getInt("ItemIDs.slot1")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot2")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot3")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot4")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot5")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot6")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot7")) {
			return true;
		} else if (itemID == getConfig().getInt("ItemIDs.slot8")) {
			return true;
		}else if (itemID == getConfig().getInt("ItemIDs.slot9")) {
			return true;
		}
		return false;
	}
	public int isLight(int itemID) {
		if (itemID == getConfig().getInt("ItemIDs.slot1")) {
			return getConfig().getInt("LightIDs.slot1");
		} else if (itemID == getConfig().getInt("ItemIDs.slot2")) {
			return getConfig().getInt("LightIDs.slot2");
		} else if (itemID == getConfig().getInt("ItemIDs.slot3")) {
			return getConfig().getInt("LightIDs.slot3");
		} else if (itemID == getConfig().getInt("ItemIDs.slot4")) {
			return getConfig().getInt("LightIDs.slot4");
		} else if (itemID == getConfig().getInt("ItemIDs.slot5")) {
			return getConfig().getInt("LightIDs.slot5");
		} else if (itemID == getConfig().getInt("ItemIDs.slot6")) {
			return getConfig().getInt("LightIDs.slot6");
		} else if (itemID == getConfig().getInt("ItemIDs.slot7")) {
			return getConfig().getInt("LightIDs.slot7");
		} else if (itemID == getConfig().getInt("ItemIDs.slot8")) {
			return getConfig().getInt("LightIDs.slot8");
	} else if (itemID == getConfig().getInt("ItemIDs.slot9")) {
		return getConfig().getInt("LightIDs.slot9");
	}
		return itemID;
	}
	public boolean isHelmet(int helmetID) {
		if (helmetID == getConfig().getInt("Helmets.slot1")) {
			return true;
		} else if (helmetID == getConfig().getInt("Helmets.slot2")) {
			return true;
		} else if (helmetID == getConfig().getInt("Helmets.slot3")) {
			return true;
		}
		return false;
	}
	
	public int isHelmetLight(int helmetID) {
		if (helmetID == getConfig().getInt("Helmets.slot1")) {
			return getConfig().getInt("LightHelmets.slot1");
		} else if (helmetID == getConfig().getInt("Helmets.slot2")) {
			return getConfig().getInt("LightHelmets.slot2");
		} else if (helmetID == getConfig().getInt("Helmets.slot3")) {
			return getConfig().getInt("LightHelmets.slot3");
		}
		return helmetID;
	}
}