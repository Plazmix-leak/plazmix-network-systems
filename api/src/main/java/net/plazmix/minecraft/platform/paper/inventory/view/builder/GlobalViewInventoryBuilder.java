package net.plazmix.minecraft.platform.paper.inventory.view.builder;

import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.view.GlobalViewInventory;

public interface GlobalViewInventoryBuilder<T extends GlobalViewInventoryBuilder<T>> extends InventoryBaseBuilder<T, GlobalViewInventory> {

    String getTitle();

    T setTitle(String title);

    T withIcon(int slot, Icon icon);
}