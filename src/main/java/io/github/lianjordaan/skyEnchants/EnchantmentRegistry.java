package io.github.lianjordaan.skyEnchants;

import io.github.lianjordaan.skyEnchants.enchantments.SimpleEnchantment;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EnchantmentRegistry {
    private static final Map<String, SimpleEnchantment> ENCHANTMENTS = new HashMap<>();

    public static void register(SimpleEnchantment enchantment) {
        ENCHANTMENTS.put(enchantment.getName(), enchantment);
    }

    public static SimpleEnchantment get(String name) {
        return ENCHANTMENTS.get(name);
    }

    public static List<SimpleEnchantment> getAll() {
        return new ArrayList<>(ENCHANTMENTS.values());
    }
}
