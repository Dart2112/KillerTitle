package net.lapismc.killertitle;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillerTitle extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info(getName() + " v." + getDescription().getVersion() + " has been enabled!");
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            if (damaged.getHealth() - e.getFinalDamage() > 0) {
                return;
            }
            //We know that the player is going to die and that it is caused by damager
            Player damager = (Player) e.getDamager();
            //Get the message and send it to the killer
            String message = getConfig().getString("KillerMessage").replace("%PLAYER%", damaged.getName());
            message = ChatColor.translateAlternateColorCodes('&', message);
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
            //Send the world wide message
            message = getConfig().getString("BroadcastMessage").replace("%KILLER%", damager.getName()).replace("%PLAYER%", damaged.getName());
            message = ChatColor.translateAlternateColorCodes('&', message);
            Bukkit.broadcastMessage(message);
        }
    }
}
