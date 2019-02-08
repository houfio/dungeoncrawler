package io.houf.dungeoncrawler.validator.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.CommandHandler;
import io.houf.dungeoncrawler.validator.Validator;

import java.util.Arrays;

public class CommandValidator extends Validator<Command> {
    public CommandValidator() {
        super("command");
    }

    @Override
    public Command parse(Game game, String input) {
        return Arrays.stream(CommandHandler.COMMANDS)
            .filter(command -> command.getName().equals(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getSuggested(Game game, String input) {
        return Arrays.stream(CommandHandler.COMMANDS)
            .map(Command::getName)
            .filter(name -> name.startsWith(input))
            .findFirst()
            .orElse(null);
    }
}
