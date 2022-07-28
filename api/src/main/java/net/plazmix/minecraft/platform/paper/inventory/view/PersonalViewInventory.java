package net.plazmix.minecraft.platform.paper.inventory.view;

import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.paginator.Paginator;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface PersonalViewInventory extends InventoryBase {

    Paginator getPaginator();

    void refresh(Player player);

    <T extends PersonalViewInventory> BiFunction<Player, T, String> getTitleApplier();

    Function<Player, Icon> getGlobalIcon(int slot);

    PersonalViewInventory setGlobalIcon(int slot, Function<Player, Icon> iconFunction);

    Icon getPersonalIcon(Player player, int slot);

    PersonalViewInventory setPersonalIcon(Player player, int slot, Icon icon);

    void resetPersonalIcons(Player player);

    void clearSlot(Player player, int slot);

    default PersonalViewInventory setGlobalIcon(int slot, Icon icon) {
        return setGlobalIcon(slot, player -> icon);
    }

    default boolean hasGlobalIcon(int slot) {
        return getGlobalIcon(slot) != null;
    }

    default boolean hasPersonalIcon(Player player, int slot) {
        return getPersonalIcon(player, slot) != null;
    }

    default boolean isOverlayed(Player player, int slot) {
        return hasGlobalIcon(slot) && hasPersonalIcon(player, slot);
    }
}