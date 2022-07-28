package net.plazmix.minecraft.game.player.paper;

import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.PlayerDecorator;
import net.plazmix.minecraft.util.paper.BukkitPotionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class DefaultSpectator extends PlayerDecorator {

    public static final String IS_SPECTATOR = "IS_SPECTATOR";
    private static final Map<UUID, DefaultSpectator> CACHE = Maps.newHashMap();

    protected DefaultSpectator(GamePlayer player) {
        super(player);
    }

    public static DefaultSpectator of(GamePlayer gamePlayer) {
        return CACHE.computeIfAbsent(gamePlayer.getUniqueId(), uuid -> new DefaultSpectator(gamePlayer));
    }

    public static <T extends DefaultSpectator> T of(GamePlayer gamePlayer, Class<T> clazz, Supplier<T> supplier) {
        if (!CACHE.containsKey(gamePlayer.getUniqueId())) {
            T spectator = supplier.get();
            CACHE.put(gamePlayer.getUniqueId(), spectator);
            return spectator;
        }

        Object spectator = CACHE.get(gamePlayer.getUniqueId());
        if (spectator.getClass() == clazz) {
            return (T) spectator;
        }

        T custom = supplier.get();
        CACHE.put(gamePlayer.getUniqueId(), custom);
        return custom;
    }

    public boolean isSpectating() {
        return player.getCache().get(IS_SPECTATOR);
    }

    public void setSpectating(boolean value) {
        this.player.getCurrentGame().ifPresent(session -> {
            player.getCache().set(IS_SPECTATOR, value);

            Player player = Bukkit.getPlayer(getUniqueId());

            player.getInventory().setArmorContents(new ItemStack[4]);
            player.getInventory().clear();

            player.setGameMode(GameMode.ADVENTURE);

            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);

            if (value) {
                player.spigot().setCollidesWithEntities(false);
                player.setMaximumNoDamageTicks(Integer.MAX_VALUE);
                player.setNoDamageTicks(Integer.MAX_VALUE);

                player.addPotionEffect(BukkitPotionUtil.getInfinityPotion(PotionEffectType.INVISIBILITY));
                player.addPotionEffect(BukkitPotionUtil.getInfinityPotion(PotionEffectType.SPEED));

                player.setAllowFlight(true);
                player.setFlying(true);

                for (GamePlayer otherPlayer : session.getPlayers()) {
                    Player bukkitHandle = Bukkit.getPlayer(otherPlayer.getUniqueId());
                    if (new DefaultSpectator(otherPlayer).isSpectating()) {
                        bukkitHandle.showPlayer(player);
                        player.showPlayer(bukkitHandle);
                    } else {
                        bukkitHandle.hidePlayer(player);
                    }
                }
            } else {
                player.spigot().setCollidesWithEntities(true);
                player.setMaximumNoDamageTicks(0);
                player.setNoDamageTicks(0);

                player.setGameMode(Bukkit.getServer().getDefaultGameMode());

                player.setFlying(false);
                player.setAllowFlight(false);

                for (GamePlayer otherPlayer : session.getPlayers()) {
                    Player bukkitHandle = Bukkit.getPlayer(otherPlayer.getUniqueId());
                    bukkitHandle.showPlayer(player);
                    player.showPlayer(bukkitHandle);
                }
            }
        });
    }
}
