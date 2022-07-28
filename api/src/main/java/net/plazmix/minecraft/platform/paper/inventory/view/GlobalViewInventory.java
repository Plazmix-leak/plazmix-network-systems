package net.plazmix.minecraft.platform.paper.inventory.view;

import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;

public interface GlobalViewInventory extends InventoryBase {

    String getTitle();

    Icon getIcon(int slot);

    GlobalViewInventory setIcon(int slot, Icon icon);

    void clearSlot(int slot);

    default boolean isEmptySlot(int slot) {
        return getIcon(slot) == null;
    }
}