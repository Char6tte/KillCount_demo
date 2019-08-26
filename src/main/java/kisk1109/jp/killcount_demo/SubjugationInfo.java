package kisk1109.jp.killcount_demo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class SubjugationInfo implements ConfigurationSerializable {
    /**
     * ゾンビ・クリーパー・スケルトンが倒された数をカウントするオブジェクト
     */

        /** ゾンビが倒された数を保存するキー */
        static final private String ZOMBIE="zombie";

        /** クリーパーが倒された数を保存するキー */
        static final private String CREEPER="creeper";

        /** スケルトンが倒された数を保存するキー */
        static final private String SKELETON="skeleton";

        static final private String BAT="bat";

        static final private String MAX="max";

        /** ゾンビが倒された数 */
        public int zombie=0;

        /** クリーパーが倒された数 */
        public int creeper=0;

        /** スケルトンが倒された数 */
        public int skeleton=0;

        public int bat=0;

        public int max=10000;

        /** コンストラクタ */
        public SubjugationInfo(){
        }

        /** ゾンビが倒された数を設定する */
        public void setZombie(int zombie){
            this.zombie=zombie;
        }

        /** ゾンビが倒された数を取得する */
        public int getZombie(){
            return zombie;
        }

        /** クリーパーが倒された数を設定する */
        public void setCreeper(int creeper){
            this.creeper=creeper;
        }

        /** クリーパーが倒された数を取得する */
        public int getCreeper(){
            return creeper;
        }

        /** スケルトンが倒された数を設定する */
        public void setSkeleton(int skeleton){
            this.skeleton=skeleton;
        }

        /** スケルトンが倒された数を取得する */
        public int getSkeleton(){
            return skeleton;
        }

        /** スケルトンが倒された数を取得する */
        public int getMax(){
        return max;
    }

        //取得
        public void setBat(int bat) {this.bat=bat;}

        /** スケルトンが倒された数を取得する */
        public int getBat(){
        return bat;
    }

        public void setMax(int max){ this.max=max; }


        /**
         * 情報をシリアライズする
         *
         * @return シリアライズされた情報
         */
        public Map<String,Object> serialize(){

            // マップを作る
            Map<String,Object> map=new HashMap<String,Object>();

            // 情報を「キー」と「値」のペアにして書き出す
            map.put(ZOMBIE,zombie);
            map.put(CREEPER,creeper);
            map.put(SKELETON,skeleton);
            map.put(BAT,bat);
            map.put(MAX,max);

            Bukkit.broadcastMessage("test: "+map);

            // データを保存したマップを返す（このマップの中身がconfig.ymlなどに書かれる）
            return map;
        }

        /**
         * 情報をデシリアライズする
         *
         * @param map
         *            シリアライズされた情報
         * @return デシリアライズされた情報
         */
        public static SubjugationInfo deserialize(Map<String,Object> map){

            // config.ymlから読み込まれたマップが引数に渡されるので、このデータを元に情報を作成（復元）する

            //オブジェクトを作る
            SubjugationInfo subjugation=new SubjugationInfo();

            //「キー」で「値」を取り出してオブジェクトにセット
            subjugation.setZombie(Integer.parseInt(map.get(ZOMBIE).toString()));
            subjugation.setCreeper(Integer.parseInt(map.get(CREEPER).toString()));
            subjugation.setSkeleton(Integer.parseInt(map.get(SKELETON).toString()));
            subjugation.setBat(Integer.parseInt(map.get(BAT).toString()));
            subjugation.setMax(Integer.parseInt(map.get(MAX).toString()));

            //オブジェクトを返す
            return subjugation;
        }
    }