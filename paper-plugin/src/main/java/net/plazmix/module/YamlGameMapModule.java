package net.plazmix.module;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimeProperty;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import net.plazmix.PlazmixAPI;
import net.plazmix.config.ConfigFile;
import net.plazmix.config.paper.YamlConfigFile;
import net.plazmix.network.module.GameMapModule;
import net.plazmix.network.module.configuration.ModuleConfiguration;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class YamlGameMapModule extends YamlConfigFile implements GameMapModule, GameMapModule.SlimeMapExtension {

    private final Collection<String> gameNames = Sets.newHashSet();
    private final Map<String, GameMap> gameMaps = Maps.newHashMap();
    private final Map<String, SlimePropertyMap> slimeMapProperties = Maps.newHashMap();

    public YamlGameMapModule(File folder) {
        super(folder, "maps");
        load();
    }

    @Override
    public ConfigFile<YamlConfiguration> load() {
        ConfigFile<YamlConfiguration> file = super.load();
        gameNames.clear();
        gameMaps.clear();

        gameNames.addAll(getStringList("games"));
        SlimePropertyMap propertyMap = new SlimePropertyMap();
        for (String key : getKeys("maps")) {
            gameMaps.put(key, new net.plazmix.network.module.gamemap.
                    GameMap(getString("maps." + key + ".title"),
                    getStringList("maps." + key + ".supported-games")));

            for (String property : getStringList("maps." + key + ".slime-properties")) {
                String[] data = property.split(Pattern.quote("="));
                if (data.length < 2) {
                    PlazmixAPI.getLogger().warning(String.format("Failed to load property: %s. Wrong property length", property));
                    continue;
                }

                String tag = data[0];
                AtomicReference<Object> value = new AtomicReference<>();
                if (data.length > 2)
                    value.set(StringUtils.join(Arrays.copyOfRange(data, 1, data.length), "="));
                else {
                    String val = data[1];
                    if (StringUtils.isNumeric(val.startsWith("-") ? val.substring(1) : val))
                        value.set(Integer.parseInt(data[1]));
                    else if (val.equals("true") || val.equals("false"))
                        value.set(Boolean.valueOf(data[1]));
                }

                Optional<SlimeProperty> slimeProperty = getProperty(tag);
                if (slimeProperty.isPresent()) {
                    Object val = value.get();
                    if (val instanceof Integer)
                        propertyMap.setInt(slimeProperty.get(), (Integer) val);
                    else if (val instanceof Boolean)
                        propertyMap.setBoolean(slimeProperty.get(), (Boolean) val);
                    else if (val instanceof String)
                        propertyMap.setString(slimeProperty.get(), (String) val);
                }
            }

            slimeMapProperties.put(key, propertyMap);
        }

        return file;
    }

    private Optional<SlimeProperty> getProperty(String tag) {
        return Arrays.stream(SlimeProperties.VALUES).filter(slimeProperty -> slimeProperty.getNbtName().equals(tag))
                .findFirst();
    }

    @Override
    public Optional<GameMap> getGameMap(String name) {
        return Optional.ofNullable(gameMaps.get(name));
    }

    @Override
    public Collection<GameMap> getMaps(String gameName) {
        return gameMaps.values().stream()
                .filter(map -> map.getSupportedGames().contains(gameName))
                .collect(Collectors.toSet());
    }

    @Override
    public SlimePropertyMap getPropertyMap(String name) {
        return slimeMapProperties.getOrDefault(name, new SlimePropertyMap());
    }

    @Override
    public SlimeMapExtension slime() {
        return this;
    }

    @Override
    public void configure(ModuleConfiguration configuration) {}

    @Override
    public void invalidate() {}
}
