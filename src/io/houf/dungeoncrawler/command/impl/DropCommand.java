package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.impl.LogUI;
import io.houf.dungeoncrawler.validator.impl.BackpackValidator;

import java.awt.*;

public class DropCommand implements Command {
    private final Argument<Item> item = new Argument<>("item", "The item to drop to the room", true, new BackpackValidator());

    @Override
    public String getName() {
        return "drop";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            this.item
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var room = game.getCurrent().currentRoom();
        var player = game.getCurrent().player;
        var item = arguments.get(this.item);

        if (!item.drop()) {
            return new LogUI.RawLogLine("You decided not to drop your " + item.name + ".", Color.ORANGE);
        }

        player.items.remove(item);
        room.addEntity(game, new ItemEntity(item, 114, 109, (float) Math.random() * 100.0f - 50.0f, (float) Math.random() * 100.0f - 50.0f));

        return new LogUI.RawLogLine("You dropped the " + item.name + " on the floor.", Color.PINK);
    }
}
