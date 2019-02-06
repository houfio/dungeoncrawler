package io.houf.dungeoncrawler.entity;

import io.houf.dungeoncrawler.item.*;
import io.houf.dungeoncrawler.ui.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends Entity {
    public final List<Item> items;

    public PlayerEntity() {
        super(new Sprite("player", 22, 5), 114, 109, 22, 32);

        this.items = new ArrayList<>() {{
            add(new BananaItem());
            add(new StickItem());
            add(new KeyItem());
            add(new GunItem());
        }};
    }
}
