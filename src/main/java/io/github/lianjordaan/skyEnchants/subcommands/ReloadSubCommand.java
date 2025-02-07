package io.github.lianjordaan.skyEnchants.subcommands;

import io.github.lianjordaan.skyEnchants.EnchantmentRegistry;
import io.github.lianjordaan.skyEnchants.SkyEnchants;
import io.github.lianjordaan.skyEnchants.enchantments.SimpleEnchantment;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReloadSubCommand {
    private final SkyEnchants plugin;

    public ReloadSubCommand(SkyEnchants plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /skyenchants reload");
            return true;
        }
        plugin.reloadConfig();
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>SkyEnchants has been reloaded."));
        return true;
    }
}
