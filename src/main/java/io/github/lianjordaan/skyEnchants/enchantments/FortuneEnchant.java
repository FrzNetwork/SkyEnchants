package io.github.lianjordaan.skyEnchants.enchantments;

import io.github.lianjordaan.skyEnchants.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class FortuneEnchant extends SimpleEnchantment {

    private final Random random = new Random();

    public FortuneEnchant(JavaPlugin plugin) {
        super(plugin, "Fortune");
    }

    public List<ItemStack> applyFortune(List<ItemStack> drops, int level) {
        for (ItemStack drop : drops) {
            int extra = random.nextInt(level) + 1; // Simulate Fortune effect
            drop.setAmount(drop.getAmount() + extra);
        }
        return drops;
    }
}