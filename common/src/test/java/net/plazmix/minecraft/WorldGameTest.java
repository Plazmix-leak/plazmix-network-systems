package net.plazmix.minecraft;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.GameWorld;
import net.plazmix.minecraft.game.WorldGameSession;
import net.plazmix.minecraft.game.logic.builder.MinecraftGameLogicBuilder;
import net.plazmix.minecraft.game.mode.WorldGame;
import net.plazmix.minecraft.game.mode.team.WorldTeamGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamGameSession;
import net.plazmix.minecraft.game.team.TeamMember;
import net.plazmix.minecraft.player.KeyGamePlayer;
import net.plazmix.util.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorldGameTest {

    private final MinecraftMock minecraftMock = new MinecraftMock();
    private final String KEY = "KEY", VALUE = "VALUE";
    private final UUID uuid = UUID.nameUUIDFromBytes("test".getBytes(StandardCharsets.UTF_8));
    private WorldGame<GameSession> worldGame;
    private WorldTeamGame<TeamGameSession> worldTeamGame;
    private GameWorld world1, world2;
    private GamePlayer player;

    @BeforeEach
    void setup() {
        this.worldGame = minecraftMock.getMinecraft().newGameBuilder(WorldGame.class)
                .withName("WorldGame")
                .addState(minecraftMock.getMinecraft().newGameStateBuilder()
                        .withName("state")
                        .withLogic(new MinecraftGameLogicBuilder<WorldGameSession>()
                                .allowPlayerAdd()
                                .build())
                        .build())
                .build();
        this.worldTeamGame = minecraftMock.getMinecraft().newGameBuilder(WorldTeamGame.class)
                .withName("WorldTeamGame")
                .addState(minecraftMock.getMinecraft().newGameStateBuilder()
                        .withName("state")
                        .withLogic(new MinecraftGameLogicBuilder<WorldGameSession>()
                                .allowPlayerAdd()
                                .build())
                        .build())
                .build();

        this.world1 = mock(GameWorld.class);
        when(world1.getName()).thenReturn("World1");

        this.world2 = mock(GameWorld.class);
        when(world2.getName()).thenReturn("World2");

        GamePlayer original = mock(GamePlayer.class);
        when(original.getName()).thenReturn("TestPlayer");
        when(original.getUniqueId()).thenReturn(uuid);
        this.player = new KeyGamePlayer(original);
    }

    @Test
    void addPlayer_shouldFindById_exists() {
        Result<GameSession> result = worldGame.run(world1);
        result.onFailure(() -> fail()).onSuccess(() -> {
            GameSession session = result.getEntity().orElseGet(() -> fail());
            session.addPlayer(player)
                    .onFailure(() -> fail());
            session.addPlayer(player)
                    .onSuccess(() -> fail());
            Optional<GamePlayer> playerOptional = session.getPlayerById(uuid);
            assertTrue(playerOptional.isPresent());
            assertEquals("TestPlayer", playerOptional.get().getName());
        });
    }

    @Test
    void addPlayer_shouldFindByName_exists() {
        Result<GameSession> result = worldGame.run(world1);
        result.onFailure(() -> fail()).onSuccess(() -> {
            GameSession session = result.getEntity().orElseGet(() -> fail());
            session.addPlayer(player)
                    .onFailure(() -> fail());
            session.addPlayer(player)
                    .onSuccess(() -> fail());
            Optional<GamePlayer> playerOptional = session.getPlayerByName("TestPlayer");

            assertTrue(playerOptional.isPresent());
            assertEquals(uuid, playerOptional.get().getUniqueId());
        });
    }

    @Test
    void removePlayer_shouldNotFindById_notFound() {
        Result<GameSession> result = worldGame.run(world1);
        result.onFailure(() -> fail()).onSuccess(() -> {
            GameSession session = result.getEntity().orElseGet(() -> fail());
            session.removePlayer(player)
                    .onSuccess(() -> fail());
            session.addPlayer(player);
            session.removePlayer(player)
                    .onFailure(() -> fail());
            Optional<GamePlayer> playerOptional = session.getPlayerById(uuid);

            assertFalse(playerOptional.isPresent());
        });
    }

    @Test
    void removePlayer_shouldNotFindByName_notFound() {
        Result<GameSession> result = worldGame.run(world1);
        result.onFailure(() -> fail()).onSuccess(() -> {
            GameSession session = result.getEntity().orElseGet(() -> fail());
            session.removePlayer(player)
                    .onSuccess(() -> fail());
            session.addPlayer(player);
            session.removePlayer(player)
                    .onFailure(() -> fail());
            Optional<GamePlayer> playerOptional = session.getPlayerByName("TestPlayer");

            assertFalse(playerOptional.isPresent());
        });
    }

    @Test
    void setCache_shouldSet_contains() {
        Result<GameSession> result = worldGame.run(world1);
        result.onFailure(() -> fail()).onSuccess(() -> {
            GameSession session = result.getEntity().orElseGet(() -> fail());
            session.getCache().set(KEY, VALUE);
            assertEquals(VALUE, session.getCache().get(KEY));
        });
    }

    @Test
    void runParallel_shouldRun_withoutSideEffects() {
        Result<GameSession> result1 = worldGame.run(world1).onFailure(() -> fail());
        Result<GameSession> result1Double = worldGame.run(world1);

        result1Double.onSuccess(() -> fail()).onFailure(() -> {
            Optional<String> description = result1Double.getDescription();

            assertTrue(description.isPresent());
            assertTrue(description.get().endsWith("is already created!"));
        });

        Result<GameSession> result2 = worldGame.run(world2).onFailure(() -> fail());
        GameSession session1 = result1.getEntity().orElseGet(() -> fail());
        GameSession session2 = result2.getEntity().orElseGet(() -> fail());

        assertNotEquals(session1.getSessionId(), session2.getSessionId());

        session1.addPlayer(player);

        assertTrue(session1.getPlayerById(player.getUniqueId()).isPresent());
        assertFalse(session2.getPlayerById(player.getUniqueId()).isPresent());
    }

    @Test
    void run_shouldRunWithCustomId_isCustomIdValid() {
        Result<GameSession> result = worldGame.run(world1, "custom").onFailure(() -> fail());
        assertTrue(result.getEntity().isPresent());
        assertEquals("custom", result.getEntity().get().getSessionId());
    }

    @Test
    void registerTeam_shouldRegister_isRegistered() {
        Team team = minecraftMock.getMinecraft().newTeam("red");
        Result<TeamGameSession> result = worldTeamGame.run(world1).onFailure(() -> fail());

        assertTrue(result.getEntity().isPresent());

        TeamGameSession session = result.getEntity().get();
        session.registerTeam(team)
                .onFailure(() -> fail());
        session.registerTeam(team)
                .onSuccess(() -> fail());
        Optional<Team> teamOptional = session.getTeam("red");

        assertTrue(teamOptional.isPresent());
        assertEquals("red", teamOptional.get().getId());
    }

    @Test
    void unregisterTeam_shouldUnregister_isUnregistered() {
        Team team = minecraftMock.getMinecraft().newTeam("red");
        Result<TeamGameSession> result = worldTeamGame.run(world1).onFailure(() -> fail());

        assertTrue(result.getEntity().isPresent());

        TeamGameSession session = result.getEntity().get();
        session.unregisterTeam(team)
                .onSuccess(() -> fail());
        session.registerTeam(team);
        session.unregisterTeam(team)
                .onFailure(() -> fail());
        Optional<Team> teamOptional = session.getTeam("red");

        assertFalse(teamOptional.isPresent());
    }

    @Test
    void toTeamMember_shouldMap_whenHasNoTeam() {
        Result<TeamGameSession> result = worldTeamGame.run(world1).onFailure(() -> fail());

        assertTrue(result.getEntity().isPresent());

        TeamGameSession session = result.getEntity().get();
        session.addPlayer(player);
        TeamMember teamMember = session.toTeamMember(player);
        assertNotNull(teamMember);
        assertFalse(teamMember.getTeam().isPresent());
        assertEquals(player.getUniqueId(), teamMember.getUniqueId());
        assertEquals(player.getName(), teamMember.getName());
    }

    @Test
    void toTeamMember_shouldMap_whenHasTeam() {
        Result<TeamGameSession> result = worldTeamGame.run(world1).onFailure(() -> fail());

        assertTrue(result.getEntity().isPresent());

        TeamGameSession session = result.getEntity().get();
        Team team = minecraftMock.getMinecraft().newTeam("red");

        session.registerTeam(team);
        session.addPlayer(player);

        team.addMember(player);
        TeamMember teamMember = session.toTeamMember(player);

        assertNotNull(teamMember);
        assertTrue(teamMember.getTeam().isPresent());
        assertEquals(player.getUniqueId(), teamMember.getUniqueId());
        assertEquals(player.getName(), teamMember.getName());
    }
}
