package net.plazmix.minecraft.platform.paper.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.List;

public class PaperPluginCommand extends Command implements PluginIdentifiableCommand {

    private final Plugin plugin;
    private final CommandExecutor executor;
    private final TabCompleter completer;
    private final String holder;
    private boolean registered;

    public PaperPluginCommand(Plugin plugin, CommandExecutor executor, TabCompleter completer, String holder, String name,
                              String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.plugin = plugin;
        this.executor = executor;
        this.completer = completer;
        this.holder = holder;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return executor.onCommand(sender, this, label, args);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return completer.onTabComplete(sender, this, alias, args);
    }

    public void register() {
        if (registered)
            return;

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register(getName(), holder, this);
            this.registered = true;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}