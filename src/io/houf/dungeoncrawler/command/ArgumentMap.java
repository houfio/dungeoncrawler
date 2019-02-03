package io.houf.dungeoncrawler.command;

import java.util.Map;

public class ArgumentMap {
    private final Map<String, String> arguments;

    public ArgumentMap(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    public boolean has(Argument<?> argument) {
        return this.arguments.containsKey(argument.name);
    }

    public <T>T get(Argument<T> argument) {
        return argument.validator.parse(this.arguments.get(argument.name));
    }
}
