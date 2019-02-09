package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.argument.Argument;
import io.houf.dungeoncrawler.argument.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.ui.LogLine;
import io.houf.dungeoncrawler.ui.impl.MainUI;

import java.awt.*;

public class QuitCommand implements Command {
    @Override
    public String getName() {
        return "quit";
    }

    @Override
    public Argument<?>[] getArguments() {
        return new Argument[0];
    }

    @Override
    public LogLine execute(Game game, ArgumentMap arguments) {
        if (game.hasUI) {
            game.openUI(new MainUI());
        } else {
            // Don't go to main ui if the game is in console mode
            game.quit();
        }

        // Cya
        return new LogLine("Goodbye.", Color.WHITE);
    }
}
