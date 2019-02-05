package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;

public class GnomeEntity extends Entity {
    public GnomeEntity(int x, int y) {
        super(new Sprite("gnome", 22, 3), x, y, 22, 32);
    }

    @Override
    public void initialize(Game game) {
        game.logger.printLine("I'm not a gnelf, I'm not a gnoblin, I'm a gnome!", new Color(0, 190, 255));
    }
}
