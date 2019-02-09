package io.houf.dungeoncrawler.item;

import io.houf.dungeoncrawler.Asset;
import io.houf.dungeoncrawler.Game;

import java.awt.image.BufferedImage;

public class Item {
    public final String name;
    public final BufferedImage image;

    public Item(String name) {
        this.name = name;
        // Read the asset but let somebody else render it
        this.image = Asset.read("item/" + this.name);
    }

    public Item onUse(Game game) {
        return this;
    }

    public boolean drop() {
        return true;
    }
}
