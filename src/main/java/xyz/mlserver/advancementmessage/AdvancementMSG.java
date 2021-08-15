package xyz.mlserver.advancementmessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.mlserver.advancementmessage.AdvancementAPI.FrameType;

public final class AdvancementMSG extends JavaPlugin {
    private static AdvancementMSG instance;
    public List<AdvancementAPI> apiList = new ArrayList<AdvancementAPI>();


    public void onEnable(){
        instance = this;
        getCommand("ad").setExecutor(new command());
        getCommand("ad").setTabCompleter(new tabCompleter());
    }

    public void onDisable(){

    }

    public void send(String title, String description, MaterialData material, Player ... player){
        AdvancementAPI test = new AdvancementAPI(new NamespacedKey(getInstance(), "story/" + UUID.randomUUID().toString()))
                .withFrame(FrameType.CHALLANGE)
                .withTrigger("minecraft:impossible")
                .withIcon(material)
                .withTitle(title)
                .withDescription(description)
                .withAnnouncement(false)
                .withBackground("minecraft:textures/blocks/bedrock.png");
        test.loadAdvancement();
        test.sendPlayer(player);

        Bukkit.getScheduler().runTaskLater(getInstance(), new Runnable() {
            @Override
            public void run() {
                test.delete(player);
            }
        }, 10);

    }

    public void AddAdvancment(AdvancementAPI api){
        NamespacedKey key = api.getID();
        for(AdvancementAPI adAPI : this.apiList){
            if(adAPI.getID().toString().equalsIgnoreCase(key.toString())){
                return;
            }
        }
        this.apiList.add(api);
    }

    public static AdvancementMSG getInstance() {
        return instance;
    }
}
