package io.github.lianjordaan.skyEnchants.enchantments;

import io.github.lianjordaan.itemVault.ItemManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ResourceConverterEnchant extends SimpleEnchantment {

    private final Random random = new Random();

    public ResourceConverterEnchant(JavaPlugin plugin) {
        super(plugin, "ResourceConverter");
    }

    public ItemStack convertBlockToResource(Material minedBlock) {
        // Get the config
        FileConfiguration config = plugin.getConfig();

        // Get the block name (e.g., "COBBLESTONE")
        String blockKey = minedBlock.name();

        // Check if the block is in the config
        if (config.contains("resource-converter.resources." + blockKey)) {
            // Get the resource path and amount from the config
            String path = config.getString("resource-converter.resources." + blockKey + ".path");
            int amount = config.getInt("resource-converter.resources." + blockKey + ".amount");

            // Check if we have the correct resource path
            if (path != null && !path.isEmpty()) {
                // Use your ItemManager to convert the path to an ItemStack
                // Assuming you have an ItemManager to fetch the ItemStack based on the path
                ItemManager itemManager = new ItemManager();
                ItemStack item = itemManager.getItem(path);

                if (item != null) {
                    item.setAmount(amount); // Set the amount from the config
                    return item;
                }
            }
        }

        // Return null if no resource found or invalid data
        return null;
    }
}