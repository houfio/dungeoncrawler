package io.houf.dungeoncrawler.validator.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.validator.Validator;

public class StringValidator extends Validator<String> {
    public StringValidator() {
        super("string");
    }

    @Override
    public String parse(Game game, String input) {
        return input;
    }
}
