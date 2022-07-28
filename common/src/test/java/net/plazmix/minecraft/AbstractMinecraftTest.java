package net.plazmix.minecraft;

import net.plazmix.minecraft.game.GameRegistry;
import net.plazmix.minecraft.game.logic.builder.MinecraftGameLogicBuilder;
import net.plazmix.minecraft.game.mode.ServerGame;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;
import net.plazmix.minecraft.hologram.HologramBuilder;
import net.plazmix.minecraft.platform.Platform;
import net.plazmix.minecraft.tag.TagFactory;
import net.plazmix.util.time.ticker.TimeTicker;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AbstractMinecraftTest {

    private final AbstractMinecraft abstractMinecraft = new AbstractMinecraft() {
        @Override
        public <T extends Platform> Optional<T> getPlatform(Class<T> clazz) {
            return Optional.empty();
        }

        @Override
        public HologramBuilder newHologramBuilder() {
            return null;
        }

        @Override
        public GameRegistry getGameRegistry() {
            return null;
        }

        @Override
        public TagFactory getTagFactory() {
            return null;
        }

        @Override
        public <T extends TimeTicker> T runTimeTicker(T timeTicker) {
            return null;
        }

        @Override
        public <T extends TimeTicker> T runAsyncTimeTicker(T timeTicker) {
            return null;
        }

        @Override
        public TimeTicker getTimeTickerById(UUID uuid) {
            return null;
        }

        @Override
        public <T extends TimeTicker> T getTimeTickerById(UUID uuid, Class<T> tickerClass) {
            return null;
        }

        @Override
        public <T extends TimeTicker> T terminateTimeTicker(T timeTicker) {
            return null;
        }

        @Override
        public <T extends TimeTicker> T terminateTimeTickerById(UUID uuid) {
            return null;
        }
    };

    @Test
    void newGameBuilder_shouldBeCreated_whenServerGame() {
        ServerGame game = abstractMinecraft.newGameBuilder(ServerGame.class)
                .withName("TestGame")
                .addState(abstractMinecraft.newGameStateBuilder()
                        .withName("state")
                        .withLogic(new MinecraftGameLogicBuilder<>()
                                .build())
                        .build())
                .build();
        game.run();

        assertNotNull(game);
        assertEquals("TestGame", game.getName());
    }

    @Test
    void newGameBuilder_shouldBeCreated_whenServerTeamGame() {
        ServerTeamGame game = abstractMinecraft.newGameBuilder(ServerTeamGame.class)
                .withName("TestTeamGame")
                .addState(abstractMinecraft.newGameStateBuilder()
                        .withName("state")
                        .withLogic(new MinecraftGameLogicBuilder<>()
                                .build())
                        .build())
                .build();
        game.run();

        assertNotNull(game);
        assertEquals("TestTeamGame", game.getName());
    }
}
