package net.plazmix.minecraft.tag;

import net.plazmix.network.user.User;
import net.plazmix.util.Result;

public interface GroupTag extends Tag {

    String getGroupName();

    Result<User> addUser(User user);

    Result<User> removeUser(User user);
}
