package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.item.impl.BananaItem;
import io.houf.dungeoncrawler.item.impl.StickItem;
import io.houf.dungeoncrawler.ui.Sprite;

import java.awt.*;

public class GnomeEntity extends Entity {
    public GnomeEntity(float x, float y) {
        super(new Sprite("entity/gnome", 22, 3), x, y, 22, 32);
    }

    @Override
    public void initialize(Game game) {
        game.getLogger().printLine("I'm not a gnelf, I'm not a gnoblin, I'm a gnome!", Color.CYAN);
    }

    @Override
    public boolean hostile() {
        return true;
    }

    @Override
    public Drop[] drops(Game game) {
        return new Drop[]{
            new Drop(new StickItem(), .25d),
            new Drop(new BananaItem(), .25d)
        };
    }
}
