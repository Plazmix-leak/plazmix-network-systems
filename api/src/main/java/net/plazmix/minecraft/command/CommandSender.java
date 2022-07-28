package net.plazmix.minecraft.command;

import java.util.Optional;

public interface CommandSender {

    String getName();

    boolean hasPermission(String permission);

    void sendMessage(String message);

    <T> Optional<T> resolveAs(Class<T> clazz);
}
