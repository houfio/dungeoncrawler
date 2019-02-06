package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.Validator;
import io.houf.dungeoncrawler.entity.ItemEntity;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;

public class DropCommand implements Command {
    private static final Argument<String> ITEM = new Argument<>("item", "The item to drop to the room", true, Validator.STRING_VALIDATOR);

    @Override
    public String getName() {
        return "drop";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[] {
            DropCommand.ITEM
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var room = game.getCurrent().currentRoom();
        var name = arguments.get(DropCommand.ITEM);
        var item = Item.getItem(name, room.player.items);

        if (item == null) {
            return new LogUI.RawLogLine("You don't have that item in your backpack", Color.ORANGE);
        }

        room.player.items.remove(item);

        var entity = new ItemEntity(item, 114, 109);
        entity.setVelocityX((float) Math.random() * 100.0f - 50.0f);
        entity.setVelocityY((float) Math.random() * 100.0f - 50.0f);

        room.addEntity(game, entity);

        return new LogUI.RawLogLine("You dropped the " + name + " on the floor", Color.PINK);
    }
}
