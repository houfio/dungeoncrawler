package io.houf.dungeoncrawler.command;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.impl.*;
import io.houf.dungeoncrawler.ui.impl.LogUI;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler {
    public static final Command[] COMMANDS = {
        new DropCommand(),
        new GetCommand(),
        new GnomeCommand(),
        new GoCommand(),
        new HelpCommand(),
        new LookCommand(),
        new PackCommand(),
        new QuitCommand(),
        new UseCommand()
    };

    public static List<String> getCommands() {
        return Arrays.stream(CommandHandler.COMMANDS)
            .map(Command::getName)
            .collect(Collectors.toList());
    }

    public LogUI.RawLogLine handle(Game game, String input) {
        var parts = input.split(" ");
        var command = Arrays.stream(CommandHandler.COMMANDS)
            .filter(c -> c.getName().equals(parts[0]))
            .findFirst()
            .orElse(null);

        if (command == null) {
            return new LogUI.RawLogLine("Command not found", Color.RED);
        }

        var arguments = this.parseArguments(command, Arrays.copyOfRange(parts, 1, parts.length));

        if (arguments == null) {
            return new LogUI.RawLogLine("Invalid arguments", Color.RED);
        }

        return command.execute(game, arguments);
    }

    public String getSuggested(String input) {
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

            var s = argument.validator.getSuggested(parts[i]);

            if (s != null) {
                suggested
                    .append(" ")
                    .append(s);
            }
        }

        return suggested.toString();
    }

    private ArgumentMap parseArguments(Command command, String[] array) {
        var map = new HashMap<String, Object>();
        var arguments = command.getArguments();

        for (var i = 0; i < arguments.length; i++) {
            var argument = arguments[i];

            if (argument.required && i > array.length - 1) {
                return null;
            }

            if (i <= array.length - 1) {
                var value = argument.validator.parse(array[i]);

                if (value == null) {
                    return null;
                }

                map.put(argument.name, value);
            }
        }

        return new ArgumentMap(map);
    }
}
