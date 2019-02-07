package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class StickItem extends Item {
    public StickItem() {
        super("stick");
    }

    @Override
    public Item onUse(Game game) {
        game.getLogger().printLine("You swing the stick in the wild. It breaks.", Color.MAGENTA);

        return null;
    }
}
