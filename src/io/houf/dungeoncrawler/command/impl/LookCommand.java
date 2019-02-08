package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.impl.GateEntity;
import io.houf.dungeoncrawler.entity.impl.HoleEntity;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
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
        var doors = current.getDoors()
            .stream()
            .map(d -> d.name().toLowerCase())
            .collect(Collectors.toList());
        var items = current.currentRoom().entities.stream()
            .filter(e -> e instanceof ItemEntity)
            .map(e -> ((ItemEntity) e).item.name)
            .distinct()
            .collect(Collectors.toList());
        var hostile = current.currentRoom().entities.stream()
            .filter(Entity::hostile)
            .count();
        var gate = current.currentRoom().entities.stream()
            .anyMatch(e -> e instanceof GateEntity);
        var hole = current.currentRoom().entities.stream()
            .anyMatch(e -> e instanceof HoleEntity);

        var doorMessage = "You see the following doors in the room: " + String.join(", ", doors) + ". ";
        var itemMessage = items.size() > 0 ? "You found the following items on the floor: " + String.join(", ", items) + ". " : "";
        var hostileMessage = hostile > 0 ? "There are " + hostile + " monsters in the room. " : "";
        var gateMessage = gate ? "Also, there seems to be a some kind of gate in the room. " : "";
        var holeMessage = hole ? "There's a huge hole in the ceiling. If only you could get to it... " : "";

        return new LogUI.RawLogLine(doorMessage + itemMessage + hostileMessage + gateMessage + holeMessage, Color.GREEN);
    }
}
