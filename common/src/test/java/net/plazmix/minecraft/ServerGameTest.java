package net.plazmix.minecraft;

import net.plazmix.minecraft.game.logic.builder.MinecraftGameLogicBuilder;
import net.plazmix.minecraft.game.mode.ServerGame;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamMember;
import net.plazmix.minecraft.player.KeyGamePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerGameTest {

    private final MinecraftMock minecraftMock = new MinecraftMock();
    private final String KEY = "KEY", VALUE = "VALUE";
    private final UUID uuid = UUID.nameUUIDFromBytes("test".getBytes(StandardCharsets.UTF_8));
    private ServerGame serverGame;
    private ServerTeamGame serverTeamGame;
    private GamePlayer player;

    @BeforeEach
    void setup() {
        this.serverGame = minecraftMock.getMinecraft().newGameBuilder(ServerGame.class)
                .withName("ServerGame")
                .addState(minecraftMock.getMinecraft().newGameStateBuilder()
                        .withName("state")
                        .withLogic(new MinecraftGameLogicBuilder<>()
                                .allowPlayerAdd()
                                .build())
                        .build())
                .build();
        this.serverTeamGame = minecraftMock.getMinecraft().newGameBuilder(ServerTeamGame.class)
                .withName("ServerTeamGame")
                .addState(minecraftMock.getMinecraft().newGameStateBuilder()
                        .withName("state")
                        .withLogic(new MinecraftGameLogicBuilder<>()
                                .allowPlayerAdd()
                                .build())
                        .build())
                .build();

        GamePlayer original = mock(GamePlayer.class);
        when(original.getName()).thenReturn("TestPlayer");
        when(original.getUniqueId()).thenReturn(uuid);
        this.player = new KeyGamePlayer(original);
    }

    @Test
    void addPlayer_shouldFindById_exists() {
        serverGame.run();
        serverGame.addPlayer(player)
                .onFailure(() -> fail());
        serverGame.addPlayer(player)
                .onSuccess(() -> fail());
        Optional<GamePlayer> playerOptional = serverGame.getPlayerById(uuid);

        assertTrue(playerOptional.isPresent());
        assertEquals("TestPlayer", playerOptional.get().getName());
    }

    @Test
    void addPlayer_shouldFindByName_exists() {
        serverGame.run();
        serverGame.addPlayer(player)
                .onFailure(() -> fail());
        serverGame.addPlayer(player)
                .onSuccess(() -> fail());
        Optional<GamePlayer> playerOptional = serverGame.getPlayerByName("TestPlayer");

        assertTrue(playerOptional.isPresent());
        assertEquals(uuid, playerOptional.get().getUniqueId());
    }

    @Test
    void removePlayer_shouldNotFindById_notFound() {
        serverGame.run();
        serverGame.removePlayer(player)
                .onSuccess(() -> fail());
        serverGame.addPlayer(player);
        serverGame.removePlayer(player)
                .onFailure(() -> fail());
        Optional<GamePlayer> playerOptional = serverGame.getPlayerById(uuid);

        assertFalse(playerOptional.isPresent());
    }

    @Test
    void removePlayer_shouldNotFindByName_notFound() {
        serverGame.run();
        serverGame.removePlayer(player)
                .onSuccess(() -> fail());
        serverGame.addPlayer(player);
        serverGame.removePlayer(player)
                .onFailure(() -> fail());
        Optional<GamePlayer> playerOptional = serverGame.getPlayerByName("TestPlayer");

        assertFalse(playerOptional.isPresent());
    }

    @Test
    void setCache_shouldSet_contains() {
        serverGame.getCache().set(KEY, VALUE);
        assertEquals(VALUE, serverGame.getCache().get(KEY));
    }

    @Test
    void getSession_shouldThrow_whenGameIsNotRunning() {
        assertThrows(IllegalStateException.class, () -> serverGame.getSessionId());
    }

    @Test
    void getSession_shouldExist_whenGameIsRunning() {
        serverGame.run();
        assertNotNull(serverGame.getSessionId());
    }

    @Test
    void run_shouldThrow_whenGameIsAlreadyRunning() {
        serverGame.run();
        assertThrows(IllegalStateException.class, () -> serverGame.run());
    }

    @Test
    void run_shouldRunWithCustomId_isCustomIdValid() {
        serverGame.run("custom");
        assertEquals("custom", serverGame.getSessionId());
    }

    @Test
    void registerTeam_shouldThrow_isNotRunning() {
        Team team = mock(Team.class);
        when(team.getId()).thenReturn("red");
        assertThrows(IllegalStateException.class, () -> serverTeamGame.registerTeam(team));
    }

    @Test
    void registerTeam_shouldRegister_isRegistered() {
        Team team = minecraftMock.getMinecraft().newTeam("red");

        serverTeamGame.run();
        serverTeamGame.registerTeam(team)
                .onFailure(() -> fail());
        serverTeamGame.registerTeam(team)
                .onSuccess(() -> fail());
        Optional<Team> teamOptional = serverTeamGame.getTeam("red");

        assertTrue(teamOptional.isPresent());
        assertEquals("red", teamOptional.get().getId());
    }

    @Test
    void unregisterTeam_shouldUnregister_isUnregistered() {
        Team team = minecraftMock.getMinecraft().newTeam("red");

        serverTeamGame.run();
        serverTeamGame.unregisterTeam(team)
                .onSuccess(() -> fail());
        serverTeamGame.registerTeam(team);
        serverTeamGame.unregisterTeam(team)
                .onFailure(() -> fail());
        Optional<Team> teamOptional = serverTeamGame.getTeam("red");

        assertFalse(teamOptional.isPresent());
    }

    @Test
    void toTeamMember_shouldMap_whenHasNoTeam() {
        serverTeamGame.run();
        serverTeamGame.addPlayer(player);
        TeamMember teamMember = serverTeamGame.toTeamMember(player);
        assertNotNull(teamMember);
        assertFalse(teamMember.getTeam().isPresent());
        assertEquals(player.getUniqueId(), teamMember.getUniqueId());
        assertEquals(player.getName(), teamMember.getName());
    }

    @Test
    void toTeamMember_shouldMap_whenHasTeam() {
        serverTeamGame.run();

        Team team = minecraftMock.getMinecraft().newTeam("red");

        serverTeamGame.registerTeam(team);
        serverTeamGame.addPlayer(player);

        team.addMember(player);
        TeamMember teamMember = serverTeamGame.toTeamMember(player);

        assertNotNull(teamMember);
        assertTrue(teamMember.getTeam().isPresent());
        assertEquals(player.getUniqueId(), teamMember.getUniqueId());
        assertEquals(player.getName(), teamMember.getName());
    }
}
