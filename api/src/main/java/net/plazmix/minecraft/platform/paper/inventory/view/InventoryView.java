package net.plazmix.minecraft.platform.paper.inventory.view;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryView implements InventoryHolder {

    private final InventoryBase owner;
    private final Inventory inventory;

    public InventoryView(InventoryBase owner, String title) {
        this.owner = owner;
        this.inventory = Bukkit.createInventory(this, owner.getType(), title);
    }

    public InventoryView(InventoryBase owner, String title, int rows) {
        Preconditions.checkArgument(owner.getType() == InventoryType.CHEST, owner.getType().name() + " inventory type does not support rows option");
        Preconditions.checkArgument(rows > 0 && rows <= 6, "Invalid rows amount: " + rows);
        this.owner = owner;
        this.inventory = Bukkit.createInventory(this, 9 * rows, title);
    }

    public InventoryBase getOwner() {
        return owner;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}