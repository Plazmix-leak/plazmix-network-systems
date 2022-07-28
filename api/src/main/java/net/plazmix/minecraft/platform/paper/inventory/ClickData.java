package net.plazmix.minecraft.platform.paper.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ClickData extends InventoryData {

    static ClickData create(HumanEntity entity, Inventory inventory, ClickType clickType, ItemStack item, int slot,
                            InventoryAction action) {
        return new ClickData() {

            @Override
            public Player getPlayer() {
                return (Player) entity;
            }

            @Override
            public Inventory getInventory() {
                return inventory;
            }

            @Override
            public ClickType getType() {
                return clickType;
            }

            @Override
            public ItemStack getClickedItem() {
                return item;
            }

            @Override
            public int getClickedSlot() {
                return slot;
            }

            @Override
            public InventoryAction getAction() {
                return action;
            }
        };
    }

    ClickType getType();

    InventoryAction getAction();

    ItemStack getClickedItem();

    int getClickedSlot();
}