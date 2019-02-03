package io.houf.dungeoncrawler.command;

public class Argument<T> {
    public final String name;
    public final String description;
    public final boolean required;
    public final Validator<T> validator;

    public Argument(String name, String description, boolean required, Validator<T> validator) {
        this.name = name;
        this.description = description;
        this.required = required;
        this.validator = validator;
    }
}
