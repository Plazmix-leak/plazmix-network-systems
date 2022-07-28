package net.plazmix.network.module;

import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;

import java.util.Collection;
import java.util.Optional;

public interface GameMapModule extends NetworkModule {

    Optional<GameMap> getGameMap(String name);

    Collection<GameMap> getMaps(String gameName);

    SlimeMapExtension slime();

    interface GameMap {

        String getMapTitle();

        Collection<String> getSupportedGames();
    }

    interface SlimeMapExtension {

        SlimePropertyMap getPropertyMap(String name);
    }
}
