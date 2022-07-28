package net.plazmix.minecraft.game.team;

import net.plazmix.minecraft.game.player.GamePlayer;

import java.util.Optional;

public interface TeamMember extends GamePlayer {

    Optional<Team> getTeam();


}
