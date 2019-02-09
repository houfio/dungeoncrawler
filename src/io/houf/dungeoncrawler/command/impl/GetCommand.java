package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.ui.LogLine;
import io.houf.dungeoncrawler.validator.impl.FloorValidator;

import java.awt.*;

public class GetCommand implements Command {
    private final Argument<ItemEntity> item = new Argument<>("item", "The item to get from the room", true, new FloorValidator());

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            this.item
        };
    }

    @Override
    public LogLine execute(Game game, ArgumentMap arguments) {
        var player = game.getCurrent().player;

        if (player.backpack.size() >= 4) {
            // For some reason I decided to limit the backpack at 4 items
            return new LogLine("Your backpack is completely full.", Color.RED);
        }

        var entity = arguments.get(this.item);

        if (player.hasItem(entity.item)) {
            // And you also can't have more than one of the same item...
            return new LogLine("You already have that item in your backpack.", Color.RED);
        }

        // Remove the item from the room
        entity.setDead();
        // And add it to the player's backpack
        player.backpack.add(entity.item);

        game.getAudio().play("get");

        return new LogLine("You picked up the " + entity.item.name + " from the floor.", Color.PINK);
    }
}
