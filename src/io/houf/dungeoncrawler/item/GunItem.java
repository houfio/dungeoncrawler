package io.houf.dungeoncrawler.item;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.Entity;
import io.houf.dungeoncrawler.entity.GnomeEntity;

import java.awt.*;

public class GunItem extends Item {
    public GunItem() {
        super("gun");
    }

    public Item onUse(Game game) {
        game.getCurrent().currentRoom().entities.stream()
            .filter(e -> e instanceof GnomeEntity)
            .forEach(Entity::setDead);

        game.getLogger().printLine("You shot the gun. It made a loud bang.", Color.ORANGE);

        return this;
    }
}
