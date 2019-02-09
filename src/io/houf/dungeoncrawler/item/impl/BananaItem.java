package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class BananaItem extends Item {
    public BananaItem() {
        super("banana");
    }

    @Override
    public Item onUse(Game game) {
        // I had to come up with something...
        game.getLogger().printLine("You peeled the banana", Color.YELLOW);
        game.getAudio().play("use");

        return new PeelItem();
    }
}
