package net.plazmix.configuration.network.module;

import lombok.Builder;
import lombok.Data;
import net.plazmix.configuration.network.module.source.GameMapSource;

@Data
@Builder
public class GameMapConfiguration {

    private boolean enabled;
    private GameMapSource source;
}
