package kisk1109.jp.killcount_demo;

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
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin implements Listener {

    /**
     * モンスターの討伐数を格納するオブジェクト
     */
    public SubjugationInfo subjugation = null;


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

        //
        getCommand("totalkill").setExecutor(this);

    }
    /**
     * プラグインが無効化されるとき呼び出される
     */
    public void onDisable() {
        // データを設定ファイルに保存する
        saveConfig();
    }

    // UUID getid = killer.getUniqueId();
    //    // String getuuid = killer.getUniqueId().toString();

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
        //int killzonmbie = subjugation.getZombie() * 10;
        LivingEntity entity = e.getEntity();
        Player player = entity.getKiller();

        // null=プレイヤーが殺したのではないなら
        if (player == null) {
            return; // 何もしない
        }else if (e.getEntityType() == EntityType.ZOMBIE) {
            //キル数加算
            subjugation.setZombie( subjugation.getZombie() + 1);
        }else if (e.getEntityType() == EntityType.SKELETON) {
            //キル数加算
            subjugation.setSkeleton( subjugation.getSkeleton() + 1);
        }else if (e.getEntityType() == EntityType.CREEPER) {
            //キル数加算
            subjugation.setCreeper( subjugation.getCreeper() + 1);
        }else if(e.getEntityType() == EntityType.BAT){
            subjugation.setBat( subjugation.getBat() + 1);
        }
        // データを保存
        getConfig().set("subjugation",  subjugation);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        int killzonmbie = subjugation.getZombie();
        int killskeleton = subjugation.getSkeleton();
        int killcreeper = subjugation.getCreeper();
        int killbat = subjugation.getBat();

        sender.sendMessage("========================");
        sender.sendMessage("ゾンビ討伐数    :"+killzonmbie);
        sender.sendMessage("スケルトン討伐数:"+killskeleton);
        sender.sendMessage("クリーパー討伐数:"+killcreeper);
        sender.sendMessage("コウモリ討伐数  :"+killbat);
        sender.sendMessage("========================");
        return true;
    }
}
