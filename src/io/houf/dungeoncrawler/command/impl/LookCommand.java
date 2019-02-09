package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.impl.GateEntity;
import io.houf.dungeoncrawler.entity.impl.HoleEntity;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.ui.LogLine;

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
    public LogLine execute(Game game, ArgumentMap arguments) {
        var current = game.getCurrent();
        // Get all the doors in the room
        var doors = current.currentRoom().getDoors()
            .stream()
            .map(d -> d.name().toLowerCase())
            .collect(Collectors.toList());
        // Find all item entities in the room, distinct because the player doesn't care about two of the same item
        var items = current.currentRoom().entities.stream()
            .filter(e -> e instanceof ItemEntity)
            .map(e -> ((ItemEntity) e).item.name)
            .distinct()
            .collect(Collectors.toList());
        // Check how many hostile monsters are in the room
        var hostile = current.currentRoom().entities.stream()
            .filter(Entity::hostile)
            .count();

        var doorMessage = "You see the following doors: " + String.join(", ", doors) + ". ";
        var itemMessage = items.size() > 0 ? "You found some items on the floor: " + String.join(", ", items) + ". " : "";
        var hostileMessage = hostile > 0 ? "There are " + hostile + " monster(s) blocking your way. " : "";

        return new LogLine(current.currentRoom().description + " " + doorMessage + itemMessage + hostileMessage, Color.GREEN);
    }
}
