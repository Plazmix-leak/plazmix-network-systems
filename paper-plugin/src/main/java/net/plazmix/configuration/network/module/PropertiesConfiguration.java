package net.plazmix.configuration.network.module;

import lombok.Builder;
import lombok.Getter;
import net.plazmix.configuration.network.module.source.PropertiesSource;

@Getter
@Builder
public class PropertiesConfiguration {

    private boolean enabled;
    private PropertiesSource source;
}
