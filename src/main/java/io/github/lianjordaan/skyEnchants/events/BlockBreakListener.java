package io.github.lianjordaan.skyEnchants.events;

import io.github.lianjordaan.skyEnchants.EnchantmentRegistry;
import io.github.lianjordaan.skyEnchants.SkyEnchants;
import io.github.lianjordaan.skyEnchants.enchantments.AutoPickupEnchant;
import io.github.lianjordaan.skyEnchants.enchantments.FortuneEnchant;
import io.github.lianjordaan.skyEnchants.enchantments.ResourceConverterEnchant;
import io.github.lianjordaan.skyEnchants.enchantments.ResourceEnchanterEnchant;
import io.github.lianjordaan.skyEnchants.utils.ItemUtils;
import io.github.lianjordaan.skyMineCore.SkyMineCore;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakListener implements Listener {
    private final JavaPlugin plugin;
    private final SkyMineCore skyMineCore;

    public BlockBreakListener(JavaPlugin plugin, SkyMineCore skyMineCore) {
        this.plugin = plugin;
        this.skyMineCore = skyMineCore;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        FortuneEnchant fortune = (FortuneEnchant) EnchantmentRegistry.get("Fortune");
        ResourceConverterEnchant resourceConverter = (ResourceConverterEnchant) EnchantmentRegistry.get("ResourceConverter");
        ResourceEnchanterEnchant resourceEnchanter = (ResourceEnchanterEnchant) EnchantmentRegistry.get("ResourceEnchanter");
        AutoPickupEnchant autoPickup = (AutoPickupEnchant) EnchantmentRegistry.get("AutoPickup");

        List<ItemStack> drops = event.getBlock().getDrops(tool).stream().toList();

        // Apply Resource Converter
        if (resourceConverter != null && resourceConverter.hasEnchantment(tool)) {
            ItemStack convertedItem = resourceConverter.convertBlockToResource(event.getBlock().getType());
            if (convertedItem != null) {
                drops = new ArrayList<>();
                drops.add(convertedItem);
            }
        }

        // Normalize ItemStacks
        drops = ItemUtils.normalizeItemStacks(drops);

        // Apply Fortune
        if (fortune != null && fortune.hasEnchantment(tool)) {
            int level = fortune.getLevel(tool);
            drops = fortune.applyFortune(drops, level);
        }

        // Normalize ItemStacks
        drops = ItemUtils.normalizeItemStacks(drops);

        // Apply Resource Enchanter (chance per item)
        if (resourceEnchanter != null && resourceEnchanter.hasEnchantment(tool)) {
            int level = resourceEnchanter.getLevel(tool);
            ItemStack enchantedDrop = resourceEnchanter.convertItemToEnchanted(drops.getFirst(), level);
            if (enchantedDrop != null) {
                drops.add(enchantedDrop);
            }
        }

        // Normalize ItemStacks
        drops = ItemUtils.normalizeItemStacks(drops);

        // Stop the event from dropping default items
        event.setDropItems(false);

        // Handle AutoPickup
        if (autoPickup != null && autoPickup.hasEnchantment(tool)) {
            boolean deleteExperienceOrbs = plugin.getConfig().getBoolean("auto-pickup.delete-experience-orbs", true);
            if (deleteExperienceOrbs) {
                event.setExpToDrop(0);
            }
            for (ItemStack drop : drops) {
                player.getInventory().addItem(drop);
            }
        } else {
            for (ItemStack drop : drops) {
                player.getWorld().dropItemNaturally(event.getBlock().getLocation().toCenterLocation(), drop);
            }
        }

        if (!event.isCancelled()) {
            SkyMineCore skyMineCore = this.skyMineCore;
            if (skyMineCore != null) {
                skyMineCore.getUserEvents().breakBlock(event.getPlayer().getUniqueId());
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<red>SkyMineCore is null during block break event! Please contact the server administrator immediately."));
                plugin.getLogger().warning("SkyMineCore is null during block break event! Check plugin dependencies.");
            }
        }
    }
}
