package io.github.lianjordaan.skyEnchants.enchantments;

import io.github.lianjordaan.skyEnchants.EnchantmentRegistry;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SimpleEnchantment {
    protected final String name;
    protected final JavaPlugin plugin;
    protected final NamespacedKey key;

    public SimpleEnchantment(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.key = new NamespacedKey(plugin, name.toLowerCase());
    }

    public String getName() {
        return name;
    }

    public void applyEnchantment(ItemStack item, int level) {
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, level);
        item.setItemMeta(meta);
    }

    public void removeEnchantment(ItemStack item) {
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(key);
        item.setItemMeta(meta);
    }

    public int getLevel(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return 0;
        return item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(key, PersistentDataType.INTEGER, 0);
    }

    public boolean hasEnchantment(ItemStack item) {
        return getLevel(item) > 0;
    }
}
