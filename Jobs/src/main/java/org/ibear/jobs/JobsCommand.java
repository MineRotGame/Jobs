package org.ibear.jobs;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class JobsCommand extends @NotNull Command {

    private final Jobs plugin;


    Inventory inv;

    protected JobsCommand(Jobs plugin) {
        super("jobs");
        this.plugin = plugin;
    }

    private ItemStack createItem(Material material, String name, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta item_meta = item.getItemMeta();
        if (name != null){
            item_meta.setDisplayName(name);
        }
        if (lore != null && lore.length > 0){
            item_meta.setLore(Arrays.asList(lore));
        }
        item.setItemMeta(item_meta);
        return item;
    }


    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;
        Jobs_Show_Inv(player);
        return false;
    }

    public void Jobs_Show_Inv(Player player){
        inv = Bukkit.createInventory(null, 27, "Choose Jobs");

        Document document = plugin.getPlayerCollection()
                .find(new Document("name", player.getName()))
                .first();
        String currentJob = (document != null && document.containsKey("job"))
                ? document.getString("job")
                : "ไม่มี";

        inv.setItem(11, createItem(Material.IRON_AXE, ChatColor.BLUE+"Cutting Trees",ChatColor.RESET+ "Chick"));
        inv.setItem(12, createItem(Material.IRON_PICKAXE,ChatColor.BLUE+"Miner",ChatColor.RESET+"Click"));
        inv.setItem(26, createItem(Material.LIME_STAINED_GLASS_PANE,ChatColor.GREEN+"Confirm"));

        player.openInventory(inv);
    }
}
