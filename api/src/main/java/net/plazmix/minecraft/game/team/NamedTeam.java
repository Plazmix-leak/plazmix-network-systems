package net.plazmix.minecraft.game.team;

public class NamedTeam extends TeamDecorator {

    public static final String TEAM_NAME = "TEAM_NAME";

    public NamedTeam(Team team) {
        super(team);
    }

    public String getName() {
        return team.getCache().getString(TEAM_NAME);
    }

    public void setName(String name) {
        team.getCache().set(TEAM_NAME, name);
    }
}
