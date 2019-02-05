package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.item.BananaItem;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.Sprite;

public class PlayerEntity extends Entity {
    public final Item[] items;

    public PlayerEntity() {
        super(new Sprite("player", 22, 5), 114, 109, 22, 32);

        this.items = new Item[4];
        this.items[0] = new BananaItem();
    }
}
