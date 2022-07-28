package net.plazmix.network.security;

import lombok.Data;

@Data(staticConstructor = "of")
public final class SecurityKey<T> {

    private final Class<T> type;
    private final String key;
}
