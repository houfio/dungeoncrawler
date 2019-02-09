package io.houf.dungeoncrawler.validator.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.room.Side;
import io.houf.dungeoncrawler.validator.Validator;

import java.util.Arrays;

public class SideValidator extends Validator<Side> {
    public SideValidator() {
        super("side");
    }

    @Override
    public Side parse(Game game, String input) {
        // Filter sides on input
        return Arrays.stream(Side.values())
            .filter(side -> side.name().toLowerCase().equals(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getSuggested(Game game, String input) {
        return Arrays.stream(Side.values())
            .map(side -> side.name().toLowerCase())
            .filter(name -> name.startsWith(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getError(Game game, String input) {
        return "You only know of four cardinal directions.";
    }
}
