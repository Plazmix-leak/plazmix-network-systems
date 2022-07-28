package net.plazmix.game;

import com.google.common.collect.Sets;
import lombok.Data;
import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.GameWorld;
import net.plazmix.minecraft.game.MinecraftGameCache;
import net.plazmix.minecraft.game.player.GamePlayer;

import java.util.Collection;
import java.util.Collections;

@Data
public class MinecraftGameWorld implements GameWorld {

    private final String name;
    private final GameCache cache = new MinecraftGameCache();
    private final Collection<GamePlayer> gamePlayers = Sets.newHashSet();

    @Override
    public Collection<GamePlayer> getPlayers() {
        return Collections.unmodifiableCollection(gamePlayers);
    }
}
