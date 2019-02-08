package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.room.Side;
import io.houf.dungeoncrawler.ui.LogLine;
import io.houf.dungeoncrawler.validator.impl.SideValidator;

import java.awt.*;

public class GoCommand implements Command {
    private final Argument<Side> side = new Argument<>("side", "Side to move the character to", true, new SideValidator());

    @Override
    public String getName() {
        return "go";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            this.side
        };
    }

    @Override
    public LogLine execute(Game game, ArgumentMap arguments) {
        var side = arguments.get(this.side);
        var name = side.name().toLowerCase();
        var hasHostile = game.getCurrent().currentRoom().entities.stream().anyMatch(Entity::hostile);

        if (hasHostile) {
            return new LogLine("There are monsters in the room that block your way.", Color.RED);
        } else if (!game.getCurrent().move(side)) {
            return new LogLine("You could't walk to the " + name + ".", Color.RED);
        }

        return new LogLine("You walked to the " + name + ".", Color.PINK);
    }
}
