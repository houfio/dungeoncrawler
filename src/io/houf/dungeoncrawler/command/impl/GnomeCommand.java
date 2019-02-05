package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.entity.GnomeEntity;
import io.houf.dungeoncrawler.ui.impl.LogUI;

public class GnomeCommand implements Command {
    @Override
    public String getName() {
        return "gnome";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[0];
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        var entity = new GnomeEntity(114, 109);
        entity.velocityX = (float) (Math.random() * 1000 - 500);
        entity.velocityY = (float) (Math.random() * 1000 - 500);

        game.ingame.currentRoom().addEntity(entity);

        return null;
    }
}
