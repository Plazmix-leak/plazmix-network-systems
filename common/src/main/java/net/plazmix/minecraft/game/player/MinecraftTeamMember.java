package net.plazmix.minecraft.game.player;

import lombok.Data;
import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamMember;
import net.plazmix.network.server.Server;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Data
public class MinecraftTeamMember implements TeamMember {

    private final GamePlayer gamePlayer;

    private Team team;

    public MinecraftTeamMember(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @Override
    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public Optional<GameSession> getCurrentGame() {
        return gamePlayer.getCurrentGame();
    }

    @Override
    public GameCache getCache() {
        return gamePlayer.getCache();
    }

    @Override
    public UUID getUniqueId() {
        return gamePlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return gamePlayer.getName();
    }

    @Override
    public InetAddress getAddress() {
        return gamePlayer.getAddress();
    }

    @Override
    public Instant getCreationTime() {
        return gamePlayer.getCreationTime();
    }

    @Override
    public Instant getLifetime() {
        return gamePlayer.getLifetime();
    }

    @Override
    public boolean isAlive() {
        return gamePlayer.isAlive();
    }

    @Override
    public Server getServer() {
        return gamePlayer.getServer();
    }

    @Override
    public boolean isOnline() {
        return gamePlayer.isOnline();
    }

    @Override
    public Locale getLocale() {
        return gamePlayer.getLocale();
    }

    @Override
    public boolean isPlaying() {
        return gamePlayer.isPlaying();
    }
}
