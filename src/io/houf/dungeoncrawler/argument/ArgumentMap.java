package io.houf.dungeoncrawler.argument;

import java.util.Map;

public class ArgumentMap {
    private final Map<String, ?> arguments;

    public ArgumentMap(Map<String, ?> arguments) {
        this.arguments = arguments;
    }

    public boolean has(Argument<?> argument) {
        // Check if optional argument is given
        return this.arguments.containsKey(argument.name);
    }

    public <T> T get(Argument<T> argument) {
        // Parsing happened in CommandHandler, casting _should_ be safe here
        return (T) this.arguments.get(argument.name);
    }
}
