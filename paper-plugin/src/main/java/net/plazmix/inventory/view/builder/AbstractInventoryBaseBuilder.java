package net.plazmix.inventory.view.builder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.view.InventoryBase;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.InventoryBaseBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractInventoryBaseBuilder<T extends AbstractInventoryBaseBuilder<T, R>, R extends InventoryBase> implements InventoryBaseBuilder<T, R> {

    @Getter
    protected final Plugin plugin;
    @Getter
    protected InventoryType type = InventoryType.CHEST;
    @Getter
    protected Consumer<InventoryData> openingAction = data -> {
    }, closingAction = data -> {
    };
    @Getter
    protected int chestRows = 0;
}