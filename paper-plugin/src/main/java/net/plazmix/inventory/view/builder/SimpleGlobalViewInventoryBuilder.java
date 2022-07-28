package net.plazmix.inventory.view.builder;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.view.GlobalViewInventory;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.GlobalViewInventoryBuilder;
import net.plazmix.inventory.view.SimpleGlobalViewInventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.function.Consumer;

public class SimpleGlobalViewInventoryBuilder extends AbstractInventoryBaseBuilder<SimpleGlobalViewInventoryBuilder, GlobalViewInventory> implements GlobalViewInventoryBuilder<SimpleGlobalViewInventoryBuilder> {

    private final Map<Integer, Icon> icons = Maps.newHashMap();
    @Getter
    private String title;

    public SimpleGlobalViewInventoryBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public GlobalViewInventory build() {
        return new SimpleGlobalViewInventory(getPlugin(), getType(), getChestRows(), getOpeningAction(), getClosingAction(), getTitle(), icons);
    }

    @Override
    public SimpleGlobalViewInventoryBuilder setType(InventoryType type) {
        this.type = type;
        return this;
    }

    @Override
    public SimpleGlobalViewInventoryBuilder setOpeningAction(Consumer<InventoryData> consumer) {
        this.openingAction = consumer;
        return this;
    }

    @Override
    public SimpleGlobalViewInventoryBuilder setClosingAction(Consumer<InventoryData> consumer) {
        this.closingAction = consumer;
        return this;
    }

    @Override
    public SimpleGlobalViewInventoryBuilder setChestRows(int rows) {
        this.chestRows = rows;
        return this;
    }

    @Override
    public SimpleGlobalViewInventoryBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public SimpleGlobalViewInventoryBuilder withIcon(int slot, Icon icon) {
        icons.put(slot, icon);
        return this;
    }
}