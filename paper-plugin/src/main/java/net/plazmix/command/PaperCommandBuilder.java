package net.plazmix.command;

import com.google.common.collect.Lists;
import net.plazmix.minecraft.command.*;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PaperCommandBuilder implements CommandBuilder {

    private final Plugin plugin;
    private final CommandBaseBuilder baseBuilder;
    private final String holder;
    private final Collection<CommandArgument> arguments = Lists.newArrayList();

    public PaperCommandBuilder(Plugin plugin, String name) {
        this(plugin, plugin.getName().toLowerCase(), name);
    }

    public PaperCommandBuilder(Plugin plugin, String holder, String name) {
        this.plugin = plugin;
        this.holder = holder;
        this.baseBuilder = new CommandBaseBuilder(name);
    }

    @Override
    public Command build() {
        return new PaperCommand(plugin, holder, arguments, baseBuilder.getName(), baseBuilder.getDescription(),
                baseBuilder.getPermission(), baseBuilder.getNoPermissionMessage(), baseBuilder.getWrongSenderMessage(),
                baseBuilder.getExecutor(), baseBuilder.getTabCompleter(),
                baseBuilder.getAliases().toArray(new String[baseBuilder.getAliases().size()]));
    }

    @Override
    public PaperCommandBuilder registerAlias(String alias) {
        baseBuilder.registerAlias(alias);
        return this;
    }

    @Override
    public PaperCommandBuilder withDescription(String description) {
        baseBuilder.withDescription(description);
        return this;
    }

    @Override
    public PaperCommandBuilder withPermission(String permission, Function<CommandSender, String> messageFunction) {
        baseBuilder.withPermission(permission, messageFunction);
        return this;
    }

    @Override
    public PaperCommandBuilder setWrongSenderMessage(Function<CommandSender, String> messageFunction) {
        baseBuilder.setWrongSenderMessage(messageFunction);
        return this;
    }

    @Override
    public PaperCommandBuilder withExecutor(BiConsumer<CommandSender, String[]> biConsumer) {
        baseBuilder.withExecutor(biConsumer);
        return this;
    }

    @Override
    public PaperCommandBuilder withTabCompleter(BiFunction<CommandSender, String[], List<String>> biFunction) {
        baseBuilder.withTabCompleter(biFunction);
        return this;
    }

    @Override
    public CommandBuilder addArgument(CommandArgumentBuilder argumentBuilder) {
        arguments.add(argumentBuilder.build());
        return this;
    }
}