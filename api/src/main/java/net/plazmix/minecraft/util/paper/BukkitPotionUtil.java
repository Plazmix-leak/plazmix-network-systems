package net.plazmix.minecraft.util.paper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@UtilityClass
public class BukkitPotionUtil {

    public static PotionEffect getInfinityPotion(@NonNull PotionEffectType potionEffectType) {
        return getPotion(potionEffectType, Integer.MAX_VALUE);
    }

    public static PotionEffect getInfinityPotion(@NonNull PotionEffectType potionEffectType, int amplifier) {
        return getPotion(potionEffectType, Integer.MAX_VALUE, amplifier);
    }

    public static PotionEffect getPotion(@NonNull PotionEffectType potionEffectType, int duration) {
        return getPotion(potionEffectType, duration, 1);
    }

    public static PotionEffect getPotion(@NonNull PotionEffectType potionEffectType, int duration, int amplifier) {
        return new PotionEffect(potionEffectType, duration, amplifier);
    }
}