package io.houf.dungeoncrawler.command.impl;

import io.houf.dungeoncrawler.Game;
import io.houf.dungeoncrawler.command.Argument;
import io.houf.dungeoncrawler.command.ArgumentMap;
import io.houf.dungeoncrawler.command.Command;
import io.houf.dungeoncrawler.ui.impl.LogUI;
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
    public LogUI.RawLogLine execute(Game game, ArgumentMap arguments) {
        game.openUI(new MainUI());

        return new LogUI.RawLogLine("Goodbye.", Color.WHITE);
    }
}
