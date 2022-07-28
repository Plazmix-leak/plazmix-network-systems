package net.plazmix.minecraft.command;

import lombok.Getter;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
public class CommandBase implements CommandElement {

    protected final String name, description, permission;
    protected final String[] aliases;
    protected final BiConsumer<CommandSender, String[]> executor;
    protected final BiFunction<CommandSender, String[], List<String>> tabCompleter;
    protected final Function<CommandSender, String> permissionMessageApplier, wrongSenderMessageApplier;

    protected CommandBase(String name, String description, String permission,
                          Function<CommandSender, String> permissionMessageApplier,
                          Function<CommandSender, String> wrongSenderMessageApplier, BiConsumer<CommandSender, String[]> executor,
                          BiFunction<CommandSender, String[], List<String>> tabCompleter, String... aliases) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.permissionMessageApplier = permissionMessageApplier;
        this.wrongSenderMessageApplier = wrongSenderMessageApplier;
        this.executor = executor;
        this.tabCompleter = tabCompleter;
        this.aliases = aliases;
    }
}