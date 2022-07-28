package net.plazmix.inventory.view;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.Getter;
import net.plazmix.minecraft.platform.paper.inventory.ClickData;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.paginator.Paginator;
import net.plazmix.minecraft.platform.paper.inventory.paginator.PaginatorType;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryView;
import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import net.plazmix.inventory.paginator.SimpleGlobalPaginator;
import net.plazmix.inventory.paginator.SimplePersonalPaginator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimplePersonalViewInventory extends AbstractInventory implements PersonalViewInventory {

    @Getter
    private final BiFunction<Player, PersonalViewInventory, String> titleApplier;
    private final Map<Player, InventoryView> viewers = Maps.newHashMap();
    private final Map<Integer, Function<Player, Icon>> globalIcons;
    private final Map<Player, Map<Integer, Icon>> personalIcons = Maps.newHashMap();
    private final int rows;
    @Getter
    private final Paginator paginator;

    public SimplePersonalViewInventory(Plugin plugin, InventoryType type, Consumer<InventoryData> openingAction,
                                       Consumer<InventoryData> closingAction, BiFunction<Player, PersonalViewInventory, String> titleApplier,
                                       Map<Integer, Function<Player, Icon>> globalIcons, Integer[] fillScheme, PaginatorType paginatorType) {
        super(plugin, type, openingAction, closingAction);
        this.titleApplier = titleApplier;
        this.globalIcons = globalIcons;
        this.rows = 0;

        if (paginatorType == PaginatorType.GLOBAL)
            this.paginator = new SimpleGlobalPaginator(fillScheme, this);
        else if (paginatorType == PaginatorType.PERSONAL)
            this.paginator = new SimplePersonalPaginator(fillScheme, this);
        else
            this.paginator = null;
    }

    public SimplePersonalViewInventory(Plugin plugin, int rows, Consumer<InventoryData> openingAction,
                                       Consumer<InventoryData> closingAction, BiFunction<Player, PersonalViewInventory, String> titleApplier,
                                       Map<Integer, Function<Player, Icon>> globalIcons, Integer[] fillScheme, PaginatorType paginatorType) {
        super(plugin, InventoryType.CHEST, openingAction, closingAction);
        this.titleApplier = titleApplier;
        this.globalIcons = globalIcons;
        this.rows = rows;

        if (paginatorType == PaginatorType.GLOBAL)
            this.paginator = new SimpleGlobalPaginator(fillScheme, this);
        else if (paginatorType == PaginatorType.PERSONAL)
            this.paginator = new SimplePersonalPaginator(fillScheme, this);
        else
            this.paginator = null;
    }

    @Override
    public Collection<Player> getCurrentViewers() {
        return ImmutableSet.copyOf(viewers.keySet());
    }

    @Override
    public Icon getIcon(ClickData data) {
        return isOverlayed(data.getPlayer(), data.getClickedSlot()) ? getPersonalIcon(data.getPlayer(), data.getClickedSlot()) : (hasGlobalIcon(data.getClickedSlot()) ? getGlobalIcon(data.getClickedSlot()).apply(data.getPlayer()) : null);
    }

    @Override
    public int getSlots() {
        return rows == 0 ? getType().getDefaultSize() : rows;
    }

    @Override
    public void open(Player player) {
        if (isViewing(player))
            return;

        if (!personalIcons.containsKey(player))
            personalIcons.put(player, Maps.newHashMap());

        InventoryView view = null;

        if (getType() != InventoryType.CHEST || rows == 0)
            view = new InventoryView(this, getTitleApplier().apply(player, this));
        else
            view = new InventoryView(this, getTitleApplier().apply(player, this), rows);

        viewers.put(player, view);
        refresh(player);
        player.openInventory(view.getInventory());
    }

    @Override
    public void close(Player player) {
        if (paginator != null)
            paginator.firstPage(player);
        viewers.remove(player);
    }

    @Override
    public void refresh() {
        for (Player player : viewers.keySet())
            refresh(player);
    }

    @Override
    public void refresh(Player player) {
        Preconditions.checkState(isViewing(player), "Unable to refresh. Player " + player.getName() + " is not viewing at inventory now.");
        InventoryView view = viewers.get(player);

        view.getInventory().clear();

        for (Entry<Integer, Function<Player, Icon>> entry : globalIcons.entrySet())
            view.getInventory().setItem(entry.getKey(), entry.getValue().apply(player).getIcon());

        if (!personalIcons.containsKey(player))
            return;

        Map<Integer, Icon> icons = personalIcons.get(player);
        for (Entry<Integer, Icon> entry : icons.entrySet())
            view.getInventory().setItem(entry.getKey(), entry.getValue().getIcon());
    }

    @Override
    public void clear() {
        globalIcons.clear();
    }

    @Override
    public Function<Player, Icon> getGlobalIcon(int slot) {
        return globalIcons.getOrDefault(slot, null);
    }

    @Override
    public PersonalViewInventory setGlobalIcon(int slot, Function<Player, Icon> iconFunction) {
        globalIcons.put(slot, iconFunction);
        return this;
    }

    @Override
    public Icon getPersonalIcon(Player player, int slot) {
        if (!personalIcons.containsKey(player))
            personalIcons.put(player, Maps.newHashMap());
        return personalIcons.get(player).getOrDefault(slot, null);
    }

    @Override
    public PersonalViewInventory setPersonalIcon(Player player, int slot, Icon icon) {
        if (!personalIcons.containsKey(player))
            personalIcons.put(player, Maps.newHashMap());
        personalIcons.get(player).put(slot, icon);
        return this;
    }

    @Override
    public void resetPersonalIcons(Player player) {
        personalIcons.remove(player);
    }

    @Override
    public void clearSlot(Player player, int slot) {
        globalIcons.remove(slot);
        personalIcons.remove(player, slot);
    }
}