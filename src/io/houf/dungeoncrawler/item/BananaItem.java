package io.houf.dungeoncrawler.item;

import io.houf.dungeoncrawler.Game;

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
