package io.houf.dungeoncrawler.entity.impl;

import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.item.impl.GunItem;
import io.houf.dungeoncrawler.ui.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends Entity {
    public final List<Item> backpack;

    public PlayerEntity() {
        super(new Sprite("entity/player", 22, 3), 114, 109, 22, 32);

        // Initialize backpack with a gun
        this.backpack = new ArrayList<>() {{
            add(new GunItem());
        }};
    }

    @Override
    public int priority() {
        // Render above all other entities
        return 2;
    }

    public boolean hasItem(Item item) {
        // Check if player has specified item in backpack
        return this.backpack.stream()
            .anyMatch(i -> i.name.equals(item.name));
    }
}
