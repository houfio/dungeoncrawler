package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.BananaItem;
import io.houf.dungeoncrawler.item.StickItem;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;

public class GnomeEntity extends Entity {
    public GnomeEntity(int x, int y) {
        super(new Sprite("gnome", 22, 3), x, y, 22, 32);
    }

    @Override
    public void initialize(Game game) {
        game.getLogger().printLine("I'm not a gnelf, I'm not a gnoblin, I'm a gnome!", new Color(0, 190, 255));
    }

    @Override
    public boolean hostile() {
        return true;
    }

    @Override
    public Drop[] drops(Game game) {
        return new Drop[] {
            new Drop(new StickItem(), .25d),
            new Drop(new BananaItem(), .25d)
        };
    }
}
