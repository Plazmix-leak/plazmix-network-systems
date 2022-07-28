package net.plazmix.minecraft.command;

public interface Command extends CommandElement {

    String getCommandHolder();

    void register();
}