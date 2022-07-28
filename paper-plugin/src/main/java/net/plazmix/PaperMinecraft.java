package net.plazmix;

import net.plazmix.minecraft.AbstractMinecraft;
import net.plazmix.minecraft.game.GameRegistry;
import net.plazmix.minecraft.hologram.HologramBuilder;
import net.plazmix.minecraft.platform.Platform;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import net.plazmix.minecraft.tag.TagFactory;
import net.plazmix.network.module.LocalizationModule;
import net.plazmix.network.server.ServerVersion;
import net.plazmix.game.PaperGameRegistry;
import net.plazmix.util.time.ticker.TimeTicker;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;

public class PaperMinecraft extends AbstractMinecraft {

    private final Optional<PaperSpigotPlatform> platform;
    private final PaperGameRegistry gameRegistry = new PaperGameRegistry(PlazmixAPI.getNetwork().getModule(LocalizationModule.class).get());

    public PaperMinecraft(Plugin plugin, ServerVersion serverVersion) {
        this.platform = Optional.of(new MinecraftPaperSpigotPlatform(plugin, serverVersion));
        plugin.getServer().getPluginManager().registerEvents(gameRegistry, plugin);
    }

    @Override
    public <T extends Platform> Optional<T> getPlatform(Class<T> clazz) {
        return (Optional<T>) platform;
    }

    @Override
    public HologramBuilder newHologramBuilder() {
        return null;
    }

    @Override
    public GameRegistry getGameRegistry() {
        return gameRegistry;
    }

    @Override
    public TagFactory getTagFactory() {
        return null;
    }

    @Override
    public <T extends TimeTicker> T runTimeTicker(T timeTicker) {
        return null;
    }

    @Override
    public <T extends TimeTicker> T runAsyncTimeTicker(T timeTicker) {
        return null;
    }

    @Override
    public TimeTicker getTimeTickerById(UUID uuid) {
        return null;
    }

    @Override
    public <T extends TimeTicker> T getTimeTickerById(UUID uuid, Class<T> tickerClass) {
        return null;
    }

    @Override
    public <T extends TimeTicker> T terminateTimeTicker(T timeTicker) {
        return null;
    }

    @Override
    public <T extends TimeTicker> T terminateTimeTickerById(UUID uuid) {
        return null;
    }
}
