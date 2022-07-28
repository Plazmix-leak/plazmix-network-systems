package net.plazmix.inventory.paginator;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.plazmix.minecraft.platform.paper.inventory.paginator.Paginator;
import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractPaginator implements Paginator {

    @Getter
    private final Integer[] fillScheme;
    @Getter
    private final PersonalViewInventory source;
    private final Map<Player, Integer> playerPageMap = Maps.newHashMap();

    @Override
    public int getPage(Player player) {
        return playerPageMap.getOrDefault(player, 1);
    }

    @Override
    public void setPage(Player player, int page) {
        playerPageMap.put(player, page);
    }

    @Override
    public void firstPage(Player player) {
        setPage(player, 1);
    }
}