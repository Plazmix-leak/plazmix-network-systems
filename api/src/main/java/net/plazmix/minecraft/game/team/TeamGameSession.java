package net.plazmix.minecraft.game.team;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;

public interface TeamGameSession extends GameSession {

    Collection<Team> getRegisteredTeams();

    Result<Team> registerTeam(Team team);

    Result<Team> unregisterTeam(Team team);

    Optional<Team> getTeam(String id);

    TeamMember toTeamMember(GamePlayer gamePlayer);
}
