package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;
import java.util.stream.Collectors;

public class PackCommand implements Command {
    @Override
    public String getName() {
        return "pack";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[0];
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var items = game.getCurrent().player.items
            .stream()
            .map(i -> i.name)
            .collect(Collectors.toList());

        if (items.size() == 0) {
            return new LogUI.RawLogLine("Your backpack is completely empty.", Color.RED);
        }

        return new LogUI.RawLogLine("You have the following items in your backpack: " + String.join(", ", items) + ".", Color.PINK);
    }
}
