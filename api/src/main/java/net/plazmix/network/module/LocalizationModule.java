package net.plazmix.network.module;

import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public interface LocalizationModule extends NetworkModule {

    <T> Result<Void> registerPlaceholderFunction(Class<T> placeholderClass, String placeholder, Function<T, String> function);

    Text getDefaultText(String key);

    Text getLocalizedText(Locale locale, String key);

    Text getUserText(User user, String key);

    default Text getDefaultText(SystemTextKey key) {
        return getDefaultText(key.name());
    }

    default Text getLocalizedText(Locale locale, SystemTextKey key) {
        return getLocalizedText(locale, key.name());
    }

    default Text getUserText(User user, SystemTextKey key) {
        return getUserText(user, key.name());
    }

    interface Text {

        Text format(Object object);

        Text formatAll(Object... objects);

        String[] getLines();

        Optional<String> getFirstLine();

        Optional<String> getLine(int index);
    }

    enum SystemTextKey {
        GAMESTATE_JOIN_DISALLOWED,
        GAME_IS_FULL
    }
}
