package net.plazmix.minecraft.game.logic.paper.event;

import lombok.Data;
import net.plazmix.minecraft.game.GameSession;
import org.bukkit.event.Event;

import java.util.function.Predicate;

@Data(staticConstructor = "of")
public final class EventLogic<L extends Event, R extends GameEvent> {

    private final EventLogicMapper<? extends GameSession, L> mapper;
    private final Predicate<R> predicate;
}
