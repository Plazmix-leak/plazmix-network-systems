package net.plazmix.inventory.paginator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.paginator.PersonalPaginator;
import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import org.bukkit.entity.Player;

import java.util.List;

public class SimplePersonalPaginator extends AbstractPaginator implements PersonalPaginator {

    private final ListMultimap<Player, Icon> playerIcons = ArrayListMultimap.create();

    public SimplePersonalPaginator(Integer[] fillScheme, PersonalViewInventory source) {
        super(fillScheme, source);
    }

    @Override
    public boolean nextPage(Player player) {
        int page = getPage(player);
        if (page < getPages(player)) {
            setPage(player, page + 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean previousPage(Player player) {
        int page = getPage(player);
        if (page > 1) {
            setPage(player, page - 1);
            return true;
        }
        return false;
    }

    @Override
    public void lastPage(Player player) {
        setPage(player, getPages(player));
    }

    @Override
    public void refresh(Player player) {
        List<Icon> contents = getPageContents(player, getPage(player));
        int index = 0;
        for (int slot : getFillScheme()) {
            getSource().clearSlot(player, slot);
            if (index < contents.size()) {
                getSource().setGlobalIcon(slot, contents.get(index));
                index++;
            }
        }
        getSource().refresh(player);
    }

    @Override
    public List<Icon> getContents(Player player) {
        return ImmutableList.copyOf(playerIcons.get(player));
    }

    @Override
    public List<Icon> getPageContents(Player player, int page) {
        List<Icon> result = Lists.newArrayList();
        int pages = getPages(player);
        if (page > pages)
            return result;

        List<Icon> contents = playerIcons.get(player);
        for (int index = (page - 1) * getSchemeSlots(); index < getSchemeSlots() * page && index < contents.size(); index++)
            result.add(contents.get(index));
        return result;
    }

    @Override
    public void addContents(Player player, List<Icon> icons) {
        playerIcons.putAll(player, icons);
    }

    @Override
    public void removeContents(Player player, List<Icon> icons) {
        playerIcons.get(player).removeAll(icons);
    }

    @Override
    public void clearContents(Player player) {
        playerIcons.removeAll(player);
    }
}
