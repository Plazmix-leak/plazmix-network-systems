package net.plazmix.minecraft.game.session;

import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.MinecraftTeamMember;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class MinecraftServerTeamGameSession extends AbstractTeamGameSession {

    private final ServerTeamGame serverGame;
    private final String sessionId;

    public MinecraftServerTeamGameSession(ServerTeamGame serverGame, String sessionId) {
        this.serverGame = serverGame;
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public GameStateController getStateController() {
        return serverGame.getStateController();
    }

    @Override
    public GameCache getCache() {
        return serverGame.getCache();
    }

    @Override
    public Collection<GamePlayer> getPlayers() {
        return serverGame.getPlayers();
    }

    @Override
    public Optional<GamePlayer> getPlayerById(UUID uuid) {
        return serverGame.getPlayerById(uuid);
    }

    @Override
    public Optional<GamePlayer> getPlayerByName(String name) {
        return serverGame.getPlayerByName(name);
    }

    @Override
    public Result<Void> addPlayer(GamePlayer gamePlayer) {
        return new Result<>(teamMemberMap.putIfAbsent(gamePlayer, new MinecraftTeamMember(gamePlayer)) == null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Result<Void> removePlayer(GamePlayer gamePlayer) {
        if (teamMemberMap.containsKey(gamePlayer))
            teamMemberMap.get(gamePlayer).getTeam().ifPresent(team -> team.removeMember(gamePlayer));
        return new Result<>(teamMemberMap.remove(gamePlayer) != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }
}
