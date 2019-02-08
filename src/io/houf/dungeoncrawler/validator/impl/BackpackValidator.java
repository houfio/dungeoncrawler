package io.houf.dungeoncrawler.validator.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.item.Item;
import io.houf.dungeoncrawler.validator.Validator;

public class BackpackValidator extends Validator<Item> {
    public BackpackValidator() {
        super("backpack");
    }

    @Override
    public Item parse(Game game, String input) {
        return game.getCurrent().player.items.stream()
            .filter(item -> item.name.equals(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getSuggested(Game game, String input) {
        return game.getCurrent().player.items.stream()
            .map(item -> item.name)
            .filter(name -> name.startsWith(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getError(Game game, String input) {
        return "You couldn't find that item in your backpack.";
    }
}
