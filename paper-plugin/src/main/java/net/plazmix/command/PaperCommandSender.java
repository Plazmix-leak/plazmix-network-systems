package net.plazmix.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.plazmix.minecraft.command.CommandSender;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PaperCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    public static CommandSender of(org.bukkit.command.CommandSender sender) {
        return new PaperCommandSender(sender);
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public <T> Optional<T> resolveAs(Class<T> clazz) {
        try {
            if (clazz.isAssignableFrom(sender.getClass())) {
                T type = (T) sender;
                return Optional.of(type);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
