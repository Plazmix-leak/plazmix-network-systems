package net.plazmix.network.user;

import java.util.Date;

public interface AuthenticatedUser extends User {

    Date getRegistrationDate();

    Date getLastLoginDate();
}
