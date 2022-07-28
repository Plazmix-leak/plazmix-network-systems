package net.plazmix.minecraft.util.paper;

import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;
import java.util.function.Supplier;

@UtilityClass
public class Schedulers {

    public static void execute(Plugin plugin, Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

    public static void executeAsync(Plugin plugin, Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static <T> void handleSupplier(Plugin plugin, Supplier<T> supplier, Consumer<T> consumer) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            T result = supplier.get();
            execute(plugin, () -> consumer.accept(result));
        });
    }

    public static <T> void handleSupplierAsync(Plugin plugin, Supplier<T> supplier, Consumer<T> consumer) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> consumer.accept(supplier.get()));
    }
}
