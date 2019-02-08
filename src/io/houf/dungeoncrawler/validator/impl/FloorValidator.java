package io.houf.dungeoncrawler.validator.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.entity.impl.ItemEntity;
import io.houf.dungeoncrawler.validator.Validator;

public class FloorValidator extends Validator<ItemEntity> {
    public FloorValidator() {
        super("floor");
    }

    @Override
    public ItemEntity parse(Game game, String input) {
        return game.getCurrent().currentRoom().entities.stream()
            .filter(entity -> entity instanceof ItemEntity)
            .map(entity -> (ItemEntity) entity)
            .filter(entity -> entity.item.name.equals(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getSuggested(Game game, String input) {
        return game.getCurrent().currentRoom().entities.stream()
            .filter(entity -> entity instanceof ItemEntity)
            .map(entity -> ((ItemEntity) entity).item.name)
            .filter(name -> name.startsWith(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getError(Game game, String input) {
        return "You couldn't find that item on the floor.";
    }
}
