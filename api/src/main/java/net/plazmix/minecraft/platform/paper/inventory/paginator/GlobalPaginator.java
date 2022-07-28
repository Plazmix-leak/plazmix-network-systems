package net.plazmix.minecraft.platform.paper.inventory.paginator;

import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import org.bukkit.entity.Player;

import java.util.List;

public interface GlobalPaginator extends Paginator {

    List<Icon> getContents();

    List<Icon> getPageContents(int page);

    void addContents(List<Icon> icons);

    void removeContents(List<Icon> icons);

    void clearContents();

    default List<Icon> getPlayerPageContents(Player player) {
        return getPageContents(getPage(player));
    }

    default int getPages() {
        double items = (double) getContents().size(), size = (double) getSchemeSlots();
        return (int) Math.ceil(items / size);
    }
}