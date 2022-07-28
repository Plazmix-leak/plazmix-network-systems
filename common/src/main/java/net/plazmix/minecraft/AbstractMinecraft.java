package net.plazmix.minecraft;

import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.builder.*;
import net.plazmix.minecraft.game.logic.builder.MinecraftGameStateBuilder;
import net.plazmix.minecraft.game.mode.PolygonGame;
import net.plazmix.minecraft.game.mode.ServerGame;
import net.plazmix.minecraft.game.mode.WorldGame;
import net.plazmix.minecraft.game.mode.team.PolygonTeamGame;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;
import net.plazmix.minecraft.game.mode.team.WorldTeamGame;
import net.plazmix.minecraft.game.team.GameTeam;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.lookup.*;
import net.plazmix.minecraft.util.geometry.polygon.*;

public abstract class AbstractMinecraft implements Minecraft {

    public AbstractMinecraft() {
        Intersections.registerIfAbsent(Plot.class, new PlotLookup());
        Intersections.registerIfAbsent(Cuboid.class, new CuboidLookup());
        Intersections.registerIfAbsent(Cube.class, new CubeLookup());
        Intersections.registerIfAbsent(Circle.class, new CircleLookup());
        Intersections.registerIfAbsent(Cylinder.class, new CylinderLookup());
    }

    @Override
    public <T extends Game> GameBuilder<T> newGameBuilder(Class<T> gameClass) {
        if (gameClass.isAssignableFrom(ServerTeamGame.class)) {
            return (GameBuilder<T>) new MinecraftServerTeamGameBuilder();
        } else if (gameClass.isAssignableFrom(ServerGame.class)) {
            return (GameBuilder<T>) new MinecraftServerGameBuilder();
        } else if (gameClass.isAssignableFrom(WorldTeamGame.class)) {
            return (GameBuilder<T>) new MinecraftWorldTeamGameBuilder();
        } else if (gameClass.isAssignableFrom(WorldGame.class)) {
            return (GameBuilder<T>) new MinecraftWorldTeamGameBuilder();
        } else if (gameClass.isAssignableFrom(PolygonTeamGame.class)) {
            return (GameBuilder<T>) new MinecraftPolygonTeamGameBuilder();
        } else if (gameClass.isAssignableFrom(PolygonGame.class)) {
            return (GameBuilder<T>) new MinecraftPolygonGameBuilder();
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public GameStateBuilder newGameStateBuilder() {
        return new MinecraftGameStateBuilder();
    }

    @Override
    public Team newTeam(String id) {
        return new GameTeam(id);
    }
}
