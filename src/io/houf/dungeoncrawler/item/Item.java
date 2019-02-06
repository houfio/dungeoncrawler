package io.houf.dungeoncrawler.item;

import io.houf.dungeoncrawler.Asset;
import io.houf.dungeoncrawler.Game;

import java.awt.image.BufferedImage;
import java.util.List;

public class Item {
    public final String name;
    public final BufferedImage image;

    public Item(String name) {
        this.name = name;
        this.image = Asset.read("item/" + this.name);
    }

    public Item onUse(Game game) {
        return this;
    }

    public static Item getItem(String name, List<Item> items) {
        return items.stream()
            .filter(i -> i != null && i.name.equals(name))
            .findFirst()
            .orElse(null);
    }
}
