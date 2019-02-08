package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.command.CommandHandler;
import io.houf.dungeoncrawler.ui.impl.LogUI;
import io.houf.dungeoncrawler.validator.impl.CommandValidator;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HelpCommand implements Command {
    private final Argument<Command> command = new Argument<>("command", "Command to receive detailed information about", false, new CommandValidator());

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[]{
            this.command
        };
    }

    @Override
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        if (!arguments.has(this.command)) {
            var lines = new HashMap<String, String>();

            for (var command : CommandHandler.COMMANDS) {
                String help = Arrays.stream(command.getArguments())
                    .map(argument -> this.getArgumentHelp(argument, false))
                    .reduce("", (a, b) -> a + " " + b);

                lines.put(command.getName(), help);
            }

            return new LogUI.RawLogLine(lines.keySet().stream().map(key -> key + lines.get(key)).collect(Collectors.joining("\n ")), Color.WHITE);
        }

        var command = arguments.get(this.command);

        var help = Arrays.stream(command.getArguments())
            .map(argument -> this.getArgumentHelp(argument, true))
            .reduce("", (a, b) -> a + "\n " + b);

        return new LogUI.RawLogLine(command.getName() + help, Color.WHITE);
    }

    private String getArgumentHelp(Argument<?> argument, boolean detailed) {
        var name = argument.required ? "<" + argument.name + ">" : "[" + argument.name + "]";

        if (!detailed) {
            return name;
        }

        return name + " " + argument.description + " (" + argument.validator.type + ")";
    }
}
