package net.plazmix;

import lombok.Data;
import net.plazmix.minecraft.command.CommandArgumentBuilder;
import net.plazmix.minecraft.command.CommandBuilder;
import net.plazmix.minecraft.command.MinecraftCommandArgumentBuilder;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.builder.paper.PaperGameLogicBuilder;
import net.plazmix.minecraft.game.logic.builder.MinecraftGameLogicBuilder;
import net.plazmix.minecraft.platform.paper.PaperSpigotClassMapper;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.GlobalViewInventoryBuilder;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.PersonalViewInventoryBuilder;
import net.plazmix.network.server.ServerVersion;
import net.plazmix.command.PaperCommandBuilder;
import net.plazmix.game.MinecraftPaperGameLogicBuilder;
import net.plazmix.inventory.view.builder.SimpleGlobalViewInventoryBuilder;
import net.plazmix.inventory.view.builder.SimplePersonalViewInventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

@Data
public class MinecraftPaperSpigotPlatform implements PaperSpigotPlatform {

    private static final Listener FIGMENT_LISTENER = new Listener() {};

    private final Plugin plugin;
    private final ServerVersion serverVersion;
    private final PaperSpigotClassMapper classMapper = new MinecraftPaperSpigotClassMapper();

    @Override
    public String getName() {
        return Bukkit.getName();
    }

    @Override
    public String getVersion() {
        return Bukkit.getBukkitVersion();
    }

    @Override
    public <T extends GameSession> PaperGameLogicBuilder<GameSession> getGameLogicBuilder() {
        return new MinecraftPaperGameLogicBuilder<>(new MinecraftGameLogicBuilder<>());
    }

    @Override
    public <T extends GameSession> PaperGameLogicBuilder<T> getGameLogicBuilder(Class<T> gameSessionClass) {
        return new MinecraftPaperGameLogicBuilder<>(new MinecraftGameLogicBuilder<>());
    }

    @Override
    public GlobalViewInventoryBuilder<?> getGlobalViewInventoryBuilder() {
        return new SimpleGlobalViewInventoryBuilder(plugin);
    }

    @Override
    public GlobalViewInventoryBuilder<?> getGlobalViewInventoryBuilder(Plugin plugin) {
        return new SimpleGlobalViewInventoryBuilder(plugin);
    }

    @Override
    public PersonalViewInventoryBuilder<?> getPersonalViewInventoryBuilder() {
        return new SimplePersonalViewInventoryBuilder(plugin);
    }

    @Override
    public PersonalViewInventoryBuilder<?> getPersonalViewInventoryBuilder(Plugin plugin) {
        return new SimplePersonalViewInventoryBuilder(plugin);
    }

    @Override
    public CommandBuilder newCommand(String holder, String name) {
        return new PaperCommandBuilder(getPlugin(), holder, name);
    }

    @Override
    public CommandBuilder newCommand(Plugin plugin, String name) {
        return new PaperCommandBuilder(plugin, name);
    }

    @Override
    public CommandBuilder newCommand(Plugin plugin, String holder, String name) {
        return new PaperCommandBuilder(plugin, holder, name);
    }

    @Override
    public CommandBuilder newCommand(String name) {
        return new PaperCommandBuilder(getPlugin(), name);
    }


    @Override
    public CommandArgumentBuilder newCommandArgument(String name) {
        return new MinecraftCommandArgumentBuilder(name, null);
    }

    @Override
    public <T extends Event> PaperSpigotPlatform addEventHandler(Class<T> eventClass, Consumer<T> handler) {
        return addEventHandler(eventClass, EventPriority.NORMAL, handler);
    }

    @Override
    public <T extends Event> PaperSpigotPlatform addEventHandler(Class<T> eventClass, EventPriority priority, Consumer<T> handler) {
        return addEventHandler(eventClass, priority, handler, false);
    }

    @Override
    public <T extends Event> PaperSpigotPlatform addEventHandler(Class<T> eventClass, EventPriority priority, Consumer<T> handler, boolean ignoreCancelled) {
        Bukkit.getPluginManager().registerEvent(eventClass, FIGMENT_LISTENER, priority, (listener, event) -> handler.accept((T) event), plugin, ignoreCancelled);
        return this;
    }

    @Override
    public <T extends Event> PaperSpigotPlatform removeEventHandler(Class<T> eventClass) {
        try {
            Method getHandlerListMethod = eventClass.getMethod("getHandlerList");
            HandlerList handlerList = (HandlerList) getHandlerListMethod.invoke(null);
            handlerList.unregister(FIGMENT_LISTENER);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            plugin.getLogger().warning("Unable to unregister event handler: " + e.getMessage());
        }
        return this;
    }
}
