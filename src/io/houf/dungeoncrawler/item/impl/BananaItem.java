package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class BananaItem extends Item {
    public BananaItem() {
        super("banana");
    }

    public Item onUse(Game game) {
        game.getLogger().printLine("You peeled the banana", Color.YELLOW);

        return new PeelItem();
    }
}
