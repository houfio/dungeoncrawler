package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.LogLine;
import io.houf.dungeoncrawler.validator.impl.BackpackValidator;

import java.awt.*;

public class ItemCommand implements Command {
    private final Argument<Item> item = new Argument<>("item", "The item to get the description for", true, new BackpackValidator());

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            this.item
        };
    }

    @Override
    public LogLine execute(Game game, ArgumentMap arguments) {
        var item = arguments.get(this.item);

        // Print the item description
        return new LogLine(item.description, Color.MAGENTA);
    }
}
