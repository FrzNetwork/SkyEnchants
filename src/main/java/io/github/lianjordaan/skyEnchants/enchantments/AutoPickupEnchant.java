package io.github.lianjordaan.skyEnchants.enchantments;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class AutoPickupEnchant extends SimpleEnchantment {

    private final Random random = new Random();

    public AutoPickupEnchant(JavaPlugin plugin) {
        super(plugin, "AutoPickup");
    }
}