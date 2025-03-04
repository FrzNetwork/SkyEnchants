package io.github.lianjordaan.skyEnchants;

import io.github.lianjordaan.skyEnchants.enchantments.*;
import io.github.lianjordaan.skyEnchants.events.BlockBreakListener;
import io.github.lianjordaan.skyEnchants.events.DamageListener;
import io.github.lianjordaan.skyMineCore.SkyMineCore;
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
    private SkyMineCore skyMineCore; // Store reference

    @Override
    public void onEnable() {
        // load and store reference to SkyMineCore

        // Get the SkyMineCore instance only once
        this.skyMineCore = (SkyMineCore) getServer().getPluginManager().getPlugin("SkyMineCore");

        if (skyMineCore == null) {
            getLogger().warning("SkyMineCore plugin not found! Some features may not work.");
        }


        // load default config
        saveDefaultConfig();

        // Register enchantments
        EnchantmentRegistry.register(new FortuneEnchant(this));
        EnchantmentRegistry.register(new ResourceConverterEnchant(this));
        EnchantmentRegistry.register(new AutoPickupEnchant(this));
        EnchantmentRegistry.register(new ResourceEnchanterEnchant(this));
        EnchantmentRegistry.register(new ProtectionEnchant(this));
        EnchantmentRegistry.register(new DamageAbsorptionEnchant(this));

        // Register events
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this, skyMineCore), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this, skyMineCore), this);

        // Register commands
        getCommand("skyenchants").setExecutor(new SkyEnchantsCommand(this));

        // Register tab completers
        getCommand("skyenchants").setTabCompleter(new SkyEnchantsTabCompletion(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public SkyMineCore getSkyMineCore() {
        return skyMineCore;
    }

}
