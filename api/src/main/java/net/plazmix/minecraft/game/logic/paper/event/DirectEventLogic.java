package net.plazmix.minecraft.game.logic.paper.event;

import lombok.Data;
import net.plazmix.PlazmixAPI;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
public class DirectEventLogic<S extends GameSession, E extends Event> {

    private final Class<E> eventClass;
    private final EventPriority priority;
    private final boolean ignoreCancelled;
    private final BiConsumer<S, E> handler;

    public void register(S session) {
        PlazmixAPI.getMinecraft().getPlatform(PaperSpigotPlatform.class)
                .ifPresent(platform -> platform.addEventHandler(eventClass, priority, event -> handler.accept(session, event), ignoreCancelled));
    }

    public void unregister() {
        PlazmixAPI.getMinecraft().getPlatform(PaperSpigotPlatform.class).ifPresent(platform -> platform.removeEventHandler(eventClass));
    }
}
