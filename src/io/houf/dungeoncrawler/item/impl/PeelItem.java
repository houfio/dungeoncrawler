package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class PeelItem extends Item {
    public PeelItem() {
        super("peel", "You recently found out that eating banana peels isn't unhealthy.");
    }

    @Override
    public Item onUse(Game game) {
        game.getLogger().printLine("You decided not to eat the banana peel.", Color.ORANGE);

        return this;
    }
}
