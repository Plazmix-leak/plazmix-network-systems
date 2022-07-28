package net.plazmix.inventory.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryBase;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractInventory implements InventoryBase {

    private final Plugin plugin;
    private final InventoryType type;
    private final Consumer<InventoryData> openingAction, closingAction;
}