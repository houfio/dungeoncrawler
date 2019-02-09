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
        // Find command by first part
        var command = Arrays.stream(CommandHandler.COMMANDS)
            .filter(c -> c.getName().equals(parts[0]))
            .findFirst()
            .orElse(null);

        if (command == null) {
            // If the player can't even write the command why bother?
            return new LogLine("Invalid command.", Color.WHITE);
        }

        try {
            var arguments = this.parseArguments(game, command, Arrays.copyOfRange(parts, 1, parts.length));

            // Execute found command with game instance and validated arguments
            return command.execute(game, arguments);
        } catch (RuntimeException e) {
            if (e.getMessage() == null) {
                return new LogLine("Invalid arguments.", Color.WHITE);
            } else {
                // Print validator's custom error message in a pretty red color
                return new LogLine(e.getMessage(), Color.RED);
            }
        }
    }

    public String getSuggested(Game game, String input) {
        if (input.trim().isEmpty()) {
            // Don't give suggestions when the player didn't type anything yet
            return "";
        }

        var parts = input.toLowerCase().split(" ");
        // Find command by first part, partially if only one part
        var command = Arrays.stream(CommandHandler.COMMANDS)
            .filter(c -> parts.length > 1 ? c.getName().equals(parts[0]) : c.getName().startsWith(parts[0]))
            .findFirst()
            .orElse(null);

        if (command == null) {
            // No command suggestion found
            return "";
        }

        var suggested = new StringBuilder(command.getName());
        var arguments = command.getArguments();

        if (parts.length - 1 > arguments.length) {
            // If more arguments given than the command has just give up
            return suggested.toString();
        }

        for (var i = 1; i < parts.length && i - 1 < arguments.length; i++) {
            var argument = arguments[i - 1];

            if (i != parts.length - 1) {
                // If not last argument don't give suggestion
                suggested
                    .append(" ")
                    .append(parts[i]);

                continue;
            }

            var s = argument.validator.getSuggested(game, parts[i]);

            if (s != null) {
                // Add validator suggestion if given
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
            // If more arguments given than command has I can guarantee the input is wrong
            throw new RuntimeException();
        }

        for (var i = 0; i < arguments.length; i++) {
            var argument = arguments[i];

            if (argument.required && i > array.length - 1) {
                // If there are too few arguments given, give up
                throw new RuntimeException();
            }

            if (i <= array.length - 1) {
                // Let validator parse input string to the correct type
                var value = argument.validator.parse(game, array[i]);

                if (value == null) {
                    // If null (a.k.a. invalid) check if validator has error message and throw
                    throw new RuntimeException(argument.validator.getError(game, array[i]));
                }

                map.put(argument.name, value);
            }
        }

        return new ArgumentMap(map);
    }
}
