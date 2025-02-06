package io.github.lianjordaan.skyEnchants;

import io.github.lianjordaan.skyEnchants.subcommands.AddSubCommand;
import io.github.lianjordaan.skyEnchants.subcommands.ListSubCommand;
import io.github.lianjordaan.skyEnchants.subcommands.ReloadSubCommand;
import io.github.lianjordaan.skyEnchants.subcommands.RemoveSubCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkyEnchantsCommand implements CommandExecutor {
    private final SkyEnchants plugin;
    private final AddSubCommand addSubCommand;
    private final RemoveSubCommand removeSubCommand;
    private final ListSubCommand listSubCommand;
    private final ReloadSubCommand reloadSubCommand;

    public SkyEnchantsCommand(SkyEnchants plugin) {
        this.plugin = plugin;
        this.addSubCommand = new AddSubCommand(plugin);
        this.removeSubCommand = new RemoveSubCommand(plugin);
        this.listSubCommand = new ListSubCommand(plugin);
        this.reloadSubCommand = new ReloadSubCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /skyenchants <add|remove|list|reload>");
            return true;
        }
        if (!sender.hasPermission("skyenchants.command")) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You don't have permission to use this command."));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "add":
                return new AddSubCommand(plugin).execute(sender, command, label, args);
            case "remove":
                return new RemoveSubCommand(plugin).execute(sender, command, label, args);
            case "list":
                return new ListSubCommand(plugin).execute(sender, command, label, args);
            case "reload":
                return new ReloadSubCommand(plugin).execute(sender, command, label, args);
            default:
                sender.sendMessage("§cInvalid command. Use /skyenchants <add|remove|list|reload>");
                break;
        }
        return true;
    }
}
