package net.plazmix.minecraft.platform.paper.inventory;

import net.plazmix.minecraft.platform.paper.inventory.view.InventoryBase;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryView;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface InventoryData {

    static InventoryData create(HumanEntity entity, Inventory inventory) {
        return new InventoryData() {

            @Override
            public Player getPlayer() {
                return (Player) entity;
            }

            @Override
            public Inventory getInventory() {
                return inventory;
            }
        };
    }

    Inventory getInventory();

    Player getPlayer();

    default InventoryBase getInventoryBase() {
        return ((InventoryView) getInventory().getHolder()).getOwner();
    }
}