package net.plazmix.module;

import com.google.common.collect.Maps;
import lombok.Data;
import net.plazmix.config.ConfigFile;
import net.plazmix.config.paper.YamlConfigFile;
import net.plazmix.network.module.LocalizationModule;
import net.plazmix.network.module.configuration.ModuleConfiguration;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class YamlLocalizationModule extends YamlConfigFile implements LocalizationModule {

    private static final Map<Class<?>, PlaceholderData<Object>> placeholderDataMap = Maps.newHashMap();
    private Locale defaultLocale;

    public YamlLocalizationModule(File folder) {
        super(folder, "localization");
        load();
    }

    @Override
    public ConfigFile<YamlConfiguration> load() {
        ConfigFile<YamlConfiguration> file = super.load();

        this.defaultLocale = Locale.forLanguageTag(file.getString("default-locale"));

        return file;
    }

    @Override
    public <T> Result<Void> registerPlaceholderFunction(Class<T> placeholderClass, String placeholder, Function<T, String> function) {
        return new Result<>(placeholderDataMap.putIfAbsent(placeholderClass, new PlaceholderData<>(placeholder, o -> function.apply((T) o))) == null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Text getDefaultText(String key) {
        return getLocalizedText(defaultLocale, key);
    }

    @Override
    public Text getLocalizedText(Locale locale, String key) {
        String fullKey = locale.toLanguageTag() + "." + key;
        if (unwrap().isList(fullKey))
            return new LocaleText(getStringList(fullKey).stream().toArray(String[]::new));
        return new LocaleText(new String[] {fullKey});
    }

    @Override
    public Text getUserText(User user, String key) {
        return getLocalizedText(user.getLocale(), key);
    }

    @Override
    public void configure(ModuleConfiguration configuration) {}

    @Override
    public void invalidate() {}

    @Data
    static class PlaceholderData<T> {

        private final String placeholder;
        private final Function<T, String> mapper;
    }

    @Data
    static class LocaleText implements Text {

        private final String[] sourceLines;

        @Override
        public Text format(Object object) {
            if (placeholderDataMap.containsKey(object.getClass())) {
                String[] lines = Arrays.stream(sourceLines)
                        .map(str -> {
                            PlaceholderData<Object> placeholderData = placeholderDataMap.get(object.getClass());
                            return str.replace(placeholderData.getPlaceholder(), placeholderData.getMapper().apply(object));
                        })
                        .toArray(String[]::new);
                return new LocaleText(lines);
            }
            return this;
        }

        @Override
        public Text formatAll(Object... objects) {
            boolean changed = false;
            String[] lines = sourceLines;
            for (Object object : objects) {
                if (!placeholderDataMap.containsKey(object.getClass()))
                    continue;
                lines = Arrays.stream(lines)
                        .map(str -> {
                            PlaceholderData<Object> placeholderData = placeholderDataMap.get(object.getClass());
                            return str.replace(placeholderData.getPlaceholder(), placeholderData.getMapper().apply(object));
                        })
                        .toArray(String[]::new);
                changed = true;
            }
            return changed ? new LocaleText(lines) : this;
        }

        @Override
        public String[] getLines() {
            return sourceLines;
        }

        @Override
        public Optional<String> getFirstLine() {
            if (sourceLines.length > 0)
                return Optional.of(sourceLines[0]);
            return Optional.empty();
        }

        @Override
        public Optional<String> getLine(int index) {
            if (sourceLines.length <= index)
                return Optional.empty();
            return Optional.of(sourceLines[index]);
        }
    }
}
