package net.plazmix.inventory.view;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.plazmix.minecraft.platform.paper.inventory.ClickData;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.view.GlobalViewInventory;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SimpleGlobalViewInventory extends AbstractInventory implements GlobalViewInventory {

    @Getter
    private final String title;
    private final Map<Integer, Icon> icons;
    private final InventoryView view;

    public SimpleGlobalViewInventory(Plugin plugin, InventoryType type, int chestRows, Consumer<InventoryData> openingAction,
                                     Consumer<InventoryData> closingAction, String title, Map<Integer, Icon> icons) {
        super(plugin, type, openingAction, closingAction);
        this.title = title;
        this.icons = icons;
        if (type != InventoryType.CHEST || chestRows == 0)
            this.view = new InventoryView(this, title);
        else
            this.view = new InventoryView(this, title, chestRows);
        refresh();
    }

    @Override
    public Collection<Player> getCurrentViewers() {
        return view.getInventory().getViewers().stream().map(entity -> (Player) entity).collect(Collectors.toSet());
    }

    @Override
    public Icon getIcon(ClickData data) {
        return getIcon(data.getClickedSlot());
    }

    @Override
    public int getSlots() {
        return view.getInventory().getSize();
    }

    @Override
    public void open(Player player) {
        refresh();
        player.openInventory(view.getInventory());
    }

    @Override
    public void close(Player player) {
    }

    @Override
    public void refresh() {
        view.getInventory().clear();
        for (Entry<Integer, Icon> entry : icons.entrySet())
            view.getInventory().setItem(entry.getKey(), entry.getValue().getIcon());
    }

    @Override
    public void clear() {
        icons.clear();
    }

    @Override
    public Icon getIcon(int slot) {
        return icons.getOrDefault(slot, null);
    }

    @Override
    public GlobalViewInventory setIcon(int slot, Icon icon) {
        Preconditions.checkNotNull(icon, "Icon is null!");
        icons.put(slot, icon);
        return this;
    }

    @Override
    public void clearSlot(int slot) {
        icons.remove(slot);
    }
}