package kisk1109.jp.killcount_demo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class getUUID implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        UUID getid = player.getUniqueId();
        String getuuid = player.getUniqueId().toString();


                sender.sendMessage(getuuid);
                return true;
    }

}
