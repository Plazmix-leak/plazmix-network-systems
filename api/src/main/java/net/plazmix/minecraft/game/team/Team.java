package net.plazmix.minecraft.game.team;

import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;

public interface Team {

    String getId();

    GameCache getCache();

    Collection<TeamMember> getMembers();

    Optional<TeamMember> getMember(GamePlayer gamePlayer);

    Result<TeamMember> addMember(GamePlayer gamePlayer);

    Result<Void> removeMember(GamePlayer gamePlayer);
}
