package net.plazmix.minecraft.game;

import com.google.common.collect.Maps;
import lombok.Data;
import net.plazmix.minecraft.game.mode.WorldGame;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Data
public abstract class AbstractWorldGame<T extends GameSession> implements WorldGame<T> {

    private final String name;
    private final Map<String, T> sessionMap = Maps.newConcurrentMap();

    @Override
    public Optional<T> getGameSession(String id) {
        return Optional.ofNullable(sessionMap.get(id));
    }

    @Override
    public Collection<T> getGames() {
        return sessionMap.values();
    }
}
