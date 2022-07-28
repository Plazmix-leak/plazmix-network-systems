package net.plazmix.minecraft.platform.paper.inventory.view.builder;

import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryBase;
import net.plazmix.util.function.Builder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface InventoryBaseBuilder<B extends InventoryBaseBuilder<B, R>, R extends InventoryBase> extends Builder<R> {

    Plugin getPlugin();

    InventoryType getType();

    B setType(InventoryType type);

    Consumer<InventoryData> getOpeningAction();

    B setOpeningAction(Consumer<InventoryData> consumer);

    Consumer<InventoryData> getClosingAction();

    B setClosingAction(Consumer<InventoryData> consumer);

    int getChestRows();

    B setChestRows(int rows);
}