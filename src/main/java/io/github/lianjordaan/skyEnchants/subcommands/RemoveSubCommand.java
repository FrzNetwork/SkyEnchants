package io.github.lianjordaan.skyEnchants.subcommands;

import io.github.lianjordaan.skyEnchants.EnchantmentRegistry;
import io.github.lianjordaan.skyEnchants.SkyEnchants;
import io.github.lianjordaan.skyEnchants.enchantments.SimpleEnchantment;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RemoveSubCommand {
    private final SkyEnchants plugin;

    public RemoveSubCommand(SkyEnchants plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        //check if sender is console, then send error message
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player to use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 2) {
            player.sendMessage("§cUsage: /skyenchants remove <enchantment>");
            return true;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage("§cYou must be holding an item to use this command.");
            return true;
        }
        String enchantmentToAdd = args[1];
        SimpleEnchantment enchantment = EnchantmentRegistry.get(enchantmentToAdd);
        if (enchantment == null) {
            sender.sendMessage("§cEnchantment not found: " + enchantmentToAdd);
            return true;
        }
        enchantment.removeEnchantment(item);
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Removed enchantment <yellow>" + enchantmentToAdd + " <green>from item"));
        return true;
    }
}
