package io.houf.dungeoncrawler.command;

import java.util.List;

public class OptionValidator extends Validator<String> {
    private final List<String> options;

    public OptionValidator(List<String> options) {
        super("options");

        this.options = options;
    }

    @Override
    public String parse(String input) {
        return this.options.stream()
            .filter(option -> option.equals(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String getSuggested(String input) {
        return this.options.stream()
            .filter(option -> option.startsWith(input))
            .findFirst()
            .orElse(null);
    }
}
