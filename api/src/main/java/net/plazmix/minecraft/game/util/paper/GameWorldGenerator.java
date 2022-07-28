package net.plazmix.minecraft.game.util.paper;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.swm.api.exceptions.NewerFormatException;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.exceptions.WorldInUseException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import lombok.experimental.UtilityClass;
import net.plazmix.PlazmixAPI;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.GameWorld;
import net.plazmix.minecraft.game.mode.WorldGame;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import net.plazmix.minecraft.util.paper.Schedulers;
import net.plazmix.util.Result;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.function.Consumer;

@UtilityClass
public class GameWorldGenerator {

    public static void generate(Plugin plugin, SlimePropertyMap propertyMap, String worldName) {
        SlimePlugin slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        SlimeLoader sqlLoader = slimePlugin.getLoader("mongodb");

        Schedulers.handleSupplier(plugin, () -> {
            try {
                return slimePlugin.loadWorld(sqlLoader, worldName, true, propertyMap);
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException ex) {
                return null;
            }
        }, world -> {
            PlazmixAPI.getLogger().info("Generating world!");
            if (world != null) {
                slimePlugin.generateWorld(world);
                PlazmixAPI.getLogger().info("World is generated successfully!");
            }
        });
    }

    public static <T extends GameSession> void runLater(Plugin plugin, WorldGame<T> game, String worldName, String mapName, long delay, Consumer<T> then) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            World bukkitWorld = Bukkit.getWorld(worldName);
            Result<T> result = game.run(PlazmixAPI.getMinecraft().getPlatform(PaperSpigotPlatform.class).get().getClassMapper().toGameWorld(bukkitWorld));
            result.onSuccess(() -> {
                result.getEntity().ifPresent(s -> {
                    s.getCache().set(GameSession.GAME_SESSION_TITLE, mapName);
                    then.accept(s);
                    PlazmixAPI.getLogger().info("Game is started successfully!");
                });
            }).onFailure(() -> {
                PlazmixAPI.getLogger().info(String.format("Game start is failed: %s", result.getDescription().orElse("no information found")));
            });
        }, delay);
    }

    public static void unloadWorld(GameWorld world) {
        PaperSpigotPlatform platform = PlazmixAPI.getMinecraft().getPlatform(PaperSpigotPlatform.class).get();
        platform.getClassMapper().unloadWorld(platform.getClassMapper().fromGameWorld(world));
    }

    public static void unloadWorld(World world) {
        PlazmixAPI.getMinecraft().getPlatform(PaperSpigotPlatform.class).get().getClassMapper().unloadWorld(world);
    }
}
