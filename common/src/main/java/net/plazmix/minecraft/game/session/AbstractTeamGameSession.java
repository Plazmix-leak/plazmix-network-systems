package net.plazmix.minecraft.game.session;

import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.MinecraftTeamMember;
import net.plazmix.minecraft.game.team.GameTeam;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamGameSession;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractTeamGameSession implements TeamGameSession {

    protected final Map<String, Team> registeredTeams = Maps.newLinkedHashMap();
    protected final Map<GamePlayer, MinecraftTeamMember> teamMemberMap = Maps.newHashMap();
    private final AtomicBoolean valid = new AtomicBoolean(false);

    @Override
    public boolean isActive() {
        return valid.get();
    }

    @Override
    public Result<Void> setActive(boolean active) {
        if (!active)
            getCache().clear();
        return new Result<>(valid.getAndSet(active) != active ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Collection<Team> getRegisteredTeams() {
        return registeredTeams.values();
    }

    @Override
    public Result<Team> registerTeam(Team team) {
        Result<Team> result = new Result<>(registeredTeams.putIfAbsent(team.getId(), team) == null ? Result.Status.SUCCESS : Result.Status.FAILURE);
        result.onSuccess(() -> ((GameTeam) team).setSession(this));
        return result;
    }

    @Override
    public Result<Team> unregisterTeam(Team team) {
        Result<Team> result = new Result<>(registeredTeams.remove(team.getId()) != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
        result.onSuccess(() -> ((GameTeam) team).setSession(null));
        return result;
    }

    @Override
    public Optional<Team> getTeam(String id) {
        return Optional.ofNullable(registeredTeams.get(id));
    }

    @Override
    public MinecraftTeamMember toTeamMember(GamePlayer gamePlayer) {
        return teamMemberMap.get(gamePlayer);
    }
}
