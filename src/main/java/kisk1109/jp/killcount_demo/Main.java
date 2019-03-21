package kisk1109.jp.killcount_demo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;




public final class Main extends JavaPlugin implements Listener {

    /**
     * モンスターの討伐数を格納するオブジェクト
     */
    public SubjugationInfo subjugation = null;

    sportC sportc = new sportC(this);
    CustomConfig customconfig = new CustomConfig(this);
    CustomConfig config,message;

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
        //config用
        customconfig.saveDefaultConfig();
        config = new CustomConfig(this,"config.yml");

        //コマンドクラス分割
        getCommand("getuuid").setExecutor(new getUUID());
        // イベントリスナーの登録
        getServer().getPluginManager().registerEvents(this, this);
    }


    /**
     * プラグインが無効化されるとき呼び出される
     */
    public void onDisable() {
        // データを設定ファイルに保存する
        saveConfig();
        ClearAllBar();
    }

    //UUID getid = killer.getUniqueId();
    //String getuuid = killer.getUniqueId().toString();
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
        }else if (e.getEntityType() == EntityType.ZOMBIE) {
            //キル数加算
            subjugation.setZombie( subjugation.getZombie() + 1);

            int killzonmbie = subjugation.getZombie() * 10;
            //検証用
            Bukkit.broadcastMessage("いままでに倒したゾンビの数：" + ChatColor.YELLOW +  subjugation.getZombie());
            ShowBar();
        }
        // データを保存
        getConfig().set("subjugation",  subjugation);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e){
        ShowBar();
    }


    public void ShowAllPlayer(){
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            sportc.addPlayer(player);

        }
    }

    public void ClearAllBar(){
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            sportc.removePlayer(player);

        }
        sportc.Clearbar();
    }

    public void ShowBar(){
        sportc.setName("現在のゾンビ討伐数" + subjugation.getZombie());
        sportc.zonmbie.setProgress(subjugation.getMax() / subjugation.getZombie());
        ShowAllPlayer();
    }
}
