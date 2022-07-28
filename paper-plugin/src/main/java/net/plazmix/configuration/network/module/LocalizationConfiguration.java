package net.plazmix.configuration.network.module;

import lombok.Builder;
import lombok.Data;
import net.plazmix.configuration.network.module.source.LocalizationSource;

@Data
@Builder
public class LocalizationConfiguration {

    private boolean enabled;
    private LocalizationSource source;
}
