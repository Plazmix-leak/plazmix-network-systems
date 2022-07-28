package net.plazmix.minecraft.util.paper.time.ticker;

import com.google.common.collect.Sets;
import net.plazmix.util.time.ticker.ExtendedTimeTicker;
import net.plazmix.util.time.ticker.TimeTicker;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.BiConsumer;

public class PlayerCollectionTimeTicker extends ExtendedTimeTicker {

    private final BiConsumer<PlayerCollectionTimeTicker, Player> consumer;
    private final Collection<Player> players = Sets.newHashSet();

    public PlayerCollectionTimeTicker(TimeTicker timeTicker, BiConsumer<PlayerCollectionTimeTicker, Player> consumer) {
        super(timeTicker);
        this.consumer = consumer;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public void onTick() {
        players.forEach(player -> consumer.accept(this, player));
    }
}
