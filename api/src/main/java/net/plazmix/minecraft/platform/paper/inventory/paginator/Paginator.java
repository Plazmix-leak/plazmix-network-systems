package net.plazmix.minecraft.platform.paper.inventory.paginator;

import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface Paginator {

    Integer[] getFillScheme();

    PersonalViewInventory getSource();

    int getPage(Player player);

    void setPage(Player player, int page);

    boolean nextPage(Player player);

    boolean previousPage(Player player);

    void firstPage(Player player);

    void lastPage(Player player);

    void refresh(Player player);

    default int getSchemeSlots() {
        return getFillScheme().length;
    }

    default Collection<Player> getCurrentViewers() {
        return getSource().getCurrentViewers();
    }

    default boolean isViewing(Player player) {
        return getSource().isViewing(player);
    }

    default void refresh() {
        for (Player player : getSource().getCurrentViewers())
            refresh(player);
    }
}