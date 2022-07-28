package net.plazmix.command;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.plazmix.minecraft.command.*;
import net.plazmix.minecraft.platform.paper.command.PaperPluginCommand;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaperCommand<S extends org.bukkit.command.CommandSender> extends CommandBase implements Command, CommandExecutor, TabCompleter {

    private static final Function<String, List<String>> DEFAULT_COMPLETER = s -> Bukkit.getOnlinePlayers().stream()
            .map(Player::getName).filter(name -> name.startsWith(s)).collect(Collectors.toList());

    private final PaperPluginCommand command;
    private final String holder;
    private final Multimap<Integer, ArgumentInfo> argumentMap = HashMultimap.create();

    protected PaperCommand(Plugin plugin, String holder,
                           Collection<CommandArgument> argumentMap, String name, String description,
                           String permission, Function<CommandSender, String> permissionMessageFunction,
                           Function<CommandSender, String> wrongSenderInstanceMessage, BiConsumer<CommandSender, String[]> executor,
                           BiFunction<CommandSender, String[], List<String>> tabCompleter, String[] aliases) {
        super(name, description, permission, permissionMessageFunction, wrongSenderInstanceMessage, executor,
                tabCompleter, aliases);
        this.holder = holder;
        this.load(argumentMap, this, 0);
        this.command = new PaperPluginCommand(plugin, this, this, getCommandHolder(), getName(), getDescription(), "", Lists.newArrayList(aliases));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] params) {
        ArgumentInfo argument = null;
        int argIndex = 0;

        for (int paramIndex = params.length - 1; paramIndex >= 0; paramIndex--) {
            final int localIndex = paramIndex;
            Collection<ArgumentInfo> args = argumentMap.get(paramIndex);

            Optional<ArgumentInfo> argumentOptional = args.stream()
                    .filter(arg -> arg.getName().equalsIgnoreCase(params[localIndex])
                            || ArrayUtils.contains(arg.getAliases(), params[localIndex]))
                    .filter(arg -> (localIndex < 1
                            || arg.getParent().getName().equalsIgnoreCase(params[localIndex - 1])
                            || ArrayUtils.contains(arg.getParent().getAliases(), params[localIndex - 1])))
                    .findFirst();

            if (argumentOptional.isPresent()) {
                argument = argumentOptional.get();
                argIndex = localIndex;
                break;
            }
        }

        CommandSender commandSender = PaperCommandSender.of(sender);
        if (argument == null) {
            if (getPermission() != null && !sender.hasPermission(getPermission())) {
                sender.sendMessage(getPermissionMessageApplier().apply(commandSender));
                return true;
            }
            getExecutor().accept(commandSender, params);
        } else {
            if (argument.getPermission() != null && !sender.hasPermission(argument.getPermission())) {
                sender.sendMessage(argument.getPermissionMessageApplier().apply(commandSender));
                return true;
            }
            argument.getExecutor().accept(commandSender, Arrays.copyOfRange(params, argIndex + 1, params.length));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label,
                                      String[] params) {
        if (!getName().equalsIgnoreCase(command.getName()) && !ArrayUtils.contains(getAliases(), command.getName()))
            return null;
        else {

            if (params.length <= 0)
                return null;

            final int paramIndex = params.length - 1;
            Collection<ArgumentInfo> args = argumentMap.get(paramIndex).stream()
                    .filter(arg -> arg.getPermission() == null || sender.hasPermission(arg.getPermission()))
                    .filter(arg -> paramIndex < 1 || arg.getParent().getName().equalsIgnoreCase(params[paramIndex - 1])
                            || ArrayUtils.contains(arg.getParent().getAliases(), params[paramIndex - 1]))
                    .collect(Collectors.toList());

            List<String> defaultCompleter = DEFAULT_COMPLETER.apply(params[paramIndex]);

            if (params.length < 2) {
                List<String> result = args.stream().map(CommandElement::getName).filter(arg -> arg.toLowerCase().startsWith(params[paramIndex].toLowerCase())).collect(Collectors.toList());
                if (getTabCompleter() != null) {
                    List<String> custom = getTabCompleter().apply(PaperCommandSender.of(sender), params);
                    if (custom != null)
                        result.addAll(custom);
                }
                if (result.isEmpty())
                    return defaultCompleter;
                return result;
            } else {
                Optional<ArgumentInfo> optionalParent = argumentMap.get(paramIndex - 1).stream()
                        .filter(arg -> arg.getPermission() == null || sender.hasPermission(arg.getPermission()))
                        .filter(arg -> arg.getName().equalsIgnoreCase(params[paramIndex - 1])
                                || ArrayUtils.contains(arg.getAliases(), params[paramIndex - 1]))
                        .findFirst();

                if (!optionalParent.isPresent())
                    return defaultCompleter;

                ArgumentInfo parent = optionalParent.get();
                if (parent.getTabCompleter() != null)
                    return parent.getTabCompleter()
                            .apply(PaperCommandSender.of(sender), Arrays.copyOfRange(params, paramIndex, params.length)).stream()
                            .filter(str -> str.toLowerCase().startsWith(params[paramIndex].toLowerCase()))
                            .collect(Collectors.toList());
                return args.stream().map(CommandElement::getName).collect(Collectors.toList());
            }
        }
    }

    @Override
    public String getCommandHolder() {
        return holder;
    }

    @Override
    public void register() {
        command.register();
    }

    private void load(Collection<CommandArgument> arguments,
                      CommandElement parent, int index) {
        for (CommandArgument argument : arguments) {
            @SuppressWarnings({"rawtypes", "unchecked"})
            CommandArgumentInfo<CommandSender> argumentInfo = new CommandArgumentInfo<>(argument, parent);
            argumentMap.put(index, argumentInfo);

            if (argument.getChilds().isEmpty())
                continue;

            load(argument.getChilds(), argumentInfo, index + 1);
        }
    }
}
