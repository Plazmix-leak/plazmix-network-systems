package net.plazmix.minecraft.command;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MinecraftCommandArgumentBuilder implements CommandArgumentBuilder {

    private final CommandBaseBuilder baseBuilder;
    private final Collection<CommandArgument> childs = Lists.newArrayList();
    private final CommandElement parent;

    public MinecraftCommandArgumentBuilder(String name, CommandElement parent) {
        this.baseBuilder = new CommandBaseBuilder(name);
        this.parent = parent;
    }

    @Override
    public CommandArgument build() {
        return new MinecraftCommandArgument(baseBuilder.name, baseBuilder.description, baseBuilder.permission,
                baseBuilder.noPermissionMessage, baseBuilder.wrongSenderMessage, baseBuilder.executor,
                baseBuilder.tabCompleter, baseBuilder.aliases.toArray(new String[baseBuilder.aliases.size()]), parent,
                childs);
    }

    @Override
    public CommandArgumentBuilder registerAlias(String alias) {
        baseBuilder.registerAlias(alias);
        return this;
    }

    @Override
    public CommandArgumentBuilder withDescription(String description) {
        baseBuilder.withDescription(description);
        return this;
    }

    @Override
    public CommandArgumentBuilder withPermission(String permission,
                                                 Function<CommandSender, String> messageFunction) {
        baseBuilder.withPermission(permission, messageFunction);
        return this;
    }

    @Override
    public CommandArgumentBuilder setWrongSenderMessage(Function<CommandSender, String> messageFunction) {
        baseBuilder.setWrongSenderMessage(messageFunction);
        return this;
    }

    @Override
    public CommandArgumentBuilder withExecutor(BiConsumer<CommandSender, String[]> biConsumer) {
        baseBuilder.withExecutor(biConsumer);
        return this;
    }

    @Override
    public CommandArgumentBuilder withTabCompleter(BiFunction<CommandSender, String[], List<String>> biFunction) {
        baseBuilder.withTabCompleter(biFunction);
        return this;
    }

    @Override
    public CommandArgumentBuilder addChild(CommandArgumentBuilder child) {
        childs.add(child.build());
        return this;
    }
}