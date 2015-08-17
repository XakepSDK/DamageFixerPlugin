package io.github.xakepsdk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private double maxDamage = 10.0;

    @Override
    public void onEnable() {
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DamageFixer by Xakep_SDK started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DamageFixer stopped!");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getDamage() >= maxDamage) {
            e.setDamage(maxDamage);
        }
    }

    private void loadConfig() {
        getConfig().addDefault("maxDamage", 10.0);
        getConfig().options().copyDefaults(true);
        maxDamage = getConfig().getDouble("maxDamage");
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dfp")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    maxDamage = getConfig().getDouble("maxDamage");
                    sender.sendMessage("DFP was reloaded!");
                    return true;
                } else if (args[0].equalsIgnoreCase("setmaxdamage")) {
                    switch (args.length) {
                        case 2:
                            try {
                                maxDamage = Double.parseDouble(args[1]);
                                getConfig().set("maxDamage", Double.parseDouble(args[1]));
                                saveConfig();
                                sender.sendMessage("Max damage was set.");
                            } catch (NumberFormatException e) {
                                sender.sendMessage("NumberFormatException: maxDamage must be double!");
                            }
                            return true;
                        default:
                            sender.sendMessage("usage: /dfp setmaxdamage <maxDamage>");
                            return false;
                    }
                }
            }
        }
        return false;
    }
}