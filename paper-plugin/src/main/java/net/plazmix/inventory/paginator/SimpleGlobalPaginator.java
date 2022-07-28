package net.plazmix.inventory.paginator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.paginator.GlobalPaginator;
import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import org.bukkit.entity.Player;

import java.util.List;

public class SimpleGlobalPaginator extends AbstractPaginator implements GlobalPaginator {

    private final List<Icon> contents = Lists.newArrayList();

    public SimpleGlobalPaginator(Integer[] fillScheme, PersonalViewInventory source) {
        super(fillScheme, source);
    }

    @Override
    public boolean nextPage(Player player) {
        int page = getPage(player);
        if (page < getPages()) {
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
        setPage(player, getPages());
    }

    @Override
    public void refresh(Player player) {
        List<Icon> contents = getPageContents(getPage(player));
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
    public List<Icon> getContents() {
        return ImmutableList.copyOf(contents);
    }

    @Override
    public List<Icon> getPageContents(int page) {
        List<Icon> result = Lists.newArrayList();
        int pages = getPages();
        if (page > pages)
            return result;

        for (int index = (page - 1) * getSchemeSlots(); index < getSchemeSlots() * page && index < contents.size(); index++)
            result.add(contents.get(index));
        return result;
    }

    @Override
    public void addContents(List<Icon> icons) {
        contents.addAll(icons);
    }

    @Override
    public void removeContents(List<Icon> icons) {
        contents.removeAll(icons);
    }

    @Override
    public void clearContents() {
        contents.clear();
    }
}