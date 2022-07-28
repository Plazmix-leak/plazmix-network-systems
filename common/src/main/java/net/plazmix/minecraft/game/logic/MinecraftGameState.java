package net.plazmix.minecraft.game.logic;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.plazmix.util.time.ticker.TimeTicker;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@NonNull))
public class MinecraftGameState implements GameState {

    private final String name;
    private final GameLogic logic;
    private final Map<UUID, ? super TimeTicker> timeTickers;

    @Override
    public <T extends TimeTicker> T addTimeTicker(T timeTicker) {
        return null;
    }

    @Override
    public <T extends TimeTicker> T getTimeTicker(UUID uuid, Class<T> tickerClass) {
        return null;
    }

    @Override
    public TimeTicker getTimeTicker(UUID uuid) {
        return null;
    }

    @Override
    public <T extends TimeTicker> T removeTimeTicker(T timeTicker) {
        return null;
    }

    @Override
    public TimeTicker removeTimeTickerById(UUID uuid) {
        return null;
    }
}
