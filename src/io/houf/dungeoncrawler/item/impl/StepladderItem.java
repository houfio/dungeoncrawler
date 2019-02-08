package io.houf.dungeoncrawler.item.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.impl.HoleEntity;
import io.houf.dungeoncrawler.item.Item;

import java.awt.*;

public class StepladderItem extends Item {
    public StepladderItem() {
        super("stepladder");
    }

    @Override
    public Item onUse(Game game) {
        var room = game.getCurrent().currentRoom();
        var entity = room.entities.stream()
            .filter(e -> e instanceof HoleEntity)
            .findFirst()
            .orElse(null);

        if (entity == null) {
            game.getLogger().printLine("You tried to use the stepladder, but the ceiling was too low.", Color.ORANGE);

            return this;
        }

        game.getLogger().printLine("You escaped the dungeon through a hole in the ceiling. Congratulations!", Color.WHITE);

        ((HoleEntity) entity).escape(game);

        return null;
    }
}
