package io.houf.dungeoncrawler.item;

import io.houf.dungeoncrawler.Game;

import java.awt.*;

public class StickItem extends Item {
    public StickItem() {
        super("stick");
    }

    public Item onUse(Game game) {
        game.logger.printLine("You swing the stick in the wild. It breaks.", Color.MAGENTA);

        return null;
    }
}
