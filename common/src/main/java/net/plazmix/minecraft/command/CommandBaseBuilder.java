package net.plazmix.minecraft.command;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
public class CommandBaseBuilder
        implements CommandElementBuilder<CommandBaseBuilder, CommandBase> {

    protected final String name;
    protected final Collection<String> aliases = Sets.newHashSet();
    protected String description = "", permission;
    protected BiConsumer<CommandSender, String[]> executor = (sender, params) -> {
    };
    protected BiFunction<CommandSender, String[], List<String>> tabCompleter;
    protected Function<CommandSender, String> noPermissionMessage = s -> "You don't have permission to use this command!";
    protected Function<CommandSender, String> wrongSenderMessage = s -> "Access denied!";

    public CommandBaseBuilder(String name) {
        this.name = name;
    }

    @Override
    public CommandBase build() {
        return new CommandBase(name, description, permission, noPermissionMessage, wrongSenderMessage, executor,
                tabCompleter, aliases.toArray(new String[aliases.size()]));
    }

    @Override
    public CommandBaseBuilder registerAlias(String alias) {
        aliases.add(alias);
        return this;
    }

    @Override
    public CommandBaseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public CommandBaseBuilder withPermission(String permission, Function<CommandSender, String> messageFunction) {
        this.permission = permission;
        this.noPermissionMessage = messageFunction;
        return this;
    }

    @Override
    public CommandBaseBuilder setWrongSenderMessage(Function<CommandSender, String> messageFunction) {
        this.wrongSenderMessage = messageFunction;
        return this;
    }

    @Override
    public CommandBaseBuilder withExecutor(BiConsumer<CommandSender, String[]> biConsumer) {
        this.executor = biConsumer;
        return this;
    }

    @Override
    public CommandBaseBuilder withTabCompleter(BiFunction<CommandSender, String[], List<String>> biFunction) {
        this.tabCompleter = biFunction;
        return this;
    }
}