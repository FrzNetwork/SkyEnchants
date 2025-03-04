package io.github.lianjordaan.skyEnchants.events;

import io.github.lianjordaan.skyEnchants.EnchantmentRegistry;
import io.github.lianjordaan.skyEnchants.enchantments.*;
import io.github.lianjordaan.skyEnchants.utils.ItemUtils;
import io.github.lianjordaan.skyMineCore.SkyMineCore;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DamageListener implements Listener {
    private final JavaPlugin plugin;
    private final SkyMineCore skyMineCore;

    public DamageListener(JavaPlugin plugin, SkyMineCore skyMineCore) {
        this.plugin = plugin;
        this.skyMineCore = skyMineCore;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) return; // Ensure it's a living entity (e.g., Player, Zombie)

        LivingEntity livingEntity = (LivingEntity) entity;
        double inputDamage = event.getDamage();
        if (inputDamage <= 0) return;
        double finalDamage = inputDamage;

        if (livingEntity.getEquipment() == null) return;

        PotionEffect resistanceEffect = livingEntity.getPotionEffect(PotionEffectType.RESISTANCE);


        if (resistanceEffect != null) {
            livingEntity.sendMessage(MiniMessage.miniMessage().deserialize("Resistance effect: " + resistanceEffect.getAmplifier()));
            livingEntity.sendMessage(MiniMessage.miniMessage().deserialize("Damage before resistance: " + finalDamage));
            finalDamage *= (1 - (0.2 * (resistanceEffect.getAmplifier() + 1)));
            livingEntity.sendMessage(MiniMessage.miniMessage().deserialize("Damage after resistance: " + finalDamage));
        }

        // Loop through armor items
        for (ItemStack armorPiece : Arrays.stream(livingEntity.getEquipment().getArmorContents()).toList().reversed()) {
            if (armorPiece != null && armorPiece.getType() != Material.AIR) {
                ProtectionEnchant protectionEnchant = (ProtectionEnchant) EnchantmentRegistry.get("Protection");
                DamageAbsorptionEnchant damageAbsorptionEnchant = (DamageAbsorptionEnchant) EnchantmentRegistry.get("DamageAbsorption");

                int protectionLevel = 0;
                int damageAbsorptionLevel = 0;

                if (protectionEnchant != null && protectionEnchant.hasEnchantment(armorPiece)) {
                    protectionLevel = protectionEnchant.getLevel(armorPiece);
                }
                if (damageAbsorptionEnchant != null && damageAbsorptionEnchant.hasEnchantment(armorPiece)) {
                    damageAbsorptionLevel = damageAbsorptionEnchant.getLevel(armorPiece);
                }

                // Apply damage absorption
                double reductionFactor = (1 - (0.1 * protectionLevel) / (1 + 0.1 * protectionLevel));
                finalDamage = Math.max(0, (finalDamage - damageAbsorptionLevel) * reductionFactor);
            }
        }
        event.setDamage(finalDamage);
    }
}
