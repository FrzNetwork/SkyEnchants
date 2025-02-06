package io.github.lianjordaan.skyEnchants.subcommands;

import io.github.lianjordaan.itemVault.ItemManager;
import io.github.lianjordaan.itemVault.ItemVault;
import io.github.lianjordaan.skyEnchants.EnchantmentRegistry;
import io.github.lianjordaan.skyEnchants.SkyEnchants;
import io.github.lianjordaan.skyEnchants.enchantments.SimpleEnchantment;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddSubCommand {
    private final SkyEnchants plugin;

    public AddSubCommand(SkyEnchants plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        //check if sender is console, then send error message
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player to use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 3) {
            player.sendMessage("§cUsage: /skyenchants add <enchantment> <level>");
            return true;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage("§cYou must be holding an item to use this command.");
            return true;
        }
        String enchantmentToAdd = args[1];
        int level = Integer.parseInt(args[2]);
        SimpleEnchantment enchantment = EnchantmentRegistry.get(enchantmentToAdd);
        if (enchantment == null) {
            sender.sendMessage("§cEnchantment not found: " + enchantmentToAdd);
            return true;
        }
        enchantment.applyEnchantment(item, level);
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Enchantment <yellow>" + enchantmentToAdd + " <green>added with level <yellow>" + level));
        return true;
    }
}
