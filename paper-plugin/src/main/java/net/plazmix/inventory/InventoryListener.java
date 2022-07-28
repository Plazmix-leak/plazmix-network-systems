package net.plazmix.inventory;

import net.plazmix.minecraft.platform.paper.inventory.ClickData;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

import java.util.function.Predicate;

public class InventoryListener implements Listener {

    private final Predicate<InventoryEvent> inventoryCheckPredicate = event ->
            event.getInventory().getHolder() instanceof InventoryView;

    @EventHandler
    private void on(InventoryOpenEvent event) {
        if (!inventoryCheckPredicate.test(event))
            return;

        InventoryView view = (InventoryView) event.getInventory().getHolder();
        view.getOwner().getOpeningAction().accept(InventoryData.create(event.getPlayer(), view.getInventory()));
    }

    @EventHandler
    private void on(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || !(event.getInventory().getHolder() instanceof InventoryView))
            return;

        event.setCancelled(true);

        boolean isBottomClick = event.getClickedInventory().equals(event.getView().getBottomInventory());
        if (isBottomClick)
            return;

        InventoryView view = (InventoryView) event.getView().getTopInventory().getHolder();
        ClickData data = ClickData.create(event.getWhoClicked(), view.getInventory(), event.getClick(),
                event.getCurrentItem(), event.getRawSlot(), event.getAction());
        if (view.getOwner().isEmptyClick(data))
            return;
        Icon icon = view.getOwner().getIcon(data);
        icon.getClickAction().accept(data);
    }

    @EventHandler
    private void on(InventoryDragEvent event) {
        if (event.getInventory() == null || !inventoryCheckPredicate.test(event))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    private void on(InventoryCloseEvent event) {
        if (!inventoryCheckPredicate.test(event))
            return;

        InventoryView view = (InventoryView) event.getInventory().getHolder();

        if (!view.getOwner().getCurrentViewers().contains(event.getPlayer()))
            return;

        view.getOwner().getClosingAction().accept(InventoryData.create(event.getPlayer(), view.getInventory()));
        view.getOwner().close((Player) event.getPlayer());
    }
}