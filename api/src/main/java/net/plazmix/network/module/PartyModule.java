package net.plazmix.network.module;

import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartyModule extends NetworkModule {

    Result<Party> createParty(User user);

    Optional<Party> getParty(UUID uuid);

    Optional<Party> getCurrentParty(User user);

    Result<Void> disband(Party party);

    Result<Void> doInvite(User user, Party party);

    Result<Void> undoInvite(User user, Party party);

    Result<Void> doAccept(User user, Party party);

    Result<Void> doReject(User user, Party party);

    interface Party {

        UUID getUniqueId();

        User getLeader();

        Collection<User> getAllMembers();

        Collection<User> getInvitedMembers();

        List<User> getInvitedList();

        Result<Void> addMember(User user);

        Result<Void> removeMember(User user);
    }
}
