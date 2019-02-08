package io.houf.dungeoncrawler.validator;

import io.houf.dungeoncrawler.Game;

public abstract class Validator<T> {
    public final String type;

    public Validator(String type) {
        this.type = type;
    }

    public abstract T parse(Game game, String input);

    public String getSuggested(Game game, String input) {
        return null;
    }

    public String getError(Game game, String input) {
        return null;
    }
}
