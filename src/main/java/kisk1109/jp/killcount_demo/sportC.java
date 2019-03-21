package kisk1109.jp.killcount_demo;


import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getLogger;

public class sportC implements Listener {
    private Main plugin;
    public sportC(Main plugin) {
        this.plugin = plugin;
    }

    public String zonmbieName;

    BossBar zonmbie = Bukkit.createBossBar(zonmbieName, BarColor.RED, BarStyle.SOLID);

    public void setName(String s) {
        zonmbie.setTitle(s);
        return;
    }
    public void addPlayer(Player p){
        zonmbie.addPlayer(p);
    }

    public void Clearbar(){
        zonmbie.removeAll();
    }
    public void removePlayer(Player p){
        zonmbie.removePlayer(p);
    }

    public void List() {
        for (String key : plugin.getConfig().getConfigurationSection("MapKeys").getKeys(false)) {
            getLogger().info(key + plugin.config.getConfig().getString("MapKeys." + key));
        }
    }



}
