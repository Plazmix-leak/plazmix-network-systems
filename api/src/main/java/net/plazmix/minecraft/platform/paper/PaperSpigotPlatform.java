package net.plazmix.minecraft.platform.paper;

import net.plazmix.minecraft.command.CommandArgumentBuilder;
import net.plazmix.minecraft.command.CommandBuilder;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.builder.paper.PaperGameLogicBuilder;
import net.plazmix.minecraft.platform.Platform;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.GlobalViewInventoryBuilder;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.PersonalViewInventoryBuilder;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface PaperSpigotPlatform extends Platform {

    Plugin getPlugin();

    PaperSpigotClassMapper getClassMapper();

    <T extends GameSession> PaperGameLogicBuilder<GameSession> getGameLogicBuilder();

    <T extends GameSession> PaperGameLogicBuilder<T> getGameLogicBuilder(Class<T> gameSessionClass);

    GlobalViewInventoryBuilder<?> getGlobalViewInventoryBuilder();

    GlobalViewInventoryBuilder<?> getGlobalViewInventoryBuilder(Plugin plugin);

    PersonalViewInventoryBuilder<?> getPersonalViewInventoryBuilder();

    PersonalViewInventoryBuilder<?> getPersonalViewInventoryBuilder(Plugin plugin);

    CommandBuilder newCommand(String name);

    CommandBuilder newCommand(String holder, String name);

    CommandBuilder newCommand(Plugin plugin, String name);

    CommandBuilder newCommand(Plugin plugin, String holder, String name);

    CommandArgumentBuilder newCommandArgument(String name);

    <T extends Event> PaperSpigotPlatform addEventHandler(Class<T> eventClass, Consumer<T> handler);

    <T extends Event> PaperSpigotPlatform addEventHandler(Class<T> eventClass, EventPriority priority, Consumer<T> handler);

    <T extends Event> PaperSpigotPlatform addEventHandler(Class<T> eventClass, EventPriority priority, Consumer<T> handler, boolean ignoreCancelled);

    <T extends Event> PaperSpigotPlatform removeEventHandler(Class<T> eventClass);
}
