package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class PeelItem extends Item {
    public PeelItem() {
        super("peel");
    }

    @Override
    public Item onUse(Game game) {
        // Apparently it's not unhealthy, but still don't do it!
        game.getLogger().printLine("You decided not to eat the banana peel.", Color.ORANGE);

        return this;
    }
}
