package me.mchiappinam.pdghfullpvparenas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;


public class Main extends JavaPlugin implements Listener, CommandExecutor {

	//ArrayList<String> outras = new ArrayList();
	//ArrayList<String> dayz = new ArrayList();
	List<String> jogar = new ArrayList<String>();
	List<String> fps = new ArrayList<String>();
	List<String> red = new ArrayList<String>();
	List<String> dayz = new ArrayList<String>();
	List<String> comprarkit = new ArrayList<String>();
	protected static Economy econ=null;
	
	  public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginCommand("jogar").setExecutor(this);
		getServer().getPluginCommand("fps").setExecutor(this);
		getServer().getPluginCommand("red").setExecutor(this);
		getServer().getPluginCommand("dayz").setExecutor(this);
		getServer().getPluginCommand("comprarkit").setExecutor(this);
		if(!setupEconomy()) {
			getLogger().warning("ERRO: Vault (Economia) nao encontrado!");
			getLogger().warning("Desativando o plugin...");
			getServer().getPluginManager().disablePlugin(this);
        }
		getServer().getConsoleSender().sendMessage("§3[PDGHFullPvPArenas] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHFullPvPArenas] §2Acesse: http://pdgh.net/");
	  }

	  public void onDisable() {
				int online = getServer().getOnlinePlayers().size();
				if (!(online == 0)) {
			  		for (String kit : comprarkit) {
			            econ.depositPlayer(kit, 1);
			            getServer().getPlayerExact(kit).sendMessage("§cVocê recebeu 1 coin de compensação por estar usando o /comprarkit");
			  		}
			  		for (Player p : getServer().getOnlinePlayers()) {
			            World w = Bukkit.getWorld("world");
			            Location loc = new Location(w, 0, 51, 0);
			            loc.setPitch(0);
			            loc.setYaw(180);
			            p.teleport(loc);
			  			clearInv(p);
				  		p.setExp(0.99F);
				  		p.setLevel(0);
  					    p.playSound(p.getLocation(), Sound.ENDERDRAGON_HIT, 1.0F, (byte) 30);
			  		}
					jogar.clear();
					fps.clear();
					red.clear();
					dayz.clear();
					comprarkit.clear();
		      	    getServer().broadcastMessage(" ");
		      	    getServer().broadcastMessage("§d§l[ARENAS] §c "+ChatColor.BOLD+"➥ §dSistema de arenas recarregado");
		      	    getServer().broadcastMessage(" ");
				}
		getServer().getConsoleSender().sendMessage("§3[PDGHFullPvPArenas] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHFullPvPArenas] §2Acesse: http://pdgh.net/");
	  }

		@EventHandler
		private void onRespawn(PlayerRespawnEvent e) {
	  		e.getPlayer().setExp(0.99F);
	  		e.getPlayer().setLevel(0);
		}

		@EventHandler
		private void onDeath(PlayerDeathEvent e) {
				Player p = e.getEntity().getPlayer();
				e.getDrops().clear();
	  			e.setDroppedExp(0);
		  		p.setExp(0.99F);
	  			p.setLevel(0);
			    World pWorld = p.getWorld();
			    pWorld.strikeLightningEffect(p.getLocation());
		  			if(e.getEntity().getKiller() instanceof Player) {
		  				Player killer = e.getEntity().getKiller();
		  					if (jogar.contains(killer.getName())) {
		  						killer.getInventory().clear();
		  						Kit(killer);
		  						killer.setHealth(20);
		  						killer.setFoodLevel(20);
		  						killer.setLevel(killer.getLevel() + 1);
		  					    p.playSound(p.getLocation(), Sound.BURP, 1.0F, (byte) 30);
		  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §f"+killer.getName()+" §cmatou §f"+p.getName()+" §cna arena §lJogar");
		  			      	    if(comprarkit.contains(killer.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §d"+killer.getName()+" §cestá usando o /comprarkit");
		  			      	    }
		  			      	    if(comprarkit.contains(p.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §d"+p.getName()+" §cestava usando o /comprarkit");
		  			      	    }
		  					}else if (fps.contains(killer.getName())) {
		  						killer.getInventory().clear();
		  						Kit(killer);
		  						killer.setHealth(20);
		  						killer.setFoodLevel(20);
		  						killer.setLevel(killer.getLevel() + 1);
		  					    p.playSound(p.getLocation(), Sound.BURP, 1.0F, (byte) 30);
		  			      	    getServer().broadcastMessage("§a§l[/FPS] §f"+killer.getName()+" §cmatou §f"+p.getName()+" §cna arena §lFPS");
		  			      	    if(comprarkit.contains(killer.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/FPS] §d"+killer.getName()+" §cestá usando o /comprarkit");
		  			      	    }
		  			      	    if(comprarkit.contains(p.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/FPS] §d"+p.getName()+" §cestava usando o /comprarkit");
		  			      	    }
		  					}else if (red.contains(killer.getName())) {
		  						killer.getInventory().clear();
		  						Kit(killer);
		  						killer.setHealth(20);
		  						killer.setFoodLevel(20);
		  						killer.setLevel(killer.getLevel() + 1);
		  					    p.playSound(p.getLocation(), Sound.BURP, 1.0F, (byte) 30);
		  			      	    getServer().broadcastMessage("§a§l[/RED] §f"+killer.getName()+" §cmatou §f"+p.getName()+" §cna arena §lRedonda");
		  			      	    if(comprarkit.contains(killer.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/RED] §d"+killer.getName()+" §cestá usando o /comprarkit");
		  			      	    }
		  			      	    if(comprarkit.contains(p.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/RED] §d"+p.getName()+" §cestava usando o /comprarkit");
		  			      	    }
		  					}else if (dayz.contains(killer.getName())) {
		  						killer.getInventory().clear();
		  						KitDayZ(killer);
		  						killer.setHealth(20);
		  						killer.setFoodLevel(20);
		  						killer.setLevel(killer.getLevel() + 1);
		  					    p.playSound(p.getLocation(), Sound.BURP, 1.0F, (byte) 30);
		  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §f"+killer.getName()+" §cmatou §f"+p.getName()+" §cna arena §lDayZ");
		  			      	    if(comprarkit.contains(killer.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §d"+killer.getName()+" §cestá usando o /comprarkit");
		  			      	    }
		  			      	    if(comprarkit.contains(p.getName())) {
			  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §d"+p.getName()+" §cestava usando o /comprarkit");
		  			      	    }
		  					}
					}else{
	  					if (jogar.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §f"+p.getName()+" §cmorreu na arena §lJogar");
	  			      	    if(comprarkit.contains(p.getName())) {
		  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §d"+p.getName()+" §cestava usando o /comprarkit");
	  			      	    }
	  					}else if (fps.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/FPS] §f"+p.getName()+" §cmorreu na arena §lFPS");
	  			      	    if(comprarkit.contains(p.getName())) {
		  			      	    getServer().broadcastMessage("§a§l[/FPS] §d"+p.getName()+" §cestava usando o /comprarkit");
	  			      	    }
	  					}else if (red.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/RED] §f"+p.getName()+" §cmorreu na arena §lRedonda");
	  			      	    if(comprarkit.contains(p.getName())) {
		  			      	    getServer().broadcastMessage("§a§l[/RED] §d"+p.getName()+" §cestava usando o /comprarkit");
	  			      	    }
	  					}else if (dayz.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §f"+p.getName()+" §cmorreu na arena §lDayZ");
	  			      	    if(comprarkit.contains(p.getName())) {
		  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §d"+p.getName()+" §cestava usando o /comprarkit");
	  			      	    }
	  					}
					}
					jogar.remove(p.getName());
					fps.remove(p.getName());
					red.remove(p.getName());
			  	    dayz.remove(p.getName());
			  	    comprarkit.remove(p.getName());
		}
		
		@EventHandler
		private void onQuit(PlayerQuitEvent e) {
            World w = Bukkit.getWorld("world");
			Player p = e.getPlayer();
				if (jogar.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/JOGAR] §f"+p.getName()+" §4desconectou-se na arena §lJogar");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}else if (fps.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/FPS] §f"+p.getName()+" §4desconectou-se na arena §lFPS");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/FPS] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}else if (red.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/RED] §f"+p.getName()+" §4desconectou-se na arena §lRedonda");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/RED] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}else if (dayz.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/DAYZ] §f"+p.getName()+" §4desconectou-se na arena §lDayZ");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}
			jogar.remove(p.getName());
			fps.remove(p.getName());
			red.remove(p.getName());
	  	    dayz.remove(p.getName());
	  	    comprarkit.remove(p.getName());
        	clearInv(p);
	  		p.setExp(0.99F);
  			p.setLevel(0);
            Location loc = new Location(w, 0, 51, 0);
            loc.setPitch(0);
            loc.setYaw(180);
            p.teleport(loc);
		}
		
		@EventHandler
		private void onKick(PlayerKickEvent e) {
            World w = Bukkit.getWorld("world");
			Player p = e.getPlayer();
				if (jogar.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/JOGAR] §f"+p.getName()+" §4desconectou-se na arena §lJogar");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/JOGAR] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}else if (fps.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/FPS] §f"+p.getName()+" §4desconectou-se na arena §lFPS");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/FPS] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}else if (red.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/RED] §f"+p.getName()+" §4desconectou-se na arena §lRedonda");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/RED] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}else if (dayz.contains(p.getName())) {
					getServer().broadcastMessage("§a§l[/DAYZ] §f"+p.getName()+" §4desconectou-se na arena §lDayZ");
			      	    if(comprarkit.contains(p.getName())) {
	  			      	    getServer().broadcastMessage("§a§l[/DAYZ] §d"+p.getName()+" §cestava usando o /comprarkit");
  			      	    }
				}
			jogar.remove(p.getName());
			fps.remove(p.getName());
			red.remove(p.getName());
	  	    dayz.remove(p.getName());
	  	    comprarkit.remove(p.getName());
        	clearInv(p);
	  		p.setExp(0.99F);
  			p.setLevel(0);
            Location loc = new Location(w, 0, 51, 0);
            loc.setPitch(0);
            loc.setYaw(180);
            p.teleport(loc);
		}

	    @Override
	    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
	        final Player p = (Player) cs;
            if(cmd.getName().equalsIgnoreCase("jogar")) {
      	      if ((jogar.contains(p.getName()) || (fps.contains(p.getName()) || (red.contains(p.getName()) || dayz.contains(p.getName()))))) {
      	    	  p.sendMessage("§c§lVocê já está na arena!");
      	    	  return true;
      	      }
      		jogar.add(p.getName());
      	    p.sendMessage("§b§lTeleportando...");
			p.closeInventory();
      	    clearInv(p);
      	    	getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		    	public void run() {
		    getServer().broadcastMessage("§a§l[/JOGAR] §f"+p.getName()+" §aentrou na arena §lJogar");
			p.closeInventory();
      	    Kit(p);
      	    for(PotionEffect pe : p.getActivePotionEffects()) {
      	    	p.removePotionEffect(pe.getType());
    	    }
            World w = Bukkit.getWorld("world");
            Random randomgen = new Random();
            int i = randomgen.nextInt(12) + 1;
          if(i == 1){
              Location loc = new Location(w, 50035, 76, 50000);
              loc.setPitch(0);
              loc.setYaw(90);
              p.teleport(loc);
          }else if(i == 2){
              Location loc = new Location(w, 49964, 70, 50000);
              loc.setPitch(0);
              loc.setYaw(270);
              p.teleport(loc);
          }else if(i == 3){
              Location loc = new Location(w, 49998, 76, 50041);
              loc.setPitch(0);
              loc.setYaw(180);
              p.teleport(loc);
          }else if(i == 4){
              Location loc = new Location(w, 50026, 68, 50020);
              loc.setPitch(0);
              loc.setYaw(180);
              p.teleport(loc);
          }else if(i == 5){
              Location loc = new Location(w, 50024 ,80, 50020);
              loc.setPitch(0);
              loc.setYaw(90);
              p.teleport(loc);
          }else if(i == 6){
              Location loc = new Location(w, 50000, 70, 50000);
              loc.setPitch(0);
              loc.setYaw(270);
              p.teleport(loc);
          }else if(i == 7){
              Location loc = new Location(w, 49986, 89, 49968);
              loc.setPitch(0);
              loc.setYaw(90);
              p.teleport(loc);
          }else if(i == 8){
              Location loc = new Location(w, 49972, 85, 49992);
              loc.setPitch(0);
              loc.setYaw(180);
              p.teleport(loc);
          }else if(i == 9){
              Location loc = new Location(w, 49986, 84, 50011);
              loc.setPitch(0);
              loc.setYaw(180);
              p.teleport(loc);
          }else if(i == 10){
              Location loc = new Location(w, 49969, 70, 50019);
              loc.setPitch(0);
              loc.setYaw(0);
              p.teleport(loc);
          }else if(i == 11){
              Location loc = new Location(w, 49998, 73, 50014);
              loc.setPitch(0);
              loc.setYaw(180);
              p.teleport(loc);
          }else if(i == 12){
              Location loc = new Location(w, 50011, 85, 50027);
              loc.setPitch(0);
              loc.setYaw(90);
              p.teleport(loc);
          }
		    p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0F, (byte) 30);
		      }
		    }, 60L);
            return true;
    }
            if(cmd.getName().equalsIgnoreCase("fps")) {
        	      if ((jogar.contains(p.getName()) || (fps.contains(p.getName()) || (red.contains(p.getName()) || dayz.contains(p.getName()))))) {
          	    	  p.sendMessage("§c§lVocê já está na arena!");
          	    	  return true;
          	      }
          		fps.add(p.getName());
          	    p.sendMessage("§b§lTeleportando...");
    			p.closeInventory();
          	    clearInv(p);
          	    	getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    		    	public void run() {
    	        getServer().broadcastMessage("§a§l[/FPS] §f"+p.getName()+" §aentrou na arena §lFPS");
    			p.closeInventory();
          	    Kit(p);
          	    for(PotionEffect pe : p.getActivePotionEffects()) {
          	    	p.removePotionEffect(pe.getType());
        	    }
                World w = Bukkit.getWorld("world");
                Random randomgen = new Random();
                int i = randomgen.nextInt(12) + 1;
              if(i == 1){
                  Location loc = new Location(w, -24965, 76, -25000);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }else if(i == 2){
                  Location loc = new Location(w, -25036, 70, -25000);
                  loc.setPitch(0);
                  loc.setYaw(270);
                  p.teleport(loc);
              }else if(i == 3){
                  Location loc = new Location(w, -25002, 76, -24959);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 4){
                  Location loc = new Location(w, -24974, 68, -24980);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 5){
                  Location loc = new Location(w, -24976, 80, -24980);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }else if(i == 6){
                  Location loc = new Location(w, -25000, 70, -25000);
                  loc.setPitch(0);
                  loc.setYaw(270);
                  p.teleport(loc);
              }else if(i == 7){
                  Location loc = new Location(w, -25014, 89, -25032);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }else if(i == 8){
                  Location loc = new Location(w, -25028, 85, -25008);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 9){
                  Location loc = new Location(w, -25014, 84, -24989);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 10){
                  Location loc = new Location(w, -25031, 70, -24981);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 11){
                  Location loc = new Location(w, -25002, 73, -24986);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 12){
                  Location loc = new Location(w, -24989, 85, -24973);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }
  		    p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0F, (byte) 30);
    			      }
    			    }, 60L);
    	            return true;
    	    }
            if(cmd.getName().equalsIgnoreCase("red")) {
        	      if ((jogar.contains(p.getName()) || (fps.contains(p.getName()) || (red.contains(p.getName()) || dayz.contains(p.getName()))))) {
          	    	  p.sendMessage("§c§lVocê já está na arena!");
          	    	  return true;
          	      }
        		red.add(p.getName());
          	    p.sendMessage("§b§lTeleportando...");
    			p.closeInventory();
          	    clearInv(p);
          	    	getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    		    	public void run() {
    	        getServer().broadcastMessage("§a§l[/RED] §f"+p.getName()+" §aentrou na arena §lRedonda");
    			p.closeInventory();
          	    Kit(p);
          	    for(PotionEffect pe : p.getActivePotionEffects()) {
          	    	p.removePotionEffect(pe.getType());
        	    }
                World w = Bukkit.getWorld("world");
                Random randomgen = new Random();
                int i = randomgen.nextInt(14) + 1;
              if(i == 1){
                  Location loc = new Location(w, 25000, 87, 25000);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }else if(i == 2){
                  Location loc = new Location(w, 25000, 77, 25000);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 3){
                  Location loc = new Location(w, 24990, 70, 25011);
                  loc.setPitch(0);
                  loc.setYaw(270);
                  p.teleport(loc);
              }else if(i == 4){
                  Location loc = new Location(w, 24979, 70, 24992);
                  loc.setPitch(0);
                  loc.setYaw(270);
                  p.teleport(loc);
              }else if(i == 5){
                  Location loc = new Location(w, 24991, 70, 24991);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 6){
                  Location loc = new Location(w, 24987, 74, 24981);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 7){
                  Location loc = new Location(w, 25014, 78, 24996);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 8){
                  Location loc = new Location(w, 25013, 78, 25006);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 9){
                  Location loc = new Location(w, 25014, 70, 25013);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 10){
                  Location loc = new Location(w, 25014, 70, 24988);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }else if(i == 11){
                  Location loc = new Location(w, 24991, 70, 25018);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }else if(i == 12){
                  Location loc = new Location(w, 25000, 98, 24997);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 13){
                  Location loc = new Location(w, 25000, 70, 24972);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 14){
                  Location loc = new Location(w, 24979, 75, 25025);
                  loc.setPitch(0);
                  loc.setYaw(270);
                  p.teleport(loc);
              }
  		    p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0F, (byte) 30);
    			      }
    			    }, 60L);
    	            return true;
    	    }
            if(cmd.getName().equalsIgnoreCase("dayz")) {
        	      if ((jogar.contains(p.getName()) || (fps.contains(p.getName()) || (red.contains(p.getName()) || dayz.contains(p.getName()))))) {
          	    	  p.sendMessage("§c§lVocê já está na arena!");
          	    	  return true;
          	      }
      			dayz.add(p.getName());
          	    p.sendMessage("§b§lTeleportando...");
    			p.closeInventory();
          	    clearInv(p);
          	    	getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    		    	public void run() {
    	        getServer().broadcastMessage("§a§l[/DAYZ] §f"+p.getName()+" §aentrou na arena §lDayZ");
    			p.closeInventory();
          	    KitDayZ(p);
          	    for(PotionEffect pe : p.getActivePotionEffects()) {
          	    	p.removePotionEffect(pe.getType());
        	    }
                World w = Bukkit.getWorld("world");
                Random randomgen = new Random();
                int i = randomgen.nextInt(8) + 1;
              if(i == 1){
                  Location loc = new Location(w, 25012, 70, -24988);
                  loc.setPitch(0);
                  loc.setYaw(135);
                  p.teleport(loc);
              }else if(i == 2){
                  Location loc = new Location(w, 25012, 70, -25012);
                  loc.setPitch(0);
                  loc.setYaw(45);
                  p.teleport(loc);
              }else if(i == 3){
                  Location loc = new Location(w, 24988, 70, -25012);
                  loc.setPitch(0);
                  loc.setYaw(315);
                  p.teleport(loc);
              }else if(i == 4){
                  Location loc = new Location(w, 24988, 70, -24988);
                  loc.setPitch(0);
                  loc.setYaw(225);
                  p.teleport(loc);
              }else if(i == 5){
                  Location loc = new Location(w, 25000, 70, -25104);
                  loc.setPitch(0);
                  loc.setYaw(0);
                  p.teleport(loc);
              }else if(i == 6){
                  Location loc = new Location(w, 25000, 70, -24896);
                  loc.setPitch(0);
                  loc.setYaw(180);
                  p.teleport(loc);
              }else if(i == 7){
                  Location loc = new Location(w, 24896, 70, -25000);
                  loc.setPitch(0);
                  loc.setYaw(270);
                  p.teleport(loc);
              }else if(i == 8){
                  Location loc = new Location(w, 25104, 70, -25000);
                  loc.setPitch(0);
                  loc.setYaw(90);
                  p.teleport(loc);
              }
  		    p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0F, (byte) 30);
    			      }
    			    }, 60L);
    	            return true;
    	    }
            if(cmd.getName().equalsIgnoreCase("comprarkit")) {
            	if (!(jogar.contains(p.getName()) || (fps.contains(p.getName()) || (red.contains(p.getName()) || dayz.contains(p.getName()))))) {
            		p.sendMessage("§c§lVocê não está na arena!");
            		return true;
            	}
            	if(dayz.contains(p.getName())) {
    		        if(!(econ.getBalance(p.getName()) >= 1)) {
    				    p.sendMessage("§d[/Comprarkit] §cVocê não tem money suficiente.");
    				    p.sendMessage("§d[/Comprarkit] §cMoney necessário: §a$1§c.");
    				    return true;
    				}
            		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                    	all.playSound(all.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
            		}
	      	        getServer().broadcastMessage(" ");
	      	        getServer().broadcastMessage("§d[/Comprarkit] §l"+p.getName()+"§d comprou o kit DayZ.");
	      	        getServer().broadcastMessage(" ");
      	    		comprarkit.add(p.getName());
      	    		econ.withdrawPlayer(p.getName(), 1);
	      	        p.sendMessage("§d[/Comprarkit] O kit DayZ lhe custou 1 coin.");
					p.getInventory().clear();
					KitDayZ(p);
            	}else{
    		        if(!(econ.getBalance(p.getName()) >= 1)) {
    				    p.sendMessage("§d[/Comprarkit] §cVocê não tem money suficiente.");
    				    p.sendMessage("§d[/Comprarkit] §cMoney necessário: §a$1§c.");
    				    return true;
    				}
	      	        getServer().broadcastMessage(" ");
	      	        getServer().broadcastMessage("§d[/Comprarkit] §l"+p.getName()+"§d comprou o kit PvP.");
	      	        getServer().broadcastMessage(" ");
      	    		comprarkit.add(p.getName());
      	    		econ.withdrawPlayer(p.getName(), 1);
	      	        p.sendMessage("§d[/Comprarkit] O kit PvP lhe custou 1 coin.");
					p.getInventory().clear();
					Kit(p);
            	}
  	            return true;
            }
	        return false;
	    }

		public void clearInv(Player p) {
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().clear();
		}

		public void Kit(Player p) {
        	if(comprarkit.contains(p.getName())) {
			    ItemStack espada = new ItemStack(Material.DIAMOND_SWORD, 1);
			    espada.addEnchantment(Enchantment.DAMAGE_ALL, 3);
			    espada.addEnchantment(Enchantment.FIRE_ASPECT, 2);
			    espada.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack arco = new ItemStack(Material.BOW, 1);
			    arco.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE , 7);
			    arco.addEnchantment(Enchantment.ARROW_FIRE, 1);
			    arco.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			    arco.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
			    elmo.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 6);
			    elmo.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
			    ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			    peito.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 6);
			    peito.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
			    ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			    calca.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 6);
			    calca.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
			    ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
			    bota.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 6);
			    bota.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
				p.getInventory().clear();
			    p.getInventory().addItem(espada);
			    p.getInventory().addItem(arco);
			    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 45, (short) 1));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 40, (short) 8233));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 40, (short) 8226));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 40, (short) 8225));
			    p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
				p.getInventory().setHelmet(elmo);
				p.getInventory().setChestplate(peito);
				p.getInventory().setLeggings(calca);
				p.getInventory().setBoots(bota);
        		return;
        	}
			if(p.hasPermission("pdgh.vip")) {
			    ItemStack espada = new ItemStack(Material.DIAMOND_SWORD, 1);
			    espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			    espada.addEnchantment(Enchantment.FIRE_ASPECT, 2);
			    espada.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack arco = new ItemStack(Material.BOW, 1);
			    arco.addEnchantment(Enchantment.ARROW_DAMAGE , 5);
			    arco.addEnchantment(Enchantment.ARROW_FIRE, 1);
			    arco.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			    arco.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
			    elmo.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 4);
			    elmo.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			    peito.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 4);
			    peito.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			    calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 4);
			    calca.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
			    bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 4);
			    bota.addEnchantment(Enchantment.DURABILITY, 3);
				//p.getInventory().clear();
			    p.getInventory().addItem(espada);
			    p.getInventory().addItem(arco);
			    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 15, (short) 1));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 20, (short) 8233));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 20, (short) 8226));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 20, (short) 8225));
			    p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
				p.getInventory().setHelmet(elmo);
				p.getInventory().setChestplate(peito);
				p.getInventory().setLeggings(calca);
				p.getInventory().setBoots(bota);
			}else{
				ItemStack espada = new ItemStack(Material.DIAMOND_SWORD, 1);
				espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
				espada.addEnchantment(Enchantment.FIRE_ASPECT, 1);
				ItemStack arco = new ItemStack(Material.BOW, 1);
				arco.addEnchantment(Enchantment.ARROW_DAMAGE , 3);
				arco.addEnchantment(Enchantment.ARROW_FIRE, 1);
				arco.addEnchantment(Enchantment.ARROW_INFINITE, 1);
				arco.addEnchantment(Enchantment.DURABILITY, 1);
				ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
				elmo.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 2);
				elmo.addEnchantment(Enchantment.DURABILITY, 2);
				ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
				peito.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 2);
				peito.addEnchantment(Enchantment.DURABILITY, 2);
				ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
				calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 2);
				calca.addEnchantment(Enchantment.DURABILITY, 2);
		    	ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
		    	bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 2);
		    	bota.addEnchantment(Enchantment.DURABILITY, 2);
		    	//p.getInventory().clear();
		    	p.getInventory().addItem(espada);
		    	p.getInventory().addItem(arco);
		    	p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 7, (short) 1));
		    	p.getInventory().addItem(new ItemStack(Material.POTION, 10, (short) 8233));
		    	p.getInventory().addItem(new ItemStack(Material.POTION, 10, (short) 8226));
		    	p.getInventory().addItem(new ItemStack(Material.POTION, 10, (short) 8225));
		    	p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
		    	p.getInventory().setHelmet(elmo);
		    	p.getInventory().setChestplate(peito);
		    	p.getInventory().setLeggings(calca);
		    	p.getInventory().setBoots(bota);
			}
		}
		
		public void KitDayZ(Player p) {
        	if(comprarkit.contains(p.getName())) {
			    ItemStack espada = new ItemStack(Material.DIAMOND_SWORD, 1);
			    espada.addEnchantment(Enchantment.DAMAGE_ALL, 4);
			    ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
			    elmo.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 3);
			    elmo.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			    peito.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 3);
			    peito.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			    calca.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 3);
			    calca.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
			    bota.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 3);
			    bota.addEnchantment(Enchantment.DURABILITY, 3);
				//p.getInventory().clear();
			    p.getInventory().addItem(espada);
			    p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.IRON_AXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.SUGAR, 60));
			    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 10, (short) 1));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 10, (short) 8226));
			    p.getInventory().addItem(new ItemStack(Material.GOLD_PICKAXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.GHAST_TEAR, 128));
				p.getInventory().setHelmet(elmo);
				p.getInventory().setChestplate(peito);
				p.getInventory().setLeggings(calca);
				p.getInventory().setBoots(bota);
        		return;
			}
			if(p.hasPermission("pdgh.vip")) {
			    ItemStack espada = new ItemStack(Material.DIAMOND_SWORD, 1);
			    espada.addEnchantment(Enchantment.DAMAGE_ALL, 3);
			    ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
			    elmo.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 2);
			    elmo.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			    peito.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 2);
			    peito.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			    calca.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 2);
			    calca.addEnchantment(Enchantment.DURABILITY, 3);
			    ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
			    bota.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 2);
			    bota.addEnchantment(Enchantment.DURABILITY, 3);
				//p.getInventory().clear();
			    p.getInventory().addItem(espada);
			    p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.IRON_AXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.SUGAR, 30));
			    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3, (short) 1));
			    p.getInventory().addItem(new ItemStack(Material.POTION, 5, (short) 8226));
			    p.getInventory().addItem(new ItemStack(Material.GOLD_PICKAXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE, 1));
			    p.getInventory().addItem(new ItemStack(Material.GHAST_TEAR, 128));
				p.getInventory().setHelmet(elmo);
				p.getInventory().setChestplate(peito);
				p.getInventory().setLeggings(calca);
				p.getInventory().setBoots(bota);
			}else{
				ItemStack espada = new ItemStack(Material.DIAMOND_SWORD, 1);
				espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
				ItemStack elmo = new ItemStack(Material.DIAMOND_HELMET, 1);
				elmo.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 1);
				elmo.addEnchantment(Enchantment.DURABILITY, 1);
				ItemStack peito = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
				peito.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 1);
				peito.addEnchantment(Enchantment.DURABILITY, 1);
				ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
				calca.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 1);
				calca.addEnchantment(Enchantment.DURABILITY, 1);
				ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS, 1);
				bota.addEnchantment(Enchantment.PROTECTION_PROJECTILE , 1);
				bota.addEnchantment(Enchantment.DURABILITY, 1);
				//p.getInventory().clear();
				p.getInventory().addItem(espada);
				p.getInventory().addItem(new ItemStack(Material.GOLD_AXE, 1));
				p.getInventory().addItem(new ItemStack(Material.IRON_AXE, 1));
				p.getInventory().addItem(new ItemStack(Material.SUGAR, 15));
				p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
				p.getInventory().addItem(new ItemStack(Material.POTION, 2, (short) 8226));
			    p.getInventory().addItem(new ItemStack(Material.GHAST_TEAR, 128));
				p.getInventory().setHelmet(elmo);
				p.getInventory().setChestplate(peito);
				p.getInventory().setLeggings(calca);
				p.getInventory().setBoots(bota);
		}
	}
		
		private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp=getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ=rsp.getProvider();
	        return econ != null;
		}
}