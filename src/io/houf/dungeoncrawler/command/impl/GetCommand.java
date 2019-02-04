package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.Validator;
import io.houf.dungeoncrawler.ui.impl.LogUI;

public class GetCommand implements Command {
    private static final Argument<String> ITEM = new Argument<>("item", "The item to get from the room", true, Validator.STRING_VALIDATOR);

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[] {
            GetCommand.ITEM
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        throw new RuntimeException("no u");
    }
}
