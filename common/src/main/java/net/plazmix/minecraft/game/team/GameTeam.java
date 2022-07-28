package net.plazmix.minecraft.game.team;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.MinecraftGameCache;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.MinecraftTeamMember;
import net.plazmix.minecraft.game.session.AbstractTeamGameSession;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class GameTeam implements Team {

    private final String id;
    private final GameCache cache = new MinecraftGameCache();
    private final Map<GamePlayer, TeamMember> members = Maps.newHashMap();

    private AbstractTeamGameSession session;

    public GameTeam(String id) {
        this.id = id;
    }

    public AbstractTeamGameSession getSession() {
        return session;
    }

    public void setSession(AbstractTeamGameSession session) {
        this.session = session;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public GameCache getCache() {
        return cache;
    }

    @Override
    public Collection<TeamMember> getMembers() {
        return members.values();
    }

    @Override
    public Optional<TeamMember> getMember(GamePlayer gamePlayer) {
        return Optional.ofNullable(members.get(gamePlayer));
    }

    @Override
    public Result<TeamMember> addMember(GamePlayer gamePlayer) {
        Preconditions.checkState(getSession() != null, "Team is not associated with any session!");
        MinecraftTeamMember teamMember = getSession().toTeamMember(gamePlayer);
        if (teamMember == null)
            return new Result<>(Result.Status.FAILURE, "TeamMember is null!");
        Result<TeamMember> result = new Result<>(members.putIfAbsent(gamePlayer, teamMember) == null ? Result.Status.SUCCESS : Result.Status.FAILURE);
        result.onSuccess(() -> teamMember.setTeam(this));
        return result;
    }

    @Override
    public Result<Void> removeMember(GamePlayer gamePlayer) {
        Preconditions.checkState(getSession() != null, "Team is not associated with any session!");
        MinecraftTeamMember teamMember = getSession().toTeamMember(gamePlayer);
        if (teamMember == null)
            return new Result<>(Result.Status.FAILURE, "TeamMember is null!");
        Result<Void> result = new Result<>(members.remove(gamePlayer) != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
        result.onSuccess(() -> teamMember.setTeam(null));
        return result;
    }
}
