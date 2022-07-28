package net.plazmix;

import lombok.Getter;
import net.plazmix.configuration.PaperPluginConfiguration;
import net.plazmix.network.server.ServerVersion;
import net.plazmix.inventory.InventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
public class PlazmixPlugin extends JavaPlugin implements PlazmixNetworkPlugin {

    private UUID uniqueId;

    @Override
    public void onLoad() {
        this.uniqueId = UUID.nameUUIDFromBytes(super.getName().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        PaperPluginConfiguration configuration = new PaperPluginConfiguration(this.getDataFolder());

        ApiInitializer initializer = new ApiInitializer();

        if (configuration.getLogger().isEnabled())
            initializer.initialize("logger", getLogger());

        if (configuration.getNetwork().isEnabled())
            initializer.initialize("network", new PaperNetwork(this, this, configuration.getNetwork()));

        if (configuration.getMinecraft().isEnabled())
            initializer.initialize("minecraft", new PaperMinecraft(this, getServerVersion()));

        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    public String getVersion() {
        return super.getDescription().getVersion();
    }

    @Override
    public ServerVersion getServerVersion() {
        return null;
    }
}
