package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.OptionValidator;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.room.Side;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;

public class GoCommand implements Command {
    private static final Argument<String> SIDE = new Argument<>("side", "Side to move the character to", true, new OptionValidator(Side.getSides()));

    @Override
    public String getName() {
        return "go";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            GoCommand.SIDE
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var side = arguments.get(GoCommand.SIDE);
        var hasHostile = game.getCurrent().currentRoom().entities.stream().anyMatch(Entity::hostile);

        if (hasHostile) {
            return new LogUI.RawLogLine("There are monsters in the room and they block your way.", Color.ORANGE);
        } else if (!game.getCurrent().move(Side.valueOf(side.toUpperCase()))) {
            return new LogUI.RawLogLine("You could't walk to the " + side + ".", Color.ORANGE);
        }

        return new LogUI.RawLogLine("You walked to the " + side + ".", Color.YELLOW);
    }
}
