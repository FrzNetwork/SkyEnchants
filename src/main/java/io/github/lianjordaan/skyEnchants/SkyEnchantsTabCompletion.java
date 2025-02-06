package io.github.lianjordaan.skyEnchants;

import io.github.lianjordaan.itemVault.ItemManager;
import io.github.lianjordaan.itemVault.ItemVault;
import io.github.lianjordaan.skyEnchants.enchantments.SimpleEnchantment;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyEnchantsTabCompletion implements TabCompleter {
    private final SkyEnchants plugin;

    public SkyEnchantsTabCompletion(SkyEnchants plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.addAll(List.of("add", "remove", "list", "reload"));
        } else if (args.length == 2) {
            switch (args[0]) {
                case "add":
                    EnchantmentRegistry.getAll().forEach(enchantment -> completions.add(enchantment.getName()));
                    break;
                case "remove":
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item.getType() != Material.AIR) {
                            List<SimpleEnchantment> enchantments = EnchantmentRegistry.getAll();
                            for (SimpleEnchantment enchantment : enchantments) {
                                if (enchantment.hasEnchantment(item)) {
                                    completions.add(enchantment.getName());
                                }
                            }
                        }
                    }
                    break;
            }
        }

        // Return matching completions (filter by user input)
        return filterCompletions(completions, args);
    }


    // Filters completions based on user input
    private List<String> filterCompletions(List<String> completions, String[] args) {
        String currentInput = args[args.length - 1].toLowerCase();
        List<String> filtered = new ArrayList<>();
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(currentInput)) {
                filtered.add(completion);
            }
        }
        return filtered;
    }
}