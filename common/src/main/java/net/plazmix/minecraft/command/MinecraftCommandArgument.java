package net.plazmix.minecraft.command;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
public class MinecraftCommandArgument extends CommandArgumentInfo
        implements CommandArgument {

    private final Collection<CommandArgument> childs;

    protected MinecraftCommandArgument(String name, String description, String permission,
                                       Function<CommandSender, String> permissionMessageApplier,
                                       Function<CommandSender, String> wrongSenderMessageApplier, BiConsumer<CommandSender, String[]> executor,
                                       BiFunction<CommandSender, String[], List<String>> tabCompleter, String[] aliases, CommandElement parent,
                                       @SuppressWarnings("rawtypes") Collection<? extends CommandArgument> childs) {
        super(name, description, permission, permissionMessageApplier, wrongSenderMessageApplier, executor,
                tabCompleter, aliases, parent);
        this.childs = ImmutableList.copyOf(childs);
    }
}