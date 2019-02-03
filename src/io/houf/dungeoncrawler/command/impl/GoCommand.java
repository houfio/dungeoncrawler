package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.OptionValidator;
import io.houf.dungeoncrawler.ui.impl.LogUI;
import io.houf.dungeoncrawler.game.Side;

import java.awt.*;

public class GoCommand implements Command {
    private static final Argument<String> SIDE = new Argument<>("side", "Side to move the character to", true, new OptionValidator(Side.getSides()));

    @Override
    public String getName() {
        return "go";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[] {
            GoCommand.SIDE
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        return new LogUI.RawLogLine("no u", Color.YELLOW);
    }
}
