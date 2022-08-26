package me.mrdev.hp.listeners;

import me.mrdev.hp.HotPotato;
import me.mrdev.hp.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HotListener implements Listener {

    private HotPotato plugin;
    private Game game;
    private ItemStack hotp;

    public HotListener(HotPotato plugin) {
        this.plugin = plugin;
        this.game = plugin.getGame();
        hotp = game.getHotPotato();
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            if(event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                Player victim = (Player) event.getEntity();
                if (game.isStarted()) {
                    if (damager.equals(game.getSelected())) {
                        event.setDamage(0.0);
                        Arrays.stream(damager.getInventory().getContents())
                                .filter(item -> item.getType() == hotp.getType() && item.getItemMeta().equals(hotp.getItemMeta()))
                                .findAny().ifPresent(hotpotato -> { //we give the victim hotp because itemstack might be > 1 incase broken
                                    damager.getInventory().remove(hotpotato); //and hotp amount is 1 so yeah
                                    victim.getInventory().addItem(hotp);
                                    game.setSelected(victim);
                                });
                        Bukkit.broadcastMessage(ChatColor.RED + victim.getName() + " is it now!!");
                    }else { //don't get damaged by mobs if a game is started
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        if(item.getType() == hotp.getType() && item.getItemMeta().equals(hotp.getItemMeta())) {
            if(game.isStarted() && player == game.getSelected()) {
                event.setCancelled(true);
            }else {
                item.setDurability((short) 0); //you can cast ItemMeta to Damageable and use setDamage but that works for now
                player.sendMessage(ChatColor.DARK_RED + "That's illegal!");
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(game.isStarted() && player == game.getSelected()) {
            ItemStack current = event.getCurrentItem();
            if(current.getType() == hotp.getType() && current.getItemMeta().equals(hotp.getItemMeta())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack food = event.getItem();
        if(food.getType() == hotp.getType() && food.getItemMeta().equals(hotp.getItemMeta())) {
            event.getPlayer().sendMessage(ChatColor.YELLOW + "ITS TOO HOT TO BE EATEN");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(game.isStarted() && player == game.getSelected()) {
            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " was it and he left so he auto loses");
            player.getInventory().remove(hotp); //basically if the one that's it leaves he's the one who loses
            plugin.getServer().getScheduler().cancelTasks(plugin); //because we're only running one Task which is "ChallengeStart"
            game.reset();
        }
    }

}
