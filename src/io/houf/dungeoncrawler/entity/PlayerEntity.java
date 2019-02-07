package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.item.GunItem;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.ui.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends Entity {
    public final List<Item> items;

    public PlayerEntity() {
        super(new Sprite("player", 22, 5), 114, 109, 22, 32);

        this.items = new ArrayList<>() {{
            add(new GunItem());
        }};
    }

    @Override
    public int priority() {
        return 1;
    }
}
