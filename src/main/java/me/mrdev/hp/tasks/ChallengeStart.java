package me.mrdev.hp.tasks;

import me.mrdev.hp.HotPotato;
import me.mrdev.hp.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ChallengeStart extends TaskTimer {

    private HotPotato plugin;
    private Game game;
    long time;

    public ChallengeStart(HotPotato plugin) {
        super(plugin , 1);
        this.plugin = plugin;
        this.game = plugin.getGame();
        this.time = game.getTime();
    }

    @Override
    public void Do() {
        time--;
        if (time <= 0) {
            Player selected = game.getSelected();
            selected.getInventory().remove(game.getHotPotato());
            /*1.8 Sound.EXPLODE
            Sound.ENTITY_GENERIC_EXPLODE 1.9 , 1.10 , 1.11 , 1.12 , 1.13 , 1.14 , 1.15 , 1.16 , 1.17*/
            selected.getWorld().playSound(selected.getLocation() , plugin.getBukkitVersion().startsWith("v1_8") ? Sound.valueOf("EXPLODE") : Sound.valueOf("ENTITY_GENERIC_EXPLODE") , 10 , -1);
            selected.setHealth(0.0);
            Bukkit.broadcastMessage(ChatColor.YELLOW + selected.getName() + " Has been exploded!");
            game.reset();
            cancel();
        } else if (time <= 10) {
            Bukkit.broadcastMessage(ChatColor.RED + String.valueOf(time) + ChatColor.YELLOW  + " seconds left before the explosion!");
        } else if(time >= 60 && time % 60 == 0) {
            Bukkit.broadcastMessage(ChatColor.GREEN+ String.valueOf(time / 60) + ChatColor.YELLOW + " minutes left before the explosion!");
        }else if(time <= 60 && time % 10 == 0){ //if only a minute or less is left
            Bukkit.broadcastMessage(ChatColor.YELLOW + String.valueOf(time) + ChatColor.YELLOW + " seconds left before the explosion!");
        }
    }
}
