package net.plazmix.network.module.gamemap;

import lombok.Data;
import net.plazmix.network.module.GameMapModule;

import java.util.Collection;

@Data
public class GameMap implements GameMapModule.GameMap {

    private final String mapTitle;
    private final Collection<String> supportedGames;
}
