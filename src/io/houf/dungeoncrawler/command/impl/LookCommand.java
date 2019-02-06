package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.ItemEntity;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;
import java.util.stream.Collectors;

public class LookCommand implements Command {
    @Override
    public String getName() {
        return "look";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[0];
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var current = game.getCurrent();
        var items = current.currentRoom().entities.stream()
            .filter(e -> e instanceof ItemEntity)
            .map(e -> ((ItemEntity) e).item.name)
            .distinct()
            .collect(Collectors.toList());
        var doors = current.getDoors()
            .stream()
            .map(d -> d.name().toLowerCase())
            .collect(Collectors.toList());

        var d = "You see the following doors in the room: " + String.join(", ", doors) + ".";

        var i = items.size() == 0
            ? "You couldn't find any items on the floor."
            : "You found the following items: " + String.join(", ", items) + ".";

        return new LogUI.RawLogLine(d + " " + i, Color.GREEN);
    }
}
