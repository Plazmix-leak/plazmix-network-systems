package net.plazmix.inventory.view.builder;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.plazmix.minecraft.platform.paper.inventory.InventoryData;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.paginator.PaginatorType;
import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import net.plazmix.minecraft.platform.paper.inventory.view.builder.PersonalViewInventoryBuilder;
import net.plazmix.inventory.view.SimplePersonalViewInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimplePersonalViewInventoryBuilder extends AbstractInventoryBaseBuilder<SimplePersonalViewInventoryBuilder, PersonalViewInventory> implements PersonalViewInventoryBuilder<SimplePersonalViewInventoryBuilder> {

    private final Map<Integer, Function<Player, Icon>> globalIcons = Maps.newHashMap();
    @Getter
    private BiFunction<Player, PersonalViewInventory, String> titleApplier;
    @Getter
    private Integer[] paginatorScheme;
    @Getter
    private PaginatorType paginatorType = PaginatorType.NONE;

    public SimplePersonalViewInventoryBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public PersonalViewInventory build() {
        return new SimplePersonalViewInventory(getPlugin(), getChestRows(), getOpeningAction(), getClosingAction(), getTitleApplier(), globalIcons, getPaginatorScheme(), getPaginatorType());
    }

    @Override
    public SimplePersonalViewInventoryBuilder setType(InventoryType type) {
        this.type = type;
        return this;
    }

    @Override
    public SimplePersonalViewInventoryBuilder setOpeningAction(Consumer<InventoryData> consumer) {
        this.openingAction = consumer;
        return this;
    }

    @Override
    public SimplePersonalViewInventoryBuilder setClosingAction(Consumer<InventoryData> consumer) {
        this.closingAction = consumer;
        return this;
    }

    @Override
    public SimplePersonalViewInventoryBuilder setChestRows(int rows) {
        this.chestRows = rows;
        return this;
    }

    @Override
    public SimplePersonalViewInventoryBuilder setTitleApplier(
            BiFunction<Player, PersonalViewInventory, String> biFunction) {
        this.titleApplier = biFunction;
        return this;
    }

    @Override
    public SimplePersonalViewInventoryBuilder withGlobalIcon(int slot, Function<Player, Icon> iconFunction) {
        globalIcons.put(slot, iconFunction);
        return this;
    }

    @Override
    public SimplePersonalViewInventoryBuilder setPaginatorScheme(PaginatorType paginatorType, Integer[] fillScheme) {
        this.paginatorScheme = fillScheme;
        this.paginatorType = paginatorType;
        return this;
    }
}