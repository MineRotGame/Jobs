package org.ibear.jobs;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

public final class Jobs extends JavaPlugin {

    private MongoClient mongoClient;
    private MongoCollection<Document> playerCollection;

    @Override
    public void onEnable() {
        CommandMap commandMap = Bukkit.getCommandMap();
        commandMap.register("jobs", new JobsCommand(this));

        connectToDatabase();

        getServer().getPluginManager().registerEvents(new JobsListener(this), this);
    }

    private void connectToDatabase() {
        String uri = "mongodb+srv://ibear:<db_password>@ibear.y5xgt.mongodb.net/";
        mongoClient = MongoClients.create(uri);

        MongoDatabase database = mongoClient.getDatabase("minecraft_jobs");
        playerCollection = database.getCollection("player");

        getLogger().info("Connected to MongoDB!");
    }

    public MongoCollection<Document> getPlayerCollection(){
        return playerCollection;
    }

    @Override
    public void onDisable() {
    }
}
