package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.Validator;
import io.houf.dungeoncrawler.entity.PlayerEntity;
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
        var entity = new PlayerEntity();
        entity.velocityX = (float) (Math.random() * 100 - 50);
        entity.velocityY = (float) (Math.random() * 100 - 50);

        game.ingame.currentRoom().addEntity(entity);

        return new LogUI.RawLogLine("Yeet", Color.PINK);
    }
}
