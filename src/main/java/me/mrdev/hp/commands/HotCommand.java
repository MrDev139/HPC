package me.mrdev.hp.commands;

import me.mrdev.hp.HotPotato;
import me.mrdev.hp.game.Game;
import me.mrdev.hp.utils.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HotCommand implements CommandExecutor {

    private HotPotato plugin; //might be useful
    private Game game;

    public HotCommand(HotPotato plugin) {
        this.plugin = plugin;
        this.game = plugin.getGame();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("INGAME ONLY CMD!");
            return true;
        }else {
            Player player = (Player) sender;
            if(player.hasPermission("HotPotato.start")) {
                int len = args.length;
                if(len >= 1) {
                    String sub = args[0];
                    if(sub.equalsIgnoreCase("start")) {
                        if(!game.isStarted()) {
                            if (len != 2) {
                                player.sendMessage(ChatColor.YELLOW + "HotPotato challenge duration has been set to default(10 secs)");
                                game.setStarted(true);
                            } else {
                                if (NumberUtils.isLong(args[1])) {
                                    long time = Long.parseLong(args[1]);
                                    player.sendMessage(ChatColor.YELLOW + "HotPotato challenge duration has been set to " + time + " seconds");
                                    game.setStarted(true, time);
                                } else {
                                    player.sendMessage(ChatColor.YELLOW + "Invalid parameters /hotpotato start <time>(seconds in numbers only)");
                                }
                            }
                        }else {
                            player.sendMessage("Game is already started");
                        }
                    }else if(sub.equalsIgnoreCase("check")) {
                        String info = "Game started: " + game.isStarted() + " | Selected: NOPE";
                        if(game.getSelected() != null) {
                            info = info.replace("NOPE" , game.getSelected().getName());
                        }
                        player.sendMessage(info);
                    }
                }else {
                    player.sendMessage("try /hotpotato <start/check>");
                }
            }else {
                player.sendMessage("No permission sorry");
            }
        }
        return true;
    }

}
