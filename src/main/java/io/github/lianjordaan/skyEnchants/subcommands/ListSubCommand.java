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

import java.util.List;

public class ListSubCommand {
    private final SkyEnchants plugin;

    public ListSubCommand(SkyEnchants plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("§cUsage: /skyenchants list");
            return true;
        }
        List<SimpleEnchantment> enchantmentList = EnchantmentRegistry.getAll();
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>List of available Enchantments:"));
        for (SimpleEnchantment enchantment : enchantmentList) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>- <gold><u>" + enchantment.getName()));
        }
        return true;
    }
}
