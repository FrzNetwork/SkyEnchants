package io.github.lianjordaan.skyEnchants;

import io.github.lianjordaan.skyEnchants.enchantments.*;
import io.github.lianjordaan.skyEnchants.events.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;

public final class SkyEnchants extends JavaPlugin {

    @Override
    public void onEnable() {
        // load default config
        saveDefaultConfig();

        // Register enchantments
        EnchantmentRegistry.register(new FortuneEnchant(this));
        EnchantmentRegistry.register(new ResourceConverterEnchant(this));
        EnchantmentRegistry.register(new AutoPickupEnchant(this));
        EnchantmentRegistry.register(new ResourceEnchanterEnchant(this));

        // Register events
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        // Register commands
        getCommand("skyenchants").setExecutor(new SkyEnchantsCommand(this));

        // Register tab completers
        getCommand("skyenchants").setTabCompleter(new SkyEnchantsTabCompletion(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
