package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.item.impl.GunItem;
import io.houf.dungeoncrawler.ui.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends Entity {
    public final List<Item> items;

    public PlayerEntity() {
        super(new Sprite("player", 22, 3), 114, 109, 22, 32);

        this.items = new ArrayList<>() {{
            add(new GunItem());
        }};
    }

    @Override
    public int priority() {
        return 2;
    }
}
