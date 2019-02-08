package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.impl.GateEntity;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class KeyItem extends Item {
    public KeyItem() {
        super("key");
    }

    @Override
    public Item onUse(Game game) {
        var room = game.getCurrent().currentRoom();
        var entity = room.entities.stream()
            .filter(e -> e instanceof GateEntity)
            .findFirst()
            .orElse(null);

        if (entity == null) {
            game.getLogger().printLine("You didn't know what to do with the key.", Color.ORANGE);

            return this;
        }

        game.getLogger().printLine("You threw the key inside the gate. You got teleported to another room.", Color.YELLOW);

        ((GateEntity) entity).activate(game);

        return null;
    }
}
