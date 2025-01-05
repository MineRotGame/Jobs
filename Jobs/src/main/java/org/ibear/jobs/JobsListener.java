package org.ibear.jobs;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JobsListener implements @NotNull Listener {

    private final Jobs plugin;

    public JobsListener(Jobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        String PageTitle = event.getView().getTitle();
        if (current == null || current.getType() == Material.AIR) return;
        MongoCollection<Document> collection = plugin.getPlayerCollection();
        switch (PageTitle){
            case "Choose Jobs":
                event.setCancelled(true);
                if (current.getType() == Material.IRON_AXE){
                    updatePlayerJobs(collection, player, "woodcutter");
                }
        }
    }

    private void updatePlayerJobs(MongoCollection<Document> collection, Player player, String job){
        Document query = new Document("name", player.name());
        Document update = new Document("$set", new Document("job", job));
        collection.updateOne(query, update, new com.mongodb.client.model.UpdateOptions().upsert(true));
    }
}
