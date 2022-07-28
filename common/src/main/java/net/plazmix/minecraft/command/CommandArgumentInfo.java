package net.plazmix.minecraft.command;

import lombok.Getter;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
public class CommandArgumentInfo<S extends CommandSender> extends CommandBase implements ArgumentInfo {

    private final CommandElement parent;

    public CommandArgumentInfo(ArgumentInfo info) {
        this(info, info.getParent());
    }

    public CommandArgumentInfo(ArgumentInfo info, CommandElement parent) {
        this(info.getName(), info.getDescription(), info.getPermission(), info.getPermissionMessageApplier(),
                info.getWrongSenderMessageApplier(), info.getExecutor(), info.getTabCompleter(), info.getAliases(),
                parent);
    }

    public CommandArgumentInfo(String name, String description, String permission,
                               Function<CommandSender, String> permissionMessageApplier,
                               Function<CommandSender, String> wrongSenderMessageApplier, BiConsumer<CommandSender, String[]> executor,
                               BiFunction<CommandSender, String[], List<String>> tabCompleter, String[] aliases,
                               CommandElement parent) {
        super(name, description, permission, permissionMessageApplier, wrongSenderMessageApplier, executor,
                tabCompleter, aliases);
        this.parent = parent;
    }
}