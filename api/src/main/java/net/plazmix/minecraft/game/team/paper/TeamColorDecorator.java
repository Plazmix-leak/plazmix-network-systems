package net.plazmix.minecraft.game.team.paper;

import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamDecorator;
import org.bukkit.ChatColor;

public class TeamColorDecorator extends TeamDecorator {

    public static final String TEAM_COLOR = "PAPER_TEAM_COLOR";

    public TeamColorDecorator(Team team) {
        super(team);
    }

    public ChatColor getColor() {
        return team.getCache().get(TEAM_COLOR, ChatColor.class);
    }

    public void setColor(ChatColor chatColor) {
        team.getCache().set(TEAM_COLOR, chatColor);
    }
}
