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
import java.util.stream.IntStream;

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
        var room = game.ingame.currentRoom();
        var player = room.player;
        var free = IntStream.range(0, 3).filter(i -> player.items[i] == null).findFirst().orElse(-1);

        if (free == -1) {
            return new LogUI.RawLogLine("Your backpack is full", Color.ORANGE);
        }

        var name = arguments.get(GetCommand.ITEM);

        if (Item.getItem(name, player.items) != null) {
            return new LogUI.RawLogLine("You already have that item", Color.ORANGE);
        }

        var entity = room.entities.stream()
            .filter(i -> i instanceof ItemEntity && ((ItemEntity) i).item.name.equals(name))
            .findFirst()
            .orElse(null);

        if (entity == null) {
            return new LogUI.RawLogLine("You can't find that item anywhere", Color.ORANGE);
        }

        entity.dead = true;
        player.items[free] = ((ItemEntity) entity).item;

        return new LogUI.RawLogLine("You picked up the item form the room", Color.GREEN);
    }
}
