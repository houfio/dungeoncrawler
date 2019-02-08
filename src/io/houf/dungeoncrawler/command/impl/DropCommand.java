package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.Validator;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;

public class DropCommand implements Command {
    private final Argument<String> item = new Argument<>("item", "The item to drop to the room", true, Validator.STRING_VALIDATOR);

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
        var name = arguments.get(this.item);
        var item = player.getItem(name);

        if (item == null) {
            return new LogUI.RawLogLine("You couldn't find that item in your backpack.", Color.RED);
        } else if (!item.drop()) {
            return new LogUI.RawLogLine("You decided not to drop your " + item.name + ".", Color.ORANGE);
        }

        player.items.remove(item);
        room.addEntity(game, new ItemEntity(item, 114, 109, (float) Math.random() * 100.0f - 50.0f, (float) Math.random() * 100.0f - 50.0f));

        return new LogUI.RawLogLine("You dropped the " + name + " on the floor.", Color.PINK);
    }
}
