package net.plazmix.minecraft.command;

import net.plazmix.util.function.Builder;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface CommandElementBuilder<B extends CommandElementBuilder<B, R>, R extends CommandElement>
        extends Builder<R> {

    B registerAlias(String alias);

    B withDescription(String description);

    B withPermission(String permission, Function<CommandSender, String> messageFunction);

    B setWrongSenderMessage(Function<CommandSender, String> messageFunction);

    B withExecutor(BiConsumer<CommandSender, String[]> biConsumer);

    B withTabCompleter(BiFunction<CommandSender, String[], List<String>> biFunction);

    default B registerAliases(String... aliases) {
        B builder = null;
        for (String alias : aliases)
            builder = registerAlias(alias);
        return builder;
    }

    default B withPermission(String permission, String message) {
        return withPermission(permission, s -> message);
    }

    default B setWrongSenderMessage(String message) {
        return setWrongSenderMessage(s -> message);
    }
}