package net.plazmix.minecraft.platform.paper.inventory.view.builder;

import com.google.common.base.Preconditions;
import net.plazmix.minecraft.platform.paper.inventory.icon.Icon;
import net.plazmix.minecraft.platform.paper.inventory.paginator.PaginatorType;
import net.plazmix.minecraft.platform.paper.inventory.view.PersonalViewInventory;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface PersonalViewInventoryBuilder<T extends PersonalViewInventoryBuilder<T>> extends InventoryBaseBuilder<T, PersonalViewInventory> {

    BiFunction<Player, PersonalViewInventory, String> getTitleApplier();

    T setTitleApplier(BiFunction<Player, PersonalViewInventory, String> biFunction);

    default T setTitleApplier(Function<Player, String> titleApplier) {
        return setTitleApplier((player, inventory) -> titleApplier.apply(player));
    }

    T withGlobalIcon(int slot, Function<Player, Icon> iconFunction);

    T setPaginatorScheme(PaginatorType paginatorType, Integer[] fillScheme);

    Integer[] getPaginatorScheme();

    PaginatorType getPaginatorType();

    default T setTitle(String title) {
        return setTitleApplier((player, inventory) -> title);
    }

    default T withGlobalIcon(int slot, Icon icon) {
        return withGlobalIcon(slot, player -> icon);
    }

    default T setPaginatorScheme(PaginatorType paginatorType, String fillScheme) {
        String[] numbers = fillScheme.split(" ");
        Preconditions.checkArgument(StringUtils.isNumeric(StringUtils.join(numbers)), "Scheme must contain only numeric characters!");

        Integer[] slots = new Integer[numbers.length];
        for (int i = 0; i < numbers.length; i++)
            slots[i] = Integer.parseInt(numbers[i]);

        return setPaginatorScheme(paginatorType, slots);
    }
}