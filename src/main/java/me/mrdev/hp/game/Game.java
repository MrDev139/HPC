package me.mrdev.hp.game;

import me.mrdev.hp.HotPotato;
import me.mrdev.hp.tasks.ChallengeStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private HotPotato plugin;
    private long time = 10; //default is 10 and we want each time to reset to 10 secs
    private boolean started = false;
    private Player selected;

    public Game(HotPotato plugin) {
        this.plugin = plugin;
    }

    public void setStarted(boolean started , long time) {
        this.time = time;
        setStarted(started);
    }

    public void setStarted(boolean started) {
        this.started = started;
        if(started) {
            prepareRandom();
            Bukkit.broadcastMessage(ChatColor.YELLOW + "HOT POTATO CHALLENGE HAS STARTED , " + ChatColor.RED + selected.getName() + ChatColor.YELLOW + " IS IT");
            new ChallengeStart(plugin).start();
        }
    }

    public long getTime() {
        return time;
    }

    public void reset() {
        started = false;
        selected = null;
        //we don't care about resetting this because it will get fresh value each time the task runs
    }

    private void prepareRandom() {
        List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        selected = players.get(ThreadLocalRandom.current().nextInt(players.size()));
        selected.getInventory().addItem(getHotPotato());
    }

    public boolean isStarted() {
        return started;
    }

    public Player getSelected() {
        return selected;
    }

    public void setSelected(Player choosen) {
        selected = choosen;
    }

    public ItemStack getHotPotato() {
        ItemStack hotpotato = new ItemStack(Material.BAKED_POTATO);
        ItemMeta meta = hotpotato.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "HotPotato");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "HIT SOMEONE QUICKLY BEFORE YOU EXPLODE"));
        hotpotato.setItemMeta(meta);
        return hotpotato;
    }

}
