package kisk1109.jp.killcount_demo;


import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class guiCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if ((sender instanceof Player))
        {
            Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("mgui"))
            {
// Here we create our named help "inventory"
                Inventory help = Bukkit.createInventory(p, 9, "menuGUI");
                //Here you define our item







                //Here opens the inventory
                p.openInventory(help);

            }
        }
        return false;
    }
}

