package me.mrdev.hp;

import me.mrdev.hp.commands.HotCommand;
import me.mrdev.hp.game.Game;
import me.mrdev.hp.listeners.HotListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HotPotato extends JavaPlugin {

    private Game game;
    private String version;

    @Override
    public void onEnable() {
        game = new Game(this);
        getCommand("hotpotato").setExecutor(new HotCommand(this));
        getServer().getPluginManager().registerEvents(new HotListener(this) , this);
        String pack = this.getServer().getClass().getPackage().getName();
        version = pack.substring(pack.lastIndexOf('.') + 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Game getGame() {
        return game;
    }

    public String getBukkitVersion() {
        return version;
    }
}
