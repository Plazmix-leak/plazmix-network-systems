package net.plazmix.minecraft.tag;

import net.plazmix.network.user.User;
import net.plazmix.util.Result;

public interface Tag {

    String getDisplayName();

    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    boolean isAutoRecieved();

    void setAutoRecieved(boolean value);

    Result<User> addRecieverManually(User user);

    Result<User> removeRecieverManually(User user);
}
