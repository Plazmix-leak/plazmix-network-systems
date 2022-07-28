package net.plazmix.minecraft.platform.paper.inventory.paginator;

import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import org.bukkit.entity.Player;

import java.util.List;

public interface PersonalPaginator extends Paginator {

    List<Icon> getContents(Player player);

    List<Icon> getPageContents(Player player, int page);

    void addContents(Player player, List<Icon> icons);

    void removeContents(Player player, List<Icon> icons);

    void clearContents(Player player);

    default List<Icon> getPlayerPageContents(Player player) {
        return getPageContents(player, getPage(player));
    }

    default int getPages(Player player) {
        double items = (double) getContents(player).size(), size = (double) getSchemeSlots();
        return (int) Math.ceil(items / size);
    }
}