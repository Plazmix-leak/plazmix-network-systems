package net.plazmix.minecraft.game.logic.paper.event;


import lombok.Data;
import net.plazmix.minecraft.game.GameSession;
import org.bukkit.event.Event;

import java.util.function.BiFunction;

@Data(staticConstructor = "create")
public class EventLogicMapper<T extends GameSession, U extends Event> {

    private final Class<U> eventClass;
    private final BiFunction<T, U, GameEvent> mapper;
}
