package net.plazmix.minecraft.command;

public interface CommandArgumentBuilder
        extends CommandElementBuilder<CommandArgumentBuilder, CommandArgument> {

    CommandArgumentBuilder addChild(CommandArgumentBuilder child);
}