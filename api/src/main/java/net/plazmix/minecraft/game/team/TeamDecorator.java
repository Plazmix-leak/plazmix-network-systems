package net.plazmix.minecraft.game.team;

import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;

public class TeamDecorator implements Team {

    protected final Team team;

    protected TeamDecorator(Team team) {
        this.team = team;
    }

    @Override
    public String getId() {
        return team.getId();
    }

    @Override
    public GameCache getCache() {
        return team.getCache();
    }

    @Override
    public Collection<TeamMember> getMembers() {
        return team.getMembers();
    }

    @Override
    public Optional<TeamMember> getMember(GamePlayer gamePlayer) {
        return team.getMember(gamePlayer);
    }

    @Override
    public Result<TeamMember> addMember(GamePlayer gamePlayer) {
        return team.addMember(gamePlayer);
    }

    @Override
    public Result<Void> removeMember(GamePlayer gamePlayer) {
        return team.removeMember(gamePlayer);
    }
}
