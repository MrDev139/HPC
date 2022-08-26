package me.mrdev.hp.tasks;

import me.mrdev.hp.HotPotato;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class TaskTimer { //super class for ChallengeStart class

    private HotPotato plugin;
    private BukkitTask task;
    private int time = 1; //default is 1 second(20 ticks)

    public TaskTimer(HotPotato plugin , int time) {
        this.plugin = plugin;
        this.time = time;
    }

    public TaskTimer(HotPotato plugin) {
        this.plugin = plugin;
    }

    public void start() {
        this.task = plugin.getServer().getScheduler().runTaskTimer(plugin , this::Do, 20L * time, 20L * time);
    }

    protected void cancel() {
        plugin.getServer().getScheduler().cancelTask(task.getTaskId());
    }

    public boolean isStarted() {
        return task != null;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public BukkitTask getTask() {
        return task;
    }

    public abstract void Do();

}
