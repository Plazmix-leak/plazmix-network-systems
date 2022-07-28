package net.plazmix.minecraft.tag;

import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Optional;

public interface TagFactory {

    PersonalTag createPersonalTag(User user);

    GroupTag createGroupTag(String groupName);

    Optional<PersonalTag> getPersonalTag(User user);

    Optional<GroupTag> getGroupTag(String groupName);

    Result<User> removePersonalTag(User user);

    Result<String> removeGroupTag(String groupName);

    boolean hasTag(Tag tag);
}
