package io.houf.dungeoncrawler.command;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.impl.*;
import io.houf.dungeoncrawler.ui.LogLine;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler {
    public static final Command[] COMMANDS = {
        new DropCommand(),
        new GetCommand(),
        new GoCommand(),
        new HelpCommand(),
        new LookCommand(),
        new PackCommand(),
        new QuitCommand(),
        new UseCommand()
    };

    public LogLine handle(Game game, String input) {
        var parts = input.split(" ");
        var command = Arrays.stream(CommandHandler.COMMANDS)
            .filter(c -> c.getName().equals(parts[0]))
            .findFirst()
            .orElse(null);

        if (command == null) {
            return new LogLine("Invalid command.", Color.WHITE);
        }

        try {
            var arguments = this.parseArguments(game, command, Arrays.copyOfRange(parts, 1, parts.length));

            return command.execute(game, arguments);
        } catch (RuntimeException e) {
            if (e.getMessage() == null) {
                return new LogLine("Invalid arguments.", Color.WHITE);
            } else {
                return new LogLine(e.getMessage(), Color.RED);
            }
        }
    }

    public String getSuggested(Game game, String input) {
        if (input.trim().isEmpty()) {
            return "";
        }

        var parts = input.split(" ");
        var command = Arrays.stream(CommandHandler.COMMANDS)
            .filter(c -> parts.length > 1 ? c.getName().equals(parts[0]) : c.getName().startsWith(parts[0]))
            .findFirst()
            .orElse(null);

        if (command == null) {
            return "";
        }

        var suggested = new StringBuilder(command.getName());
        var arguments = command.getArguments();

        if (parts.length - 1 > arguments.length) {
            return suggested.toString();
        }

        for (var i = 1; i < parts.length && i - 1 < arguments.length; i++) {
            var argument = arguments[i - 1];

            if (i != parts.length - 1) {
                suggested
                    .append(" ")
                    .append(parts[i]);

                continue;
            }

            var s = argument.validator.getSuggested(game, parts[i]);

            if (s != null) {
                suggested
                    .append(" ")
                    .append(s);
            }
        }

        return suggested.toString();
    }

    private ArgumentMap parseArguments(Game game, Command command, String[] array) {
        var map = new HashMap<String, Object>();
        var arguments = command.getArguments();

        if (array.length > arguments.length) {
            throw new RuntimeException();
        }

        for (var i = 0; i < arguments.length; i++) {
            var argument = arguments[i];

            if (argument.required && i > array.length - 1) {
                throw new RuntimeException();
            }

            if (i <= array.length - 1) {
                var value = argument.validator.parse(game, array[i]);

                if (value == null) {
                    throw new RuntimeException(argument.validator.getError(game, array[i]));
                }

                map.put(argument.name, value);
            }
        }

        return new ArgumentMap(map);
    }
}
