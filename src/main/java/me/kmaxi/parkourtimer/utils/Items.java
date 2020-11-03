package me.kmaxi.parkourtimer.utils;

import me.kmaxi.parkourtimer.ParkourTimerMain;
import me.kmaxi.parkourtimer.managers.ParkourManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.atomic.AtomicInteger;

public class Items {
    public final ParkourTimerMain plugin;

    public Items(ParkourTimerMain plugin) {
        this.plugin = plugin;
    }

    public static void addParkourItems(Player player, ParkourTimerMain plugin) {
        player.getInventory().clear();
        player.getInventory().setItem(8, Items.getItem("leaveParkour", plugin));
        player.getInventory().setItem(0, getItem("setCheckpoint", plugin));
        player.getInventory().setItem(7, Items.getItem("hidePlayers", plugin));
    }

    public static void addLobbyItems(Player player, ParkourTimerMain plugin) {
        player.getInventory().clear();
        AtomicInteger counter = new AtomicInteger();
        if (plugin.messegesConfig.getMessagesConfig().getBoolean("useLobbyItems")) {
            for (ParkourManager parkourManager : plugin.parkours) {
                if (!plugin.messegesConfig.getMessagesConfig().contains("blocks." + parkourManager.getName() + ".material")) {
                    plugin.messegesConfig.getMessagesConfig().set("blocks." + parkourManager.getName() + ".material", Utils.getRandomMaterial().toString());
                    plugin.messegesConfig.getMessagesConfig().set("blocks." + parkourManager.getName() + ".text", "&6Teleport to &d" + parkourManager.getName() + "&6 parkour");
                    plugin.messegesConfig.saveConifg();
                }
                player.getInventory().setItem(counter.get(), getItem(parkourManager.getName(), plugin));
                counter.getAndIncrement();
            }
        }

    }

    public static ItemStack getItem(String path, ParkourTimerMain plugin) {
        String pathToMaterial = "blocks." + path + ".material";
        String pathToText = "blocks." + path + ".text";
        ItemStack itemStack = new ItemStack(Material.valueOf(plugin.messegesConfig.getMessagesConfig().getString(pathToMaterial)));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Utils.color(plugin.messegesConfig.getMessagesConfig().getString(pathToText)));
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
