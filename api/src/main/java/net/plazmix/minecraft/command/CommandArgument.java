package net.plazmix.minecraft.command;

import java.util.Collection;

public interface CommandArgument extends ArgumentInfo {

    Collection<CommandArgument> getChilds();
}