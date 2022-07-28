package net.plazmix.minecraft.command;

public interface CommandBuilder
        extends CommandElementBuilder<CommandBuilder, Command> {

    CommandBuilder addArgument(CommandArgumentBuilder argumentBuilder);
}