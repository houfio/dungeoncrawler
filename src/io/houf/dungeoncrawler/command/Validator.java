package io.houf.dungeoncrawler.command;

public abstract class Validator<T> {
    public final String type;

    public Validator(String type) {
        this.type = type;
    }

    public abstract T parse(String input);

    public String getSuggested(String input) {
        return null;
    }
}
