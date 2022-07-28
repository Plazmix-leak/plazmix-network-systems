package net.plazmix.minecraft.game;

import com.google.common.base.Preconditions;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.session.MinecraftServerTeamGameSession;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamGameSession;
import net.plazmix.minecraft.game.team.TeamMember;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class MinecraftServerTeamGame extends AbstractServerGame<TeamGameSession> implements ServerTeamGame {

    public MinecraftServerTeamGame(String name, GameStateController stateController) {
        super(name, stateController);
    }

    @Override
    public Result run() {
        return run(String.format("%s_session", getName()));
    }

    @Override
    public Result run(Consumer<TeamGameSession> consumer) {
        return run(String.format("%s_session", getName()), consumer);
    }

    @Override
    public Result run(String id) {
        if (getSession() != null && !getSession().isActive())
            setSession(null);
        Preconditions.checkState(getSession() == null, "Game session is already created!");
        MinecraftServerTeamGameSession session = new MinecraftServerTeamGameSession(this, id);
        session.setActive(true);
        setSession(session);
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(getSession() != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Result run(String id, Consumer<TeamGameSession> consumer) {
        if (getSession() != null && !getSession().isActive())
            setSession(null);
        Preconditions.checkState(getSession() == null, "Game session is already created!");
        MinecraftServerTeamGameSession session = new MinecraftServerTeamGameSession(this, id);
        session.setActive(true);
        setSession(session);
        consumer.accept(getSession());
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(getSession() != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    public Collection<Team> getRegisteredTeams() {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return session.getRegisteredTeams();
    }

    public Result<Team> registerTeam(Team team) {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return session.registerTeam(team);
    }

    public Result<Team> unregisterTeam(Team team) {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return session.unregisterTeam(team);
    }

    public Optional<Team> getTeam(String id) {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return session.getTeam(id);
    }

    public TeamMember toTeamMember(GamePlayer gamePlayer) {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return session.toTeamMember(gamePlayer);
    }

    @Override
    public Result<Void> addPlayer(GamePlayer gamePlayer) {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return super.addPlayer(gamePlayer);
    }

    @Override
    public Result<Void> removePlayer(GamePlayer gamePlayer) {
        Preconditions.checkState(session != null, "Session is not initialized yet! Did you run the game?");
        return super.removePlayer(gamePlayer);
    }
}
