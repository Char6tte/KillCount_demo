package kisk1109.jp.killcount_demo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin implements Listener {


    /**
     * モンスターの討伐数を格納するオブジェクト
     */
    public SubjugationInfo subjugation = null;

    sportC sportc = new sportC(this);


    public String zonmbieName;
        BossBar zonmbie = Bukkit.createBossBar(zonmbieName, BarColor.RED, BarStyle.SOLID);

    /**
     * プラグインが有効化されるとき呼び出される
     */
    public void onEnable() {

        // SubjugationInfoオブジェクトをシリアライズ・デシリアライズするのに使うクラスをBukkitに登録する
        ConfigurationSerialization.registerClass(SubjugationInfo.class);

        // 設定されている値を取得してデシリアライズする
        subjugation = (SubjugationInfo) getConfig().get("subjugation");
        if (subjugation == null) {
            // データを読み取れなかった場合はオブジェクトを新規作成
            subjugation = new SubjugationInfo();
        }

        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // イベントリスナーの登録
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        //別クラスコマンド
        getCommand("mgui").setExecutor(new guiCMD());
        getCommand("getuuid").setExecutor(new getUUID());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        sender.sendMessage(getConfig().getString("Massage"));
        return true;
    }


    /**
     * プラグインが無効化されるとき呼び出される
     */
    public void onDisable() {
        // データを設定ファイルに保存する
        saveConfig();
        ClearAllBar();
    }

    // UUID getid = killer.getUniqueId();
    //    // String getuuid = killer.getUniqueId().toString();


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
    public void RemovePlayer(Player p){
        zonmbie.removePlayer(p);
    }


    public void ShowAllPlayer() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            addPlayer(player);

        }
    }

    public void ClearAllBar() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            RemovePlayer(player);

        }
        Clearbar();
    }

    public void ShowBar() {
        setName("現在のゾンビ討伐数" + subjugation.getZombie());
        zonmbie.setProgress(subjugation.getMax() / subjugation.getZombie());
        ShowAllPlayer();
    }
    /**
     * エンティティが死亡するときに呼ばれる
     *
     * @param e
     */
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e) {

        //Monster monsterEnt = (Monster) e.getEntity();
        //Player mcPlayer = (Player)monsterEnt.getKiller();
        //Player target = monsterEnt.getKiller();
        LivingEntity entity = e.getEntity();
        Player player = entity.getKiller();

        // null=プレイヤーが殺したのではないなら
        if (player == null) {
            return; // 何もしない
        } else if (e.getEntityType() == EntityType.ZOMBIE) {
            //キル数加算
            subjugation.setZombie(subjugation.getZombie() + 1);

            int killzonmbie = subjugation.getZombie() * 10;
            //検証用
            Bukkit.broadcastMessage("いままでに倒したゾンビの数：" + ChatColor.YELLOW + subjugation.getZombie());
            ShowBar();
        }
        // データを保存
        getConfig().set("subjugation", subjugation);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        ShowBar();
    }

    //menuGUI イベント

    @EventHandler
    private void inventoryClick(InventoryClickEvent c) {

        Player p = (Player) c.getWhoClicked();

        if (c.getInventory().getTitle().equalsIgnoreCase("Help")) {
            c.setCancelled(true);
            if ((c.getCurrentItem() == null) || (c.getCurrentItem().getType().equals(Material.AIR))) {
                return;
            }


            if (c.getSlot() == 5 && (c.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lClick to get help"))) {

                p.sendMessage("§a >> /shop - to visit shop");
                p.sendMessage("§a >> /home - go to home!");
                p.closeInventory();

                p.playSound(p.getPlayer().getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);

            }
        }
    }
}