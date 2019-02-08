package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.item.Item;

public class Drop {
    public final Item item;
    public final double chance;

    public Drop(Item item, double chance) {
        this.item = item;
        this.chance = chance;
    }
}
