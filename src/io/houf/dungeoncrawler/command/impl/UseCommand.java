package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.Validator;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;
import java.util.Arrays;

public class UseCommand implements Command {
    private static final Argument<String> ITEM = new Argument<>("item", "The item to use", true, Validator.STRING_VALIDATOR);

    @Override
    public String getName() {
        return "use";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[] {
            UseCommand.ITEM
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var player = game.ingame.currentRoom().player;
        var item = Item.getItem(arguments.get(UseCommand.ITEM), player.items);

        if (item == null) {
            return new LogUI.RawLogLine("You don't have that item", Color.ORANGE);
        }

        var index = Arrays.asList(player.items).indexOf(item);

        player.items[index] = item.onUse(game);

        return null;
    }
}
