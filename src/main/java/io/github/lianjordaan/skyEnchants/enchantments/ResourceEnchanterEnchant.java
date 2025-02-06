package io.github.lianjordaan.skyEnchants.enchantments;

import io.github.lianjordaan.itemVault.ItemManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ResourceEnchanterEnchant extends SimpleEnchantment {

    private final Random random = new Random();

    public ResourceEnchanterEnchant(JavaPlugin plugin) {
        super(plugin, "ResourceEnchanter");
    }

    public ItemStack convertItemToEnchanted(ItemStack item, int level) {
        if (item == null || level <= 0) return item; // No change if no enchantment level

        ItemManager itemManager = new ItemManager();

        // Calculate chance (1% per level)
        if (random.nextInt(100) >= level) {
            return item; // Failed to convert, return original item
        }

        // Load the resource conversion mapping from config
        ConfigurationSection resources = plugin.getConfig().getConfigurationSection("resource-enchanter.resources");
        if (resources == null) return item;

        // Find the matching item path
        for (String normalPath : resources.getKeys(false)) {
            ItemStack normalItem = itemManager.getItem(normalPath);
            if (normalItem != null && normalItem.isSimilar(item)) {
                // Get the enchanted version
                String enchantedPath = resources.getString(normalPath);
                if (enchantedPath == null) continue;

                ItemStack enchantedItem = itemManager.getItem(enchantedPath);
                if (enchantedItem != null) {
                    return enchantedItem; // Return the enchanted item if found
                }
            }
        }

        return item; // Return original item if no match was found
    }
}